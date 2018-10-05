package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionGenerationOp extends PipelineOp<QuestionGenerationOp.Input, QuestionGenerationOp.Output> {
	public interface SentenceSelectionComplete extends EventHandler<Collection<? extends SentenceSelector.SelectedSentence>> {}

	public interface JobComplete extends EventHandler<Collection<GeneratedQuestion>> {}

	static final class Input {
		final AbstractDocument sourceDoc;

		final AsyncExecutorService sentSelExecutor;
		final AsyncExecutorService qgExecutor;

		final QuestionGeneratorParams qgParams;
		final SentenceSelectorPreprocessor sentSelPreprocessor;
		final int numSelectedSentences;

		final SentenceSelectionComplete selectionComplete;
		final JobComplete jobComplete;

		Input(AbstractDocument sourceDoc,
		      AsyncExecutorService sentSelExecutor,
		      AsyncExecutorService qgExecutor,
		      QuestionGeneratorParams qgParams,
		      SentenceSelectorPreprocessor sentSelPreprocessor,
		      int numSelectedSentences,
		      SentenceSelectionComplete selectionComplete,
		      JobComplete jobComplete) {
			this.sourceDoc = sourceDoc;
			this.sentSelExecutor = sentSelExecutor;
			this.qgExecutor = qgExecutor;
			this.qgParams = qgParams;
			this.sentSelPreprocessor = sentSelPreprocessor;
			this.numSelectedSentences = numSelectedSentences;
			this.selectionComplete = selectionComplete;
			this.jobComplete = jobComplete;
		}
	}

	static final class Output {
		final List<GeneratedQuestion> generatedQuestions;

		Output() {
			this.generatedQuestions = new ArrayList<>();
		}
	}

	@Override
	protected String desc() {
		return name + " Output:\nInput:\n\tSource Doc: " + input.sourceDoc.getDescription() + "\n\tSelected Sentences: "
				+ input.numSelectedSentences + "\n\tQuestion Type: " + input.qgParams.type
				+ "\nOutput\n\t\n\tGenerated Questions: " + output.generatedQuestions.size();
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(SentenceSelectionTask.Result.class, (j, r) -> {
			input.selectionComplete.handle(r.selection);

			AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(j);
			for (SentenceSelector.SelectedSentence itr : r.selection) {
				// ### we only support single sentences currently
				List<ParserAnnotations.Sentence> chunks = new ArrayList<>();
				chunks.add(itr.annotation());

				scheduler.newTask(QuestGenTask.factory(chunks, input.qgParams))
						.with(input.qgExecutor)
						.then(this::linkTasks)
						.queue();
			}

			if (scheduler.hasTasks())
				scheduler.fire();
		});

		taskLinker.addHandler(QuestGenTask.Result.class, (j, r) -> {
			// pick one of the top-k questions randomly
			int numGeneratedQuestions = r.generated.size();
			GeneratedQuestion randomPick = r.generated.get(ThreadLocalRandom.current().nextInt(0,
					Constants.QUESTGEN_BESTQPOOL_SIZE > numGeneratedQuestions ? numGeneratedQuestions : Constants.QUESTGEN_BESTQPOOL_SIZE));
			output.generatedQuestions.add(randomPick);
		});
	}

	QuestionGenerationOp(Input input) {
		super("QuestionGenerationOp", input, new Output());
		initTaskSyncHandlers();

		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;

			input.jobComplete.handle(output.generatedQuestions);
		});

		SentenceSelector.Builder ssBuilder = TextRankSentenceSelector.Builder.factory(input.sourceDoc.getLanguage(), input.sentSelPreprocessor)
				.source(SentenceSelector.Source.DOCUMENT)
				.mainDocument(input.sourceDoc)
				.granularity(SentenceSelector.Granularity.SENTENCE)
				.ignoreStopwords(true)
				.useSynsets(false);

		scheduler.newTask(SentenceSelectionTask.factory(ssBuilder, input.numSelectedSentences))
				.with(input.sentSelExecutor)
				.then(this::linkTasks)
				.queue();

		this.job = scheduler.fire();
	}
}
