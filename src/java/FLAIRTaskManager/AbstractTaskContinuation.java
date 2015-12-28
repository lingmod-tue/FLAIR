/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRTaskManager;

/**
 * Represents a functor that gets called after an AbstractTask has run to completion
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
class GenericTaskChain implements AbstractTaskContinuation
{
    private final AbstractTaskLinkingJob	parentJob;
    
    public GenericTaskChain(AbstractTaskLinkingJob job)
    {
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
