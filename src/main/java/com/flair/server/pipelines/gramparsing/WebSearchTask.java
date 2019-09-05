package com.flair.server.pipelines.gramparsing;

import com.flair.server.crawler.SearchResult;
import com.flair.server.crawler.WebSearchAgent;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.utilities.ServerLogger;

import java.util.List;

public class WebSearchTask implements AsyncTask<WebSearchTask.Result> {
	public static WebSearchTask factory(WebSearchAgent source, int numResults) {
		return new WebSearchTask(source, numResults);
	}

	private final WebSearchAgent input;
	private final int numResults;

	private WebSearchTask(WebSearchAgent source, int numResults) {
		this.input = source;
		this.numResults = numResults;
	}


	@Override
	public Result run() {
		List<SearchResult> hits = input.getNext(numResults);
		Result result = new Result(hits, input);
		ServerLogger.get().trace("Web Search for '" + input.getQuery() + "' fetched " + hits.size() + " results");
		return result;
	}

	static final class Result {
		final List<SearchResult> output;
		final WebSearchAgent agent;

		Result(List<SearchResult> output, WebSearchAgent agent) {
			this.output = output;
			this.agent = agent;
		}
	}
}
