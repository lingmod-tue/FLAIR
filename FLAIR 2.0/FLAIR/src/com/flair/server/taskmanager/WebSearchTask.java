/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import com.flair.server.crawler.SearchResult;
import com.flair.server.crawler.WebSearchAgent;
import com.flair.server.utilities.FLAIRLogger;

import java.util.List;

/**
 * Fetches search results for a given query
 * 
 * @author shadeMe
 */
class WebSearchTask extends AbstractTask<WebSearchTaskResult>
{
	static final class Executor extends AbstractTaskExecutor
	{
		private Executor() {
			super(Constants.TEXTFETCHER_THREADPOOL_SIZE);
		}

		public void search(WebSearchTask task) {
			queue(task);
		}
	}
	
	public static final Executor getExecutor() {
		return new Executor();
	}
	
	private final WebSearchAgent	input;
	private final int				numResults;

	public WebSearchTask(AbstractJob<?, ?> job,
						WebSearchAgent source,
						int numResults)
	{
		super(TaskType.WEB_SEARCH, job, new BasicTaskLinker<WebSearchTaskResult>(job));
		this.input = source;
		this.numResults = numResults;
	}

	@Override
	protected WebSearchTaskResult performTask()
	{
		List<SearchResult> hits = input.getNext(numResults);
		WebSearchTaskResult result = new WebSearchTaskResult(hits, input);
		FLAIRLogger.get().trace("Web Search for '" + input.getQuery() + "' fetched " + hits.size() + " results");
		return result;
	}
}

class WebSearchTaskResult
{
	private final List<SearchResult>	output;
	private final WebSearchAgent		agent;

	public WebSearchTaskResult(List<SearchResult> output, WebSearchAgent agent)
	{
		this.output = output;
		this.agent = agent;
	}

	public List<SearchResult> getOutput() {
		return output;
	}

	public WebSearchAgent getAgent() {
		return agent;
	}
}