package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.server.utilities.ServerLogger;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

	private boolean queueQuestGenTask(AsyncJob jerb) {
		if (numQuestGenTasks > 0)
			return false;

		// keep generating questions until we exhaust our source sentences or have generated enough
		// generate at least as many questions as there are distractors (plus one)
		if (rankedQuestions.size() > Constants.QUESTGEN_NUM_DISTRACTOR && output.generatedQuestions.size() >= input.numQuestions)
			return false;

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

		return scheduler.hasTasks();
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(SentenceSelectionTask.Result.class, (j, r) -> {
			rankedSentences = new ArrayList<>(r.selection);
			rankedSentenceAnnotations = rankedSentences.stream().map(SentenceSelector.SelectedSentence::annotation).collect(Collectors.toList());
			input.selectionComplete.handle(rankedSentences.subList(0, rankedSentences.size() < input.numQuestions ? rankedSentences.size() : input.numQuestions));

			queueQuestGenTask(j);
		});

		taskLinker.addHandler(QuestGenTask.Result.class, (j, r) -> {
			numQuestGenTasks--;

			ParserAnnotations.Sentence source = r.sourceSentChunk.get(0);
			List<GeneratedQuestion> questions = new ArrayList<>();
			for (GeneratedQuestion q : r.generated) {
				// skip questions that don't have an answer
				if (q.answer.isEmpty())
					continue;
				questions.add(q);
			}

			if (!questions.isEmpty()) {
				List<GeneratedQuestion> existing = rankedQuestions.put(source, questions);
				if (existing != null)
					ServerLogger.get().warn("Multiple QuestGen tasks were spawned for sentence '" + source.toString() + "'. Overwriting old values");
			}

			queueQuestGenTask(j);
			if (numQuestGenTasks == 0) {
				// no more questgen tasks queued, collect generated questions and their distractors
				Set<String> distractors = rankedQuestions.values().stream().flatMap(v -> v.stream().map(w -> w.answer)).collect(Collectors.toSet());
				for (ParserAnnotations.Sentence sent : rankedSentenceAnnotations) {
					if (output.generatedQuestions.size() >= input.numQuestions)
						break;

					questions = rankedQuestions.get(sent);
					if (questions == null)
						continue;

					// randomly pick one of the generated questions for the sentence
					// ### TODO rank the questions correctly and pick the top one
					int numGeneratedQuestions = questions.size();
					int randomIndex = ThreadLocalRandom.current().nextInt(0,
							Constants.QUESTGEN_BESTQPOOL_SIZE > numGeneratedQuestions ? numGeneratedQuestions : Constants.QUESTGEN_BESTQPOOL_SIZE);
					GeneratedQuestion randomPick = questions.get(randomIndex);

					// randomly pick unique distractors from candidates collected from all generated sentences (with an answer)
					List<String> candidateDistractors = distractors.stream().filter(v -> !v.equalsIgnoreCase(randomPick.answer)).collect(Collectors.toList());
					Collections.shuffle(candidateDistractors);
					randomPick.setDistractors(candidateDistractors.subList(0, Constants.QUESTGEN_NUM_DISTRACTOR > candidateDistractors.size() ? candidateDistractors.size() : Constants.QUESTGEN_NUM_DISTRACTOR));
					if (randomPick.distractors.size() < Constants.QUESTGEN_NUM_DISTRACTOR)
						ServerLogger.get().warn("Question '" + randomPick.question + "' has only " + randomPick.distractors.size() + "/" + Constants.QUESTGEN_NUM_DISTRACTOR + " distractors!");

					output.generatedQuestions.add(randomPick);
				}
			}
		});
	}

	private List<? extends SentenceSelector.SelectedSentence> rankedSentences;
	private List<ParserAnnotations.Sentence> rankedSentenceAnnotations;
	private final Map<ParserAnnotations.Sentence, List<GeneratedQuestion>> rankedQuestions;
	private int nextSentenceIndex;
	private int numQuestGenTasks;

	QuestionGenerationOp(Input input) {
		super("QuestionGenerationOp", input, new Output());
		nextSentenceIndex = 0;
		numQuestGenTasks = 0;
		rankedQuestions = new HashMap<>();
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
