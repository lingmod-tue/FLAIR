package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.document.AbstractDocumentFactory;
import com.flair.server.document.Document;
import com.flair.server.grammar.StopwordsList;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.corenlp.StopwordAnnotator;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.ThreadPool;
import com.flair.shared.grammar.Language;
import edu.stanford.nlp.util.Lazy;

import java.util.Properties;

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

	private final AsyncExecutorService nerCorefParseExecutor;
	private final AsyncExecutorService sentenceSelExecutor;
	private final AsyncExecutorService questGenExecutor;
	private final SentenceSelectorPreprocessor sentSelPreprocessor;
	private final AbstractDocumentFactory docFactory;
	private final Lazy<CoreNlpParser> nerCorefParser;

	private QuestionGenerationPipeline() {
		ThreadPool.Builder threadPoolBuilder = ThreadPool.get().builder();
		nerCorefParseExecutor = threadPoolBuilder
				.poolSize(Constants.PARSE_TASK_THREADPOOL_SIZE)
				.poolName("NER/Coref Parse")
				.build();
		sentenceSelExecutor = threadPoolBuilder
				.poolSize(Constants.SENTENCESEL_TASK_THREADPOOL_SIZE)
				.poolName("Sentence Sel")
				.build();
		questGenExecutor = threadPoolBuilder
				.poolSize(Constants.QUESTGEN_TASK_THREADPOOL_SIZE)
				.poolName("Question Gen")
				.build();

		sentSelPreprocessor = new SentenceSelectorPreprocessor();
		docFactory = Document.factory();

		Properties pipelineProps = new Properties();
		pipelineProps.put("annotators", "tokenize, ssplit, pos, lemma, stopword, parse, ner, mention, coref");
		pipelineProps.put("tokenize.options", "tokenizePerLine");
		pipelineProps.put("ssplit.newlineIsSentenceBreak", "two");
		pipelineProps.put("parse.originalDependencies", "true");
		pipelineProps.put("parse.model", com.flair.server.parser.corenlp.Constants.ENGLISH_SR_PARSER_MODEL);
		pipelineProps.put("ner.applyFineGrained", "false");
		pipelineProps.put("coref.algorithm", "neural");
		pipelineProps.put("coref.maxMentionDistance", "20");
		pipelineProps.put("coref.maxMentionDistanceWithStringMatch", "200");
		StringBuilder stopwords = new StringBuilder();
		StopwordsList.get(Language.ENGLISH).forEach(e -> stopwords.append(e).append(","));
		pipelineProps.setProperty("customAnnotatorClass.stopword", "com.flair.server.parser.corenlp.StopwordAnnotator");
		pipelineProps.setProperty(StopwordAnnotator.STOPWORDS_LIST, stopwords.toString());
		pipelineProps.setProperty(StopwordAnnotator.IGNORE_STOPWORD_CASE, "true");

		this.nerCorefParser = Lazy.of(() -> CoreNlpParser.factory().create(Language.ENGLISH, pipelineProps));
	}

	private void shutdown() {
		// the thread pools are shutdown elsewhere
		// nothing else to do here
	}

	public final class QuestionGenerationOpBuilder implements PipelineOp.PipelineOpBuilder<QuestionGenerationOp.Input, QuestionGenerationOp.Output> {
		AbstractDocument sourceDoc;
		int numQuestions;

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

		public QuestionGenerationOpBuilder numQuestions(int numQuestions) {
			this.numQuestions = numQuestions;
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
			else if (sourceDoc.getLanguage() != Language.ENGLISH)
				throw new IllegalStateException("Unsupported language " + sourceDoc.getLanguage() + " for questgen");
			else if (numQuestions == 0)
				throw new IllegalStateException("Invalid number of source sentences");

			// create a copy of the source doc for the NER/Coref parsing
			AbstractDocument newDoc = docFactory.create(sourceDoc.getDocumentSource());
			QuestionGenerationOp.Input input = new QuestionGenerationOp.Input(newDoc,
					ParsingStrategy.factory().create(new ParsingStrategy.ParserInput(newDoc)),
					nerCorefParser.get(),
					nerCorefParseExecutor,
					sentenceSelExecutor,
					questGenExecutor,
					qgParams.build(),
					sentSelPreprocessor,
					numQuestions,
					selectionComplete,
					jobComplete);

			return new QuestionGenerationOp(input);
		}
	}

	public QuestionGenerationOpBuilder generateQuestions() { return new QuestionGenerationOpBuilder(); }
}
