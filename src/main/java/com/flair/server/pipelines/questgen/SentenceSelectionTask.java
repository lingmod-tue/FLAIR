package com.flair.server.pipelines.questgen;

import com.flair.server.scheduler.AsyncTask;
import com.flair.server.sentencesel.SentenceSelector;
import com.flair.server.utilities.ServerLogger;

import java.util.ArrayList;
import java.util.Collection;

public class SentenceSelectionTask implements AsyncTask<SentenceSelectionTask.Result> {
	static SentenceSelectionTask factory(SentenceSelector.Builder selectorBuilder, int topK) {
		return new SentenceSelectionTask(selectorBuilder, topK);
	}

	private final SentenceSelector.Builder builder;
	private final int topK;

	private SentenceSelectionTask(SentenceSelector.Builder selectorBuilder, int topK) {
		this.builder = selectorBuilder;
		this.topK = topK;
	}


	@Override
	public Result run() {
		SentenceSelector selector;
		Result out;

		try {
			selector = builder.build();
			out = new Result(selector, selector.topK(topK));
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Sentence selection task encountered an error. Exception: " + ex.toString());
			out = new Result(null, new ArrayList<>());
		}

		return out;
	}

	static final class Result {
		final SentenceSelector selector;
		final Collection<? extends SentenceSelector.SelectedSentence> selection;        // ranked list of selected sentences

		Result(SentenceSelector selector, Collection<? extends SentenceSelector.SelectedSentence> selection) {
			this.selector = selector;
			this.selection = selection;
		}
	}
}