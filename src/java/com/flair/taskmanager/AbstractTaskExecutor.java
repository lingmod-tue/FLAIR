/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Basic implementation of a background task executor
 * @author shadeMe
 */
abstract class AbstractTaskExecutor
{
    private final ExecutorService	threadPool;
    
    public AbstractTaskExecutor(int numThreads) {
	threadPool = Executors.newFixedThreadPool(numThreads);
    }
    
    protected void queue(List<AbstractTask> tasks)
    {
	for (AbstractTask itr : tasks)
	    threadPool.submit(itr.getFutureTask());
    }
    
    protected void queue(AbstractTask task) {
	threadPool.submit(task.getFutureTask());
    }
}
