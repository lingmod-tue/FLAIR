/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.flair.server.crawler.SearchResult;
import com.flair.server.utilities.ServerLogger;

/**
 * Fetches a SearchResult's text
 * 
 * @author shadeMe
 */
class WebCrawlTask extends AbstractTask<WebCrawlTaskResult>
{
	static final class Executor extends AbstractTaskExecutor
	{
		// silly workaround to prevent the I/O op from blocking perpetually
		private final ExecutorService 		auxThreadPool;
		
		private Executor()
		{
			super("WebCrawl", Constants.TEXTFETCHER_THREADPOOL_SIZE);
			auxThreadPool = Executors.newFixedThreadPool(Constants.TEXTFETCHER_THREADPOOL_SIZE, createPoolThreadFactory("WebCrawlAux"));
		}

		public void crawl(WebCrawlTask task)
		{
			task.setFetchExecutor(auxThreadPool);
			queue(task);
		}
		
		@Override
		public void shutdown(boolean force) {
			super.shutdown(force);
			shutdown(auxThreadPool, force);
		}
	}

	public static final Executor getExecutor() {
		return new Executor();
	}

	private final SearchResult			input;
	private ExecutorService				fetchExecutor;

	private static final int 			TIMEOUT_SECONDS = 30;

	final class FetchRunnable implements Callable<Boolean>
	{
		private final SearchResult source;

		public FetchRunnable(SearchResult source) {
			this.source = source;
		}

		@Override
		public Boolean call()
		{
			if (source.isTextFetched() == false)
				return input.fetchPageText(false);
			else
				return true;
		}
	}

	public WebCrawlTask(AbstractJob<?,?> job, SearchResult source)
	{
		super(TaskType.WEB_CRAWL, job, new BasicTaskLinker<WebCrawlTaskResult>(job));

		this.input = source;
		this.fetchExecutor = null;
	}

	public void setFetchExecutor(ExecutorService executor) {
		fetchExecutor = executor;
	}

	@Override
	protected WebCrawlTaskResult performTask()
	{
		if (fetchExecutor == null)
			throw new IllegalStateException("Auxiliary threadpool not set");

		WebCrawlTaskResult result = new WebCrawlTaskResult(input);
		FutureTask<Boolean> fetchWrapper = new FutureTask<>(new FetchRunnable(input));
		try
		{
			fetchExecutor.submit(fetchWrapper).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		} catch (TimeoutException ex)
		{
			ServerLogger.get().error("Fetch text timed out for URL: " + input.getDisplayURL());
		} catch (Throwable ex)
		{
			ServerLogger.get().error(ex, "Fetch text encountered an exception for URL: " + input.getDisplayURL()
					+ ". Exception: " + ex.toString());
		}

		ServerLogger.get().trace("Search Result (" + input.getDisplayURL() + ") text fetched: " + result.wasSuccessful());
		return result;
	}
}

class WebCrawlTaskResult
{
	private final SearchResult output;

	public WebCrawlTaskResult(SearchResult output) {
		this.output = output;
	}

	public SearchResult getOutput() {
		return output;
	}

	public boolean wasSuccessful() {
		return output.isTextFetched();
	}
}