package com.flair.server.pipelines.exgen;

import java.util.ArrayList;
import java.util.Properties;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.ThreadPool;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.grammar.Language;

import edu.stanford.nlp.util.Lazy;

public final class ExerciseGenerationPipeline {
	private static ExerciseGenerationPipeline SINGLETON = null;

	public static ExerciseGenerationPipeline get() {
		if (SINGLETON == null) {
			synchronized (ExerciseGenerationPipeline.class) {
				if (SINGLETON == null)
					SINGLETON = new ExerciseGenerationPipeline();
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

	private final AsyncExecutorService exGenExecutor;
	private final AsyncExecutorService downloadExecutor;
	
	private final Lazy<CoreNlpParser> parser;
	private final Lazy<SimpleNlgParser> generator;
	private final Lazy<OpenNlpParser> lemmatizer;

	private ExerciseGenerationPipeline() {
		ThreadPool.Builder threadPoolBuilder = ThreadPool.get().builder();
		downloadExecutor = threadPoolBuilder
				.poolSize(Constants.DOWNLOAD_TASK_THREADPOOL_SIZE)
				.poolName("Web page Download")
				.build();		
		exGenExecutor = threadPoolBuilder
				.poolSize(Constants.EXERCISE_GENERATION_TASK_THREADPOOL_SIZE)
				.poolName("Exercise Generation")
				.build();		
		
		CoreNlpParser.Factory parserFactory = CoreNlpParser.factory();
		parser = Lazy.of(() -> parserFactory.create(Language.ENGLISH, initializeAnnotationProperties()));
		
		SimpleNlgParser.Factory generatorFactory = SimpleNlgParser.factory();
        generator = Lazy.of(() -> generatorFactory.create(Language.ENGLISH, null));
        
        OpenNlpParser.Factory lemmatizerFactory = OpenNlpParser.factory();
        lemmatizer = Lazy.of(() -> lemmatizerFactory.create(Language.ENGLISH, null));        
	}

	private Properties initializeAnnotationProperties() {
        Properties pipelineProps = new Properties();

        pipelineProps.put("annotators", "tokenize, ssplit, pos, lemma, parse, depparse");
        pipelineProps.put("parse.originalDependencies", "true");
        pipelineProps.setProperty("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");

        pipelineProps.put("tokenize.options", "tokenizePerLine");
        pipelineProps.put("ssplit.newlineIsSentenceBreak", "two");

        return pipelineProps;
    }
	
	private void shutdown() {
		// the thread pools are shut down elsewhere
		// nothing else to do here
	}

	public final class ExerciseGenerationOpBuilder implements PipelineOp.PipelineOpBuilder<ExerciseGenerationOp.Input, ExerciseGenerationOp.Output> {
		ArrayList<ExerciseSettings> settings;
		ExerciseGenerationOp.ExGenComplete exGenComplete;
		ExerciseGenerationOp.JobComplete jobComplete;
		ResourceDownloader resourceDownloader;

		private ExerciseGenerationOpBuilder() {}

		public ExerciseGenerationOpBuilder settings(ArrayList<ExerciseSettings> settings) {
			this.settings = settings;
			this.resourceDownloader = new ResourceDownloader(settings.get(0).isDownloadResources());
			return this;
		}
		
		public ExerciseGenerationOpBuilder onExGenComplete(ExerciseGenerationOp.ExGenComplete handler) {
			this.exGenComplete = handler;
			return this;
		}
		
		public ExerciseGenerationOpBuilder onComplete(ExerciseGenerationOp.JobComplete handler) {
			this.jobComplete = handler;
			return this;
		}
		

		public PipelineOp<ExerciseGenerationOp.Input, ExerciseGenerationOp.Output> build() {
			if (settings == null)
				throw new IllegalStateException("Invalid exercise settings");
			
			ExerciseGenerationOp.Input input = new ExerciseGenerationOp.Input(settings, downloadExecutor, exGenExecutor, 
					exGenComplete, jobComplete, parser.get(), generator.get(), lemmatizer.get(), resourceDownloader);

			return new ExerciseGenerationOp(input);
		}
	}

	public ExerciseGenerationOpBuilder generateExercises() { return new ExerciseGenerationOpBuilder(); }
}
