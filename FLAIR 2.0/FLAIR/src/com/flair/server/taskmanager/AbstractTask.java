/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.flair.server.utilities.FLAIRLogger;

/**
 * Represents a basic concurrent task
 * 
 * @author shadeMe
 */
abstract class AbstractTask<R> implements Callable<R>
{
	/* gets called after an AbstractTask has successfully run to completion
	 * executed in the same thread context as the finishing task
	 */
	interface Continuation<R> {
		public void run(AbstractTaskResult<R> previous); 		// the result of the task that called the continuation
	}

	private static final class Result<R> implements AbstractTaskResult<R>
	{
		private final TaskType		type;
		private final R				output;
		
		Result(TaskType t, R o) 
		{
			type = t;
			output = o;
		}
		
		@Override
		public R getResult() {
			return output;
		}

		@Override
		public TaskType getType() {
			return type;
		}
	}
	
	private final TaskType							type;
	private final AbstractJob<?, ?>					parentJob;
	private final Continuation<R>					continuation;
	private final FutureTask<R>						wrapper;
	private boolean									cancelled;
	private boolean									executing;
	private Result<R>								output;

	public AbstractTask(TaskType type, AbstractJob<?,?> job, Continuation<R> continuation)
	{
		this.type = type;
		this.parentJob = job;
		this.continuation = continuation;
		this.wrapper = new FutureTask<>(this);
		this.output = null;
		cancelled = executing = false;
	}
	
	public final TaskType getType() {
		return type;
	}

	public final FutureTask<R> getFutureTask() {
		return wrapper;
	}

	public final synchronized boolean isExecuting() {
		return executing;
	}

	private final synchronized void setExecuting(boolean state) 
	{
		assert executing != state;
		executing = state;
	}

	public final synchronized boolean isCancelled() {
		return cancelled;
	}

	public final synchronized void cancel() 
	{
		if (cancelled == false)
		{
			cancelled = true;
			wrapper.cancel(false);
		}
	}

	@Override
	public R call() 
	{
		// the actual precondition - the task is already registered to the job
		// but we need to account for race conditions
		if (parentJob.isTaskRegistered(this) == false)
			throw new IllegalStateException("Unregistered task being executed");

		try
		{
			setExecuting(true);
			if (isCancelled() == false)
			{
				output = new Result<>(type, performTask());
				if (isCancelled() == false && continuation != null)
					continuation.run(output);
			}
		} catch (Throwable ex) 
		{
			FLAIRLogger.get().error(ex, "Uncaught exception in AbstractTask: " + ex.toString());
		} finally
		{
			parentJob.unregisterTask(this);
			setExecuting(false);
		}

		return output == null ? null : output.getResult();
	}
	
	public Result<R> getResult() {
		return output;		// null if the task hasn't run to completion
	}

	protected abstract R performTask();
}
