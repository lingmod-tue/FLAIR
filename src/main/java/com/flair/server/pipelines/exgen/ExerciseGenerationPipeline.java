package com.flair.server.pipelines.exgen;

import java.util.ArrayList;

import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.ThreadPool;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

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

	private ExerciseGenerationPipeline() {
		ThreadPool.Builder threadPoolBuilder = ThreadPool.get().builder();
		downloadExecutor = threadPoolBuilder
				.poolSize(50)
				.poolName("Web page Download")
				.build();		
		exGenExecutor = threadPoolBuilder
				.poolSize(50)
				.poolName("Exercise Generation")
				.build();		
	}

	private void shutdown() {
		// the thread pools are shut down elsewhere
		// nothing else to do here
	}

	public final class ExerciseGenerationOpBuilder implements PipelineOp.PipelineOpBuilder<ExerciseGenerationOp.Input, ExerciseGenerationOp.Output> {
		ArrayList<ExerciseSettings> settings;
		ExerciseGenerationOp.ExGenComplete exGenComplete;
		ExerciseGenerationOp.JobComplete jobComplete;

		private ExerciseGenerationOpBuilder() {}

		public ExerciseGenerationOpBuilder settings(ArrayList<ExerciseSettings> settings) {
			this.settings = settings;
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
			
			ExerciseGenerationOp.Input input = new ExerciseGenerationOp.Input(settings, downloadExecutor, exGenExecutor, exGenComplete, jobComplete);

			return new ExerciseGenerationOp(input);
		}
	}

	public ExerciseGenerationOpBuilder generateExercises() { return new ExerciseGenerationOpBuilder(); }
}
