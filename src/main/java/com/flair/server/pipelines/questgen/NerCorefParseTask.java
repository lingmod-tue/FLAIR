package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public class NerCorefParseTask implements AsyncTask<NerCorefParseTask.Result> {
	static NerCorefParseTask factory(ParsingStrategy strategy,
	                                 CoreNlpParser parser) {
		return new NerCorefParseTask(strategy, parser);
	}

	private final ParsingStrategy strategy;
	private final CoreNlpParser parser;

	private NerCorefParseTask(ParsingStrategy strategy, CoreNlpParser parser) {
		this.strategy = strategy;
		this.parser = parser;
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

				return strategy.output().parsedDoc;
			}), Constants.NERCOREF_PARSE_TASK_TIMEOUT, Constants.TIMEOUT_UNIT);
		} catch (TimeoutException ex) {
			ServerLogger.get().error("NER/Coref parsing task timed-out for " + strategy.input().source.getDescription());
			output = null;
			error = true;
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "NER/Coref parsing task encountered an error. Exception: " + ex.toString());
			output = null;
			error = true;
		}

		long endTime = System.currentTimeMillis();
		if (!error)
			ServerLogger.get().info("Document " + output.getDescription() + " parsed for NER/Coref in " + (endTime - startTime) + " ms");

		return new Result(output);
	}

	static final class Result {
		final AbstractDocument output;

		Result(AbstractDocument output) {
			this.output = output;
		}
	}
}
