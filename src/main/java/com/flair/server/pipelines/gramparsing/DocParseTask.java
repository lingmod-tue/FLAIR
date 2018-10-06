package com.flair.server.pipelines.gramparsing;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.AbstractKeywordSearcher;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.KeywordSearcherOutput;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public class DocParseTask implements AsyncTask<DocParseTask.Result> {
	static DocParseTask factory(ParsingStrategy strategy,
	                            CoreNlpParser parser,
	                            AbstractKeywordSearcher keywordSearcher,
	                            KeywordSearcherInput keywordSearcherInput) {
		return new DocParseTask(strategy, parser, keywordSearcher, keywordSearcherInput);
	}

	private final ParsingStrategy strategy;
	private final CoreNlpParser parser;
	private final AbstractKeywordSearcher keywordSearcher;
	private final KeywordSearcherInput keywordSearcherInput;

	private DocParseTask(ParsingStrategy strategy,
	                     CoreNlpParser parser,
	                     AbstractKeywordSearcher keywordSearcher,
	                     KeywordSearcherInput keywordSearcherInput) {
		this.strategy = strategy;
		this.parser = parser;
		this.keywordSearcher = keywordSearcher;
		this.keywordSearcherInput = keywordSearcherInput;
	}

	@Override
	public Result run() {
		AbstractDocument output;
		long startTime = 0;
		boolean error = false;

		try {
			startTime = System.currentTimeMillis();
			output = ThreadPool.get().invokeAndWait(new FutureTask<>(() -> {
				strategy.apply(parser);
				if (!strategy.output().valid())
					throw new IllegalStateException("Parser didn't set the document's parsed flag");

				AbstractDocument parsedDoc = strategy.output().parsedDoc;
				KeywordSearcherOutput keywordData = keywordSearcher.search(parsedDoc.getText(), keywordSearcherInput);
				parsedDoc.setKeywordData(keywordData);
				return parsedDoc;
			}), Constants.PARSE_DOC_TASK_TIMEOUT, Constants.TIMEOUT_UNIT);
		} catch (TimeoutException ex) {
			ServerLogger.get().error("Document parsing task timed-out for " + strategy.input().source.getDescription());
			output = null;
			error = true;
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Document parsing task encountered an error. Exception: " + ex.toString());
			output = null;
			error = true;
		}

		long endTime = System.currentTimeMillis();
		if (!error)
			ServerLogger.get().info("Document " + output.getDescription() + " parsed in " + (endTime - startTime) + " ms");

		return new Result(output);
	}

	static final class Result {
		final AbstractDocument output;

		Result(AbstractDocument output) {
			this.output = output;
		}
	}
}
