/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import java.util.ArrayList;
import java.util.List;

import com.flair.server.utilities.ServerLogger;

/**
 * Represents an executable job, which is basically a collection of tasks
 * 
 * @author shadeMe
 */
abstract class AbstractJob<R, E extends AbstractJobEvent<R>>
{
	interface EventHandler<E> {
		public void	handle(E event);
	}
	
	private final List<AbstractTask<?>>	registeredTasks;	// tasks that are either running or have been queued for execution
	private final List<EventHandler<E>>	listeners;
	private boolean						cancelled;
	private boolean						started;

	public AbstractJob()
	{
		registeredTasks = new ArrayList<>();
		cancelled = false;
		listeners = new ArrayList<>();
		started = false;
	}

	protected final synchronized boolean isStarted() {
		return started;
	}

	protected final synchronized void flagStarted()
	{
		started = true;
		
		// if there are no queued tasks, signal completion immediately
		if (registeredTasks.isEmpty())
			notifyListeners(createCompletionEvent());
	}

	protected final synchronized boolean isTaskRegistered(AbstractTask<?> task)
	{
		for (AbstractTask<?> itr : registeredTasks)
		{
			if (itr == task)
				return true;
		}

		return false;
	}

	private final synchronized AbstractTask<?> getFirstTask()
	{
		if (registeredTasks.isEmpty())
			return null;
		else
			return registeredTasks.get(0);
	}

	protected final synchronized void notifyListeners(E event)
	{
		if (listeners.isEmpty())
			return;
		
		// we execute the handlers in the calling thread's context
		// ### TODO will this deadlock?
		for (EventHandler<E> itr : listeners)
			itr.handle(event);
	}

	protected final synchronized boolean isCompleted() {
		return started == true && (cancelled == true || registeredTasks.isEmpty());
	}

	protected final void waitForCompletion()
	{
		if (isStarted() == false)
			throw new IllegalStateException("Job has not started yet");

		while (isCompleted() == false)
		{
			AbstractTask<?> first = getFirstTask();
			if (first == null)
				return;

			try
			{
				first.getFutureTask().get();
			} catch (Throwable ex)
			{
				ServerLogger.get().error(ex, "Job encounted an exception while waiting. Exception: " + ex.toString());
			}
		}
	}

	protected final synchronized void registerTask(AbstractTask<?> task)
	{
		if (isTaskRegistered(task))
			throw new IllegalStateException("Task already registered");

		if (cancelled == false)
			registeredTasks.add(task);
	}

	protected final synchronized void unregisterTask(AbstractTask<?> task)
	{
		if (isTaskRegistered(task))
			registeredTasks.remove(task);
		
		if (registeredTasks.isEmpty() && isCancelled() == false)
			notifyListeners(createCompletionEvent());
	}

	protected final synchronized void linkTask(AbstractTaskResult<?> result) {
		handleTaskCompletion(result);
	}
	
	public final synchronized void cancel()
	{
		if (isCompleted())
			return;

		cancelled = true;
		List<AbstractTask<?>> nonexec = new ArrayList<>();
		for (AbstractTask<?> itr : registeredTasks)
		{
			itr.cancel();
			if (itr.isExecuting() == false)
				nonexec.add(itr);
		}

		// just remove those tasks that were never executed
		registeredTasks.removeAll(nonexec);
	}

	public final synchronized boolean isCancelled() {
		return cancelled;
	}

	public final synchronized void addListener(EventHandler<E> callback) {
		listeners.add(callback);
	}

	protected abstract E						createCompletionEvent();
	public abstract void 						begin();
	protected abstract void						handleTaskCompletion(AbstractTaskResult<?> result);
	public abstract R 							getOutput();
}