/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

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
    private final List<AbstractTask>		registeredTasks;	// tasks that are either running or have been queued for execution
    private boolean				cancelled;
    private final List<Runnable>		completionListeners;
    private boolean				started;
    
    class NotifyThread extends Thread
    {
	private final List<Runnable>		listeners;

	public NotifyThread(List<Runnable> listeners) {
	    this.listeners = new ArrayList<>(listeners);
	}
	
	@Override
	public void run()
	{
	    for (Runnable itr : listeners)
		itr.run();
	}
    }
    
    public AbstractJob()
    {
	registeredTasks = new ArrayList<>();
	cancelled = false;
	completionListeners = new ArrayList<>();
	started = false;
    }
    
    protected synchronized boolean isStarted() {
	return started;
    }
    
    protected synchronized void flagStarted() 
    {
	started = true;
	
	// in the rare event when the job's over before the begin() method returns
	if (isCompleted())
	    notifyCompletionListeners();
    }
    
    protected synchronized boolean isTaskRegistered(AbstractTask task)
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
    
    private synchronized void notifyCompletionListeners()
    {
	if (started == false || completionListeners.isEmpty())
	    return;
	
	// we use a separate thread for notification to prevent deadlocks (when a listener attempts to access the job object)
	new NotifyThread(completionListeners).start();
	
	// remove all registered listeners to prevent them being notified multiple times
	// for instance, when the job is cancelled while there are active tasks
	completionListeners.clear();
    }
    
    protected synchronized boolean isCompleted() {
	return started == true && (cancelled == true || registeredTasks.isEmpty());
    }
    
    protected void waitForCompletion()
    {
	if (isStarted() == false)
	    throw new IllegalStateException("Job has not started yet");
	
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
	    
	if (isCompleted())
	    notifyCompletionListeners();
    }
    
    public synchronized void cancel()
    {
	if (isCompleted())
	    return;
	
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
	notifyCompletionListeners();
    }
    
    public synchronized boolean isCancelled() {
	return cancelled;
    }
    
    // returns false if the job is already complete, true otherwise
    // DOES NOT execute the callback in the former case
    public synchronized boolean registerCompletionListener(Runnable callback)
    {
	if (isCompleted())
	    return false;
	
	completionListeners.add(callback);
	return true;
    }
    
    public abstract void	begin();
    public abstract Object	getOutput();
}

abstract class AbstractTaskLinkingJob extends AbstractJob implements AbstractTaskLinker
{
    public AbstractTaskLinkingJob() {
	super();
    }
}

