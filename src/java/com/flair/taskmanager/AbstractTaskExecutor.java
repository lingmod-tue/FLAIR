/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
