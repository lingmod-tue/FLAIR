package com.flair.server.pipelines.exgen;

import java.util.ArrayList;

import com.flair.server.crawler.WebSearchAgent;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public class ExerciseGenerationOp extends PipelineOp<ExerciseGenerationOp.Input, ExerciseGenerationOp.Output> {
	public interface ExGenComplete extends EventHandler<byte[]> {}
	public interface JobComplete extends EventHandler<byte[]> {}

	static final class Input {
		final ArrayList<ExerciseSettings> settings;
		final AsyncExecutorService exGenExecutor;
		final ExGenComplete exGenComplete;
		final JobComplete jobComplete;

		Input(ArrayList<ExerciseSettings> settings,
		      AsyncExecutorService exGenExecutor,
		      ExGenComplete exGenComplete,
		      JobComplete jobComplete) {
			this.settings = settings;			
			this.exGenExecutor = exGenExecutor;
			this.exGenComplete = exGenComplete != null ? exGenComplete : e -> {};
			this.jobComplete = jobComplete != null ? jobComplete : e -> {};
		}
	}

	public static final class Output {
		public final byte[] file;

		Output() {
			this.file = new byte[] {};
		}
	}

	private WebSearchAgent searchAgent;

	@Override
	protected String desc() {
		return name;
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(ExGenTask.Result.class, (j, r) -> {
			if (r.file != null) {
				safeInvoke(() -> input.exGenComplete.handle(r.file),
						"Exception in generation complete handler");
			}
		});
	}

	ExerciseGenerationOp(Input input) {
		super("ExerciseGenerationOp", input, new Output());
		/*this.searchAgent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING,
				input.sourceLanguage,
				input.query);*/
		initTaskSyncHandlers();
	}

	@Override
	public PipelineOp<Input, Output> launch() {
		super.launch();

		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;

			safeInvoke(() -> input.jobComplete.handle(output.file),
					"Exception in job complete handler");
				})
				.newTask(ExGenTask.factory(input.settings))
				.with(input.exGenExecutor)
				.then(this::linkTasks)
				.queue();

		this.job = scheduler.fire();
		return this;
	}
}
