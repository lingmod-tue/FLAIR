package com.flair.server.taskmanager;

/*
 * Links tasks to their parent jobs to form a task chain
 */
class BasicTaskLinker<R> implements AbstractTask.Continuation<R>
{
	private final AbstractJob<?, ?>		parentJob;
	
	public BasicTaskLinker(AbstractJob<?,?> job) {
		parentJob = job;
	}
	
	@Override
	public void run(AbstractTaskResult<R> previous) 
	{
		if (parentJob.isCancelled() == false)
			parentJob.linkTask(previous);
	}
}
