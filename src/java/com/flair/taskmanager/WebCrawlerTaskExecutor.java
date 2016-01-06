/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Task scheduler for web crawling tasks
 * @author shadeMe
 */
class WebCrawlerTaskExecutor extends AbstractTaskExecutor
{
    private final ExecutorService	    auxThreadPool;	    // silly workaround to prevent the I/O op from blocking perpetually
								    // wouldn't be an issue if the boilerpipe API had a timeout param
    public WebCrawlerTaskExecutor()
    {
	super(Constants.TEXTFETCHER_THREADPOOL_SIZE);
	auxThreadPool = Executors.newFixedThreadPool(Constants.TEXTFETCHER_THREADPOOL_SIZE);
    }
    
    public void crawl(List<WebCrawlerTask> tasks)
    {
	// ### TODO needs to be fair when handling requests from multiple clients
	// e.g, the first client requests some 100 results but the second only wants 10 or so
	if (tasks.isEmpty())
	    return;
	
	for (WebCrawlerTask itr : tasks)
	    itr.setFetchExecutor(auxThreadPool);
	
	List<AbstractTask> collection = new ArrayList<>();
	collection.addAll(tasks);
	queue(collection);
    }
}