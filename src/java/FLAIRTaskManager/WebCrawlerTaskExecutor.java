/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRTaskManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Task scheduler for web crawling tasks
 * @author shadeMe
 */
class WebCrawlerTaskExecutor extends AbstractTaskExecutor
{
    public WebCrawlerTaskExecutor()
    {
	super(Constants.TEXTFETCHER_THREADPOOL_SIZE);
    }
    
    public void crawl(List<WebCrawlerTask> tasks)
    {
	// ### TODO needs to be fair when handling requests from multiple clients
	// e.g, the first client requests some 100 results but the second only wants 10 or so
	if (tasks.isEmpty())
	    return;
	
	List<AbstractTask> collection = new ArrayList<>();
	collection.addAll(tasks);
	queue(collection);
    }
}