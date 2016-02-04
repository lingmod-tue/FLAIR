/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.parser.DocumentCollection;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Represents an executable job, which is basically a collection of tasks
 * @author shadeMe
 */
abstract class AbstractJob
{
    private final List<AbstractTask>		registeredTasks;    // tasks that are either running or have been queued for execution
    private boolean				cancelled;
    
    public AbstractJob()
    {
	registeredTasks = new ArrayList<>();
	cancelled = false;
    }
    
    public synchronized boolean isTaskRegistered(AbstractTask task)
    {
	for (AbstractTask itr : registeredTasks)
	{
	    if (itr == task)
		return true;
	}
	
	return false;
    }
    
    private synchronized AbstractTask getFirstTask()
    {
	if (registeredTasks.isEmpty())
	    return null;
	else
	    return registeredTasks.get(0);
    }
    
    protected synchronized boolean isCompleted() {
	return cancelled == true || registeredTasks.isEmpty();
    }
    
    protected void waitForCompletion()
    {
	while (isCompleted() == false)
	{
	   AbstractTask first = getFirstTask();
	    if (first == null)
		return;

	    try {
		first.getFutureTask().get();
	    }
	    catch (InterruptedException | ExecutionException e) {
		FLAIRLogger.get().error("Job encounted an exception while waiting. Exception: " + e.getMessage());
	    } 
	}
    }
    
    public synchronized void registerTask(AbstractTask task)
    {
	if (isTaskRegistered(task))
	    throw new IllegalStateException("Task already registered");
	
	if (cancelled == false)
	    registeredTasks.add(task);
    }
    
    public synchronized void unregisterTask(AbstractTask task)
    {
	if (isTaskRegistered(task));
	    registeredTasks.remove(task);
    }
    
    public synchronized void cancel()
    {
	cancelled = true;
	List<AbstractTask> nonexec = new ArrayList<>();
	for (AbstractTask itr : registeredTasks)
	{
	    itr.cancel();
	    if (itr.isExecuting() == false)
		nonexec.add(itr);
	}
	
	// just remove those tasks that were never executed
	registeredTasks.removeAll(nonexec);
    }
    
    public synchronized boolean isCancelled() {
	return cancelled;
    }
    
    public abstract void begin();
}

interface BasicParsingJobOutput
{
    public DocumentCollection	getParsedDocuments();
}

abstract class AbstractTaskLinkingJob extends AbstractJob implements AbstractTaskLinker
{
    protected boolean		jobStarted;
    
    public AbstractTaskLinkingJob() 
    {
	super();
	this.jobStarted = false;
    }
}

