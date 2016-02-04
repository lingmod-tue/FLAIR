/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.utilities.FLAIRLogger;
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
    private boolean					cancelled;
    private boolean					executing;
    
    public AbstractTask(AbstractJob job, TaskType type, AbstractTaskContinuation continuation)
    {
	this.parentJob = job;
	this.type = type;
	this.continuation = continuation;
	this.wrapper = new FutureTask<>(this);
	cancelled = executing = false;
    }
    
    public TaskType getType() {
	return type;
    }
    
    public FutureTask<AbstractTaskResult> getFutureTask() {
	return wrapper;
    }
    
    public synchronized boolean isExecuting() {
	return executing;
    }
    
    private synchronized void setExecuting(boolean state) 
    {
	assert executing != state;
	executing = state;
    }
    
    public synchronized boolean isCancelled() {
	return cancelled;
    }
    
    public synchronized void cancel()
    {
	if (cancelled == false)
	{
	    cancelled = true;
	    wrapper.cancel(false);
	}
    }
    
    @Override
    public AbstractTaskResult call()
    {
	// the actual precondition - the task is already registered to the job
	// but we need to account for race conditions
	if (parentJob.isTaskRegistered(this) == false)
	    throw new IllegalStateException("Unregistered task being executed");
	
	AbstractTaskResult result = null;
	try
	{
	    setExecuting(true);
	    if (isCancelled() == false)
	    {	
		 result = performTask();
		 if (isCancelled() == false && continuation != null)
		     continuation.run(result);
	    }
	} 
	catch (Exception ex) {
	    FLAIRLogger.get().error("Uncaught exception in AbstractTask: " + ex.getMessage());
	} 
	finally 
	{
	    parentJob.unregisterTask(this);
	    setExecuting(false);
	}
	
	return result;
    }
    
    protected abstract AbstractTaskResult performTask();
}
