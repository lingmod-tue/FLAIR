package com.flair.server.pipelines.common;

import com.flair.server.scheduler.AsyncJob;
import com.flair.server.scheduler.Cancellable;
import com.flair.server.utilities.ServerLogger;

public abstract class PipelineOp<I, O> implements Cancellable {
	public interface EventHandler<E> {
		void handle(E event);
	}

	public interface PipelineOpBuilder<I, O> {
		PipelineOp<I, O> build();
	}


	protected AsyncJob job;
	protected final I input;
	protected final O output;
	protected final TaskSyncHelper taskLinker;
	protected final String name;
	private boolean launched;

	protected synchronized <R> void linkTasks(AsyncJob job, R taskResult) {
		taskLinker.syncTask(job, taskResult);
	}

	protected abstract String desc();

	protected PipelineOp(String name, I input, O output) {
		this.input = input;
		this.output = output;
		this.taskLinker = new TaskSyncHelper();
		this.name = name;
		this.launched = false;
	}

	protected void safeInvoke(Runnable callee, String exceptionMessage) {
		try {
			callee.run();
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "(" + name() + ") " + exceptionMessage + ". Exception: " + ex);
		}
	}

	public PipelineOp<I, O> launch() {
		if (launched)
			throw new IllegalStateException(name() + " has already been launched");

		launched = true;
		return this;
	}
	public boolean isExecuting() {
		if(job == null) 
			return false;
		return job.isExecuting();
	}
	public void await() {
		if (!launched)
			throw new IllegalStateException(name() + " has yet to be launched");

		job.await();
	}
	public String name() {
		return name;
	}
	public boolean isCancelled() {
		if(job == null) 
			return true;
		return job.isCancelled();
	}
	public void cancel() {
		job.cancel();
	}
	@Override
	public String toString() {
		if (isExecuting())
			return name + " is still running";
		else if (isCancelled())
			return name + " was cancelled";
		else
			return desc();
	}
	public O yield() {
		await();
		return output;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		PipelineOp<I, O> that = (PipelineOp<I, O>) o;
		
		return job.equals(that.job) && input.equals(that.input) &&
				output.equals(that.output) && taskLinker.equals(that.taskLinker) &&
				name == that.name && launched == that.launched;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + name.hashCode();
		hash = 31 * hash + job.hashCode();
		return hash;
	}
}
