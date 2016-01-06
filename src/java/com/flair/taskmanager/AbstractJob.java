/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an executable job, which is basically a collection of tasks
 * @author shadeMe
 */
abstract class AbstractJob
{
    private final List<AbstractTask>		queuedTasks;
    private boolean				cancelled;
    
    public AbstractJob()
    {
	queuedTasks = new ArrayList<>();
	cancelled = false;
    }
    
    public synchronized boolean isRegistered(AbstractTask task)
    {
	for (AbstractTask itr : queuedTasks)
	{
	    if (itr == task)
		return true;
	}
	
	return false;
    }
    
    private synchronized AbstractTask getFirstTask()
    {
	if (queuedTasks.isEmpty())
	    return null;
	else
	    return queuedTasks.get(0);
    }
    
    private synchronized boolean isCompleted() {
	return cancelled == true || queuedTasks.isEmpty();
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
		Logger.getLogger(AbstractJob.class.getName()).log(Level.SEVERE, null, e);
	    } 
	}
	
    }
    
    public synchronized void registerTask(AbstractTask task)
    {
	assert isRegistered(task) == false;
	if (cancelled == false)
	    queuedTasks.add(task);
    }
    
    public synchronized void unregisterTask(AbstractTask task)
    {
	if (isRegistered(task));
	    queuedTasks.remove(task);
    }
    
    public synchronized void cancel()
    {
	cancelled = true;
	for (AbstractTask itr : queuedTasks)
	    itr.getFutureTask().cancel(false);
	
	queuedTasks.clear();
    }
    
    public synchronized boolean isCancelled() {
	return cancelled;
    }
    
    public abstract void begin();
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