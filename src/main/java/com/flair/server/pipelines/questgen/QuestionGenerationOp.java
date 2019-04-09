package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.server.sentencesel.SentenceSelector;
import com.flair.server.utilities.ServerLogger;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionGenerationOp extends PipelineOp<QuestionGenerationOp.Input, QuestionGenerationOp.Output> {
	public interface SentenceSelectionComplete extends EventHandler<Collection<? extends SentenceSelector.SelectedSentence>> {}

	public interface JobComplete extends EventHandler<QuestionGenerationOp.Output> {}

	static final class Input {
		final AbstractDocument sourceDoc;

		final ParsingStrategy parsingStrategy;
		final CoreNlpParser parser;

		final AsyncExecutorService parseExecutor;
		final AsyncExecutorService sentSelExecutor;
		final AsyncExecutorService qgExecutor;

		final QuestionGeneratorParams qgParams;
		final SentenceSelector.Builder sentSelBuilder;
		final int numQuestions;

		final SentenceSelectionComplete selectionComplete;
		final JobComplete jobComplete;

		Input(AbstractDocument sourceDoc,
		      ParsingStrategy parsingStrategy,
		      CoreNlpParser parser,
		      AsyncExecutorService parseExecutor,
		      AsyncExecutorService sentSelExecutor,
		      AsyncExecutorService qgExecutor,
		      QuestionGeneratorParams qgParams,
		      SentenceSelector.Builder sentSelBuilder,
		      int numQuestions,
		      SentenceSelectionComplete selectionComplete,
		      JobComplete jobComplete) {
			this.sourceDoc = sourceDoc;
			this.parsingStrategy = parsingStrategy;
			this.parser = parser;
			this.parseExecutor = parseExecutor;
			this.sentSelExecutor = sentSelExecutor;
			this.qgExecutor = qgExecutor;
			this.qgParams = qgParams;
			this.sentSelBuilder = sentSelBuilder;
			this.numQuestions = numQuestions;
			this.selectionComplete = selectionComplete != null ? selectionComplete : e -> {};
			this.jobComplete = jobComplete != null ? jobComplete : e -> {};
		}
	}

	public static final class Output {
		public final AbstractDocument sourceDoc;
		public final List<GeneratedQuestion> generatedQuestions;

		Output(AbstractDocument sourceDoc) {
			this.sourceDoc = sourceDoc;
			this.generatedQuestions = new ArrayList<>();
		}
	}

	@Override
	protected String desc() {
		return name + " Output:\nInput:\n\tSource Doc: " + input.sourceDoc.getDescription() + "\n\tSelected Sentences: "
				+ input.numQuestions + "\n\tQuestion Type: " + input.qgParams.type
				+ "\nOutput\n\t\n\tGenerated Questions: " + output.generatedQuestions.size();
	}

	private void queueSentenceSelTask(AsyncJob jerb, AbstractDocument sourceDoc) {
		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(jerb);
		input.sentSelBuilder
				.source(SentenceSelector.Source.DOCUMENT)
				.mainDocument(sourceDoc)
				.granularity(SentenceSelector.Granularity.SENTENCE)
				.stemWords(true)
				.ignoreStopwords(true)
				.useSynsets(false)
				.dropDuplicates(true)
				.duplicateCooccurrenceThreshold(Constants.SENTENCESEL_DUPLICATE_COOCCURRENCE_THRESHOLD);

		scheduler.newTask(SentenceSelectionTask.factory(input.sentSelBuilder, -1))
				.with(input.sentSelExecutor)
				.then(this::linkTasks)
				.queue();

		scheduler.fire();
	}

	private void queueQuestGenTask(AsyncJob jerb) {
		if (numQuestGenTasks > 0)
			return;

		float percentProcessedSents = numProcessedSentences / (float) rankedSentences.size() * 100.f;
		if (numProcessedSentences >= input.numQuestions && percentProcessedSents >= Constants.QUESTGEN_OVERGENERATION_PERCENTAGE)
			return;

		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(jerb);
		int delta = Math.min((int) ((Constants.QUESTGEN_OVERGENERATION_PERCENTAGE - percentProcessedSents) * rankedSentences.size()),
				rankedSentences.size() - numProcessedSentences) + 1;
		for (int i = 0; i < delta && nextSentenceIndex < rankedSentences.size(); ++i) {
			scheduler.newTask(QuestGenTask.factory(rankedSentences.get(nextSentenceIndex).annotation(), input.qgParams))
					.with(input.qgExecutor)
					.then(this::linkTasks)
					.queue();

			++nextSentenceIndex;
			++numQuestGenTasks;
			++numProcessedSentences;
		}

		if (scheduler.hasTasks())
			scheduler.fire();
	}

	private void assignDistractorsAndPickQuestions() {
		Map<String, List<String>> firstQuestionWordToAnswers = rankedQuestions.values()
				.stream().flatMap(Collection::stream).collect(Collectors.toList())
				.stream().collect(Collectors.groupingBy(q -> q.questionTree.yield().get(0).value().toLowerCase()))
				.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().map(q -> q.answer).distinct().collect(Collectors.toList())));

		for (ParserAnnotations.Sentence sent : rankedSentenceAnnotations) {
			if (output.generatedQuestions.size() >= input.numQuestions)
				break;

			List<GeneratedQuestion> questions = rankedQuestions.get(sent);
			if (questions == null)
				continue;

			// randomly pick one of the generated questions for the sentence
			// ### TODO can we do better here?
			int numGeneratedQuestions = questions.size();
			int randomIndex = ThreadLocalRandom.current().nextInt(0,
					Constants.QUESTGEN_BESTQPOOL_SIZE > numGeneratedQuestions ? numGeneratedQuestions : Constants.QUESTGEN_BESTQPOOL_SIZE);
			GeneratedQuestion randomPick = questions.get(randomIndex);

			// randomly pick unique distractors from candidate answers grouped by their first question word
			String questionWord = randomPick.questionTree.yield().get(0).value().toLowerCase();
			List<String> candidateDistractors = firstQuestionWordToAnswers.get(questionWord);
			if (candidateDistractors == null) {
				ServerLogger.get().warn("No distractors for question word in question '" + randomPick.question + "'");
				continue;
			}

			candidateDistractors = candidateDistractors.stream().filter(e -> !e.equalsIgnoreCase(randomPick.answer)).collect(Collectors.toList());
			Collections.shuffle(candidateDistractors);
			randomPick.setDistractors(candidateDistractors.subList(0, Constants.QUESTGEN_NUM_DISTRACTORS > candidateDistractors.size() ?
					candidateDistractors.size() : Constants.QUESTGEN_NUM_DISTRACTORS));

			if (randomPick.distractors.size() < Constants.QUESTGEN_NUM_DISTRACTORS) {
				// pick random ones from the other distractors
				List<String> alternateDistractors = firstQuestionWordToAnswers.entrySet().stream().filter(e -> !e.getKey().equals(questionWord)).collect(Collectors.toList())
						.stream().flatMap(e -> e.getValue().stream()).collect(Collectors.toList());
				Collections.shuffle(alternateDistractors);
				candidateDistractors = Stream.concat(candidateDistractors.stream(), alternateDistractors.stream()).collect(Collectors.toList());
				randomPick.setDistractors(candidateDistractors.subList(0, Constants.QUESTGEN_NUM_DISTRACTORS > candidateDistractors.size() ?
						candidateDistractors.size() : Constants.QUESTGEN_NUM_DISTRACTORS));

				if (randomPick.distractors.size() < Constants.QUESTGEN_NUM_DISTRACTORS) {
					ServerLogger.get().warn("Question '" + randomPick.question + "' has only " + randomPick.distractors.size()
							+ "/" + Constants.QUESTGEN_NUM_DISTRACTORS + " distractors!");
				}
			}

			output.generatedQuestions.add(randomPick);
		}
	}

	private void onParseComplete(AsyncJob j, NerCorefParseTask.Result r) {
		if (r.output != null)
			queueSentenceSelTask(j, r.output);
	}

	private void onSentenceSelectionComplete(AsyncJob j, SentenceSelectionTask.Result r) {
		rankedSentences = new ArrayList<>(r.selection);
		rankedSentenceAnnotations = rankedSentences.stream().map(SentenceSelector.SelectedSentence::annotation).collect(Collectors.toList());
		input.selectionComplete.handle(rankedSentences);

		queueQuestGenTask(j);
	}

	private void onQuestGenComplete(AsyncJob j, QuestGenTask.Result r) {
		numQuestGenTasks--;

		ParserAnnotations.Sentence source = r.sourceSentence;
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
			assignDistractorsAndPickQuestions();
		}
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(NerCorefParseTask.Result.class, this::onParseComplete);
		taskLinker.addHandler(SentenceSelectionTask.Result.class, this::onSentenceSelectionComplete);
		taskLinker.addHandler(QuestGenTask.Result.class, this::onQuestGenComplete);
	}

	private List<? extends SentenceSelector.SelectedSentence> rankedSentences;
	private List<ParserAnnotations.Sentence> rankedSentenceAnnotations;
	private final Map<ParserAnnotations.Sentence, List<GeneratedQuestion>> rankedQuestions;
	private int nextSentenceIndex;
	private int numQuestGenTasks;
	private int numProcessedSentences;

	QuestionGenerationOp(Input input) {
		super("QuestionGenerationOp", input, new Output(input.sourceDoc));
		nextSentenceIndex = 0;
		numQuestGenTasks = 0;
		numProcessedSentences = 0;
		rankedQuestions = new HashMap<>();
		initTaskSyncHandlers();

		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;

			input.jobComplete.handle(output);
		});


		scheduler.newTask(NerCorefParseTask.factory(input.parsingStrategy, input.parser))
				.with(input.parseExecutor)
				.then(this::linkTasks)
				.queue();

		this.job = scheduler.fire();
	}
}
