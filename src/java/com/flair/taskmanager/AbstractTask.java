/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Represents a basic concurrent task
 * @author shadeMe
 */
abstract class AbstractTask implements Callable<AbstractTaskResult>
{
    private final AbstractJob				parentJob;
    private final TaskType				type;
    private final AbstractTaskContinuation		continuation;
    private final FutureTask<AbstractTaskResult>	wrapper;
    
    public AbstractTask(AbstractJob job, TaskType type, AbstractTaskContinuation continuation)
    {
	this.parentJob = job;
	this.type = type;
	this.continuation = continuation;
	this.wrapper = new FutureTask<>(this);
    }
    
    public TaskType getType() {
	return type;
    }
    
    public FutureTask<AbstractTaskResult> getFutureTask() {
	return wrapper;
    }
    
    @Override
    public AbstractTaskResult call()
    {
	// the actual precondition - the task is already registered to the job
	// but we need to account for race conditions
	if (parentJob.isCancelled())
	    return null;
	
	assert parentJob.isRegistered(this) == true;
	
	AbstractTaskResult result = performTask();
	if (continuation != null)
	    continuation.run(result);
	
	parentJob.unregisterTask(this);
	return result;
    }
    
    protected abstract AbstractTaskResult performTask();
}
