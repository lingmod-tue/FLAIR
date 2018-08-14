package com.flair.server.pipelines.gramparsing;

import com.flair.server.crawler.SearchResult;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public class WebCrawlTask implements AsyncTask<WebCrawlTask.Result> {
	static WebCrawlTask factory(SearchResult source) {
		return new WebCrawlTask(source);
	}

	private final SearchResult input;

	private WebCrawlTask(SearchResult source) {
		this.input = source;
	}


	@Override
	public Result run() {
		Result result = new Result(input);
		try {
			ThreadPool.get().invokeAndWait(new FutureTask<>(() -> {
				if (!input.isTextFetched())
					return input.fetchPageText(false);
				else
					return true;
			}), Constants.WEB_CRAWL_TASK_TIMEOUT, Constants.TIMEOUT_UNIT);
		} catch (TimeoutException ex) {
			ServerLogger.get().error("Fetch text timed out for URL: " + input.getDisplayURL());
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Fetch text encountered an exception for URL: " + input.getDisplayURL()
					+ ". Exception: " + ex.toString());
		}

		ServerLogger.get().trace("Search Result (" + input.getDisplayURL() + ") text fetched: " + result.output.isTextFetched());
		return result;
	}

	static final class Result {
		final SearchResult output;

		Result(SearchResult output) {
			this.output = output;
		}
	}
}
