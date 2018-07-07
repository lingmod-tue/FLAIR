package com.flair.server.taskmanager;

import com.flair.server.utilities.ServerLogger;

/*
 * Abstract operation that wraps around a generic job
 */
abstract class BasicPipelineOperation implements AbstractPipelineOperation {
	protected final AbstractJob<?, ?> job;
	protected final PipelineOperationType type;

	public BasicPipelineOperation(AbstractJob<?, ?> job, PipelineOperationType type) {
		this.job = job;
		this.type = type;
	}

	@Override
	public void begin() {
		job.begin();
	}

	@Override
	public boolean isCancelled() {
		return job.isCancelled();
	}

	@Override
	public void cancel() {
		if (isCompleted() == false) {
			job.cancel();
			ServerLogger.get().info("Pipeline operation " + getType() + " was cancelled");
		}
	}

	@Override
	public String toString() {
		return job.toString();
	}

	@Override
	public boolean isCompleted() {
		return job.isCompleted();
	}

	@Override
	public PipelineOperationType getType() {
		return type;
	}

	@Override
	public void waitForCompletion() {
		job.waitForCompletion();
	}
}
