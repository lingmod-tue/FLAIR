/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.flair.server.utilities.ServerLogger;

/**
 * Basic implementation of a background task executor
 * 
 * @author shadeMe
 */
abstract class AbstractTaskExecutor
{
	private final ExecutorService threadPool;

	public AbstractTaskExecutor(int numThreads) {
		threadPool = Executors.newFixedThreadPool(numThreads);
	}

	protected void queue(List<AbstractTask<?>> tasks)
	{
		for (AbstractTask<?> itr : tasks)
			threadPool.submit(itr.getFutureTask());
	}

	protected void queue(AbstractTask<?> task) {
		threadPool.submit(task.getFutureTask());
	}

	public void shutdown(boolean force) 
	{
		if (force)
			threadPool.shutdownNow();
		else
		{
			try
			{
				threadPool.shutdown();
				if (threadPool.awaitTermination(5, TimeUnit.MINUTES) == false)
					threadPool.shutdownNow();
			} catch (InterruptedException ex)
			{
				ServerLogger.get().error(ex, "Couldn't shutdown task executor thread pool. Exception: " + ex.toString());
			}
		}
	}
}
