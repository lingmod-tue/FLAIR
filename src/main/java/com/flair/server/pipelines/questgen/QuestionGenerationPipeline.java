package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.ThreadPool;

public final class QuestionGenerationPipeline {
	private static QuestionGenerationPipeline SINGLETON = null;

	public static QuestionGenerationPipeline get() {
		if (SINGLETON == null) {
			synchronized (QuestionGenerationPipeline.class) {
				if (SINGLETON == null)
					SINGLETON = new QuestionGenerationPipeline();
			}
		}

		return SINGLETON;
	}

	public static void dispose() {
		if (SINGLETON != null) {
			SINGLETON.shutdown();
			SINGLETON = null;
		}
	}

	private final AsyncExecutorService sentenceSelExecutor;
	private final AsyncExecutorService questGenExecutor;
	private final SentenceSelectorPreprocessor sentSelPreprocessor;

	private QuestionGenerationPipeline() {
		ThreadPool.Builder threadPoolBuilder = ThreadPool.get().builder();
		sentenceSelExecutor = threadPoolBuilder
				.poolSize(Constants.SENTENCESEL_TASK_THREADPOOL_SIZE)
				.poolName("Sentence Sel")
				.build();
		questGenExecutor = threadPoolBuilder
				.poolSize(Constants.QUESTGEN_TASK_THREADPOOL_SIZE)
				.poolName("Question Gen")
				.build();

		sentSelPreprocessor = new SentenceSelectorPreprocessor();
	}

	private void shutdown() {
		// the thread pools are shutdown elsewhere
		// nothing else to do here
	}

	public final class QuestionGenerationOpBuilder implements PipelineOp.PipelineOpBuilder<QuestionGenerationOp.Input, QuestionGenerationOp.Output> {
		AbstractDocument sourceDoc;
		int numSelectedSentences;

		QuestionGenerationOp.SentenceSelectionComplete selectionComplete;
		QuestionGenerationOp.JobComplete jobComplete;

		QuestionGeneratorParams.Builder qgParams = QuestionGeneratorParams.Builder.factory();

		private QuestionGenerationOpBuilder() {}

		public QuestionGenerationOpBuilder sourceDoc(AbstractDocument sourceDoc) {
			this.sourceDoc = sourceDoc;
			return this;
		}

		public QuestionGeneratorParams.Builder qgParams() {
			return qgParams;
		}

		public QuestionGenerationOpBuilder numSelectedSentences(int numSelectedSentences) {
			this.numSelectedSentences = numSelectedSentences;
			return this;
		}

		public QuestionGenerationOpBuilder onSelectionComplete(QuestionGenerationOp.SentenceSelectionComplete handler) {
			this.selectionComplete = handler;
			return this;
		}

		public QuestionGenerationOpBuilder onComplete(QuestionGenerationOp.JobComplete handler) {
			this.jobComplete = handler;
			return this;
		}

		public PipelineOp<QuestionGenerationOp.Input, QuestionGenerationOp.Output> launch() {
			if (sourceDoc == null)
				throw new IllegalStateException("Invalid source document");
			else if (numSelectedSentences == 0)
				throw new IllegalStateException("Invalid number of source sentences");

			QuestionGenerationOp.Input input = new QuestionGenerationOp.Input(sourceDoc,
					sentenceSelExecutor,
					questGenExecutor,
					qgParams.build(),
					sentSelPreprocessor,
					numSelectedSentences,
					selectionComplete,
					jobComplete);

			return new QuestionGenerationOp(input);
		}
	}

	public QuestionGenerationOpBuilder generateQuestions() { return new QuestionGenerationOpBuilder(); }
}
