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
		final int numQuestions;

		final SentenceSelectionComplete selectionComplete;
		final JobComplete jobComplete;

		Input(AbstractDocument sourceDoc,
		      AsyncExecutorService sentSelExecutor,
		      AsyncExecutorService qgExecutor,
		      QuestionGeneratorParams qgParams,
		      SentenceSelectorPreprocessor sentSelPreprocessor,
		      int numQuestions,
		      SentenceSelectionComplete selectionComplete,
		      JobComplete jobComplete) {
			this.sourceDoc = sourceDoc;
			this.sentSelExecutor = sentSelExecutor;
			this.qgExecutor = qgExecutor;
			this.qgParams = qgParams;
			this.sentSelPreprocessor = sentSelPreprocessor;
			this.numQuestions = numQuestions;
			this.selectionComplete = selectionComplete != null ? selectionComplete : e -> {};
			this.jobComplete = jobComplete != null ? jobComplete : e -> {};
		}
	}

	public static final class Output {
		public final List<GeneratedQuestion> generatedQuestions;

		Output() {
			this.generatedQuestions = new ArrayList<>();
		}
	}

	@Override
	protected String desc() {
		return name + " Output:\nInput:\n\tSource Doc: " + input.sourceDoc.getDescription() + "\n\tSelected Sentences: "
				+ input.numQuestions + "\n\tQuestion Type: " + input.qgParams.type
				+ "\nOutput\n\t\n\tGenerated Questions: " + output.generatedQuestions.size();
	}

	private void queueQuestGenTask(AsyncJob jerb) {
		// keep generating questions until we exhaust our source sentences or have generated enough
		if (output.generatedQuestions.size() >= input.numQuestions)
			return;
		else if (numQuestGenTasks > 0)
			return;

		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(jerb);
		int delta = input.numQuestions - output.generatedQuestions.size();
		for (int i = 0; i < delta && nextSentenceIndex < rankedSentences.size(); i++) {
			// ### we only support single sentences currently
			List<ParserAnnotations.Sentence> chunks = new ArrayList<>();
			chunks.add(rankedSentences.get(nextSentenceIndex).annotation());

			scheduler.newTask(QuestGenTask.factory(chunks, input.qgParams))
					.with(input.qgExecutor)
					.then(this::linkTasks)
					.queue();

			nextSentenceIndex++;
			numQuestGenTasks++;
		}

		if (scheduler.hasTasks())
			scheduler.fire();
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(SentenceSelectionTask.Result.class, (j, r) -> {
			rankedSentences = new ArrayList<>(r.selection);
			input.selectionComplete.handle(rankedSentences.subList(0, rankedSentences.size() < input.numQuestions ? rankedSentences.size() : input.numQuestions));

			queueQuestGenTask(j);
		});

		taskLinker.addHandler(QuestGenTask.Result.class, (j, r) -> {
			// pick one of the top-k questions randomly
			// the relative order of the generated questions is indeterminate but that's fine for our purposes
			numQuestGenTasks--;

			if (!r.generated.isEmpty()) {
				int numGeneratedQuestions = r.generated.size();
				GeneratedQuestion randomPick = r.generated.get(ThreadLocalRandom.current().nextInt(0,
						Constants.QUESTGEN_BESTQPOOL_SIZE > numGeneratedQuestions ? numGeneratedQuestions : Constants.QUESTGEN_BESTQPOOL_SIZE));
				output.generatedQuestions.add(randomPick);
			}

			queueQuestGenTask(j);
		});
	}

	private List<? extends SentenceSelector.SelectedSentence> rankedSentences;
	private int nextSentenceIndex;
	private int numQuestGenTasks;

	QuestionGenerationOp(Input input) {
		super("QuestionGenerationOp", input, new Output());
		nextSentenceIndex = 0;
		numQuestGenTasks = 0;
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
				.stemWords(true)
				.ignoreStopwords(true)
				.useSynsets(false);

		scheduler.newTask(SentenceSelectionTask.factory(ssBuilder, -1))
				.with(input.sentSelExecutor)
				.then(this::linkTasks)
				.queue();

		this.job = scheduler.fire();
	}
}
