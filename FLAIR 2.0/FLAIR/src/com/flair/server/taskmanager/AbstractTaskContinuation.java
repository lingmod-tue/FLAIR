/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

/**
 * Represents a functor that gets called after an AbstractTask has successfully run to completion
 * @author shadeMe
 */
interface AbstractTaskContinuation
{
    public void run(AbstractTaskResult previous);		// the result of the task that called the continuation
}

// interface to link different tasks
interface AbstractTaskLinker
{
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult);
}

// daisy-chains tasks
class BasicTaskChain implements AbstractTaskContinuation
{
    private final AbstractTaskLinkingJob	parentJob;
    
    public BasicTaskChain(AbstractTaskLinkingJob job) {
	this.parentJob = job;
    }
    
    @Override
    public void run(AbstractTaskResult previous)
    {
	if (parentJob.isCancelled())
	    return;
	
	parentJob.performLinking(previous.getType(), previous);
    }
}
