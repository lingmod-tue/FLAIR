package com.flair.server.pipelines.questgen;

import arkref.parsestuff.AnalysisUtilities;
import com.flair.server.grammar.EnglishGrammaticalConstants;
import com.flair.server.parser.CoreNlpParserAnnotations;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.utilities.ServerLogger;
import edu.cmu.ark.InitialTransformationStep;
import edu.cmu.ark.Question;
import edu.cmu.ark.QuestionRanker;
import edu.cmu.ark.QuestionTransducer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestGenTask implements AsyncTask<QuestGenTask.Result> {
	static QuestGenTask factory(ParserAnnotations.Sentence sourceSentence, QuestionGeneratorParams qgParams) {
		return new QuestGenTask(sourceSentence, qgParams);
	}

	private final ParserAnnotations.Sentence sourceSentence;
	private final QuestionGeneratorParams qgParams;

	private QuestGenTask(ParserAnnotations.Sentence sourceSentence, QuestionGeneratorParams qgParams) {
		this.sourceSentence = sourceSentence;
		this.qgParams = qgParams;
	}

	private GeneratedQuestion postProcess(Question q) {
		/*
		Sentence selection:
			> Prefer shorter sentences (avg of 15 tokens)
		Clean up answers:
			> Remove 'prepositions from the head
			> Filter answers with pronouns (downweight them?)
			> Fix case
			> Remove substrings that are found in the question
			> Remove possessive marker
			> Ignore yes/no questions
	 */
		Tree questionTree = q.getTree();
		Tree answerTree = q.getAnswerPhraseTree();

		String questionString = AnalysisUtilities.getCleanedUpYield(questionTree);
		StringBuilder answerBuilder = new StringBuilder();
		List<CoreLabel> answerTokens = answerTree.yield().stream().map(CoreLabel.class::cast).collect(Collectors.toList());

		for (CoreLabel token : answerTokens) {
			if (EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.stream().anyMatch(e -> token.word().equalsIgnoreCase(e)))
				continue;

			answerBuilder.append(token.word()).append(" ");
		}

		String answerString = AnalysisUtilities.cleanUpSentenceString(answerBuilder.toString().trim());
		if (!answerString.isEmpty() && Character.isLowerCase(answerString.codePointAt(0)))
			answerString = answerString.substring(0, 1).toUpperCase() + answerString.substring(1);

		return new GeneratedQuestion(qgParams.type, sourceSentence.text(),
				questionString, answerString, q.getScore(), questionTree, answerTree);
	}

	@Override
	public Result run() {
		Result out = new Result(qgParams, sourceSentence);

		try {
			QuestionTransducer questionTransducer = new QuestionTransducer();
			InitialTransformationStep initTransformer = new InitialTransformationStep();
			QuestionRanker questionRanker = null;
			if (!qgParams.rankerModelPath.isEmpty()) {
				questionRanker = new QuestionRanker();
				questionRanker.loadModel(qgParams.rankerModelPath);
			}

			questionTransducer.setAvoidPronouns(qgParams.dropPronouns);
			questionTransducer.setAvoidDemonstratives(qgParams.avoidDemonstratives);
			initTransformer.setDoPronounNPC(qgParams.resolvePronounNPs);
			initTransformer.setDoNonPronounNPC(qgParams.resolveNonPronounNPs);

			List<Tree> inputTrees = Collections.singletonList(sourceSentence.data(CoreNlpParserAnnotations.Sentence.class).parseTree());
			List<Question> transformationOutput = initTransformer.transform(inputTrees);
			List<Question> outputQuestionList = new ArrayList<>();

			for (Question question : transformationOutput) {
				questionTransducer.generateQuestionsFromParse(question);
				outputQuestionList.addAll(questionTransducer.getQuestions());
			}

			QuestionTransducer.removeDuplicateQuestions(outputQuestionList);

			if (questionRanker != null && !outputQuestionList.isEmpty()) {
				questionRanker.scoreGivenQuestions(outputQuestionList);
				QuestionRanker.adjustScores(outputQuestionList,
						inputTrees,
						qgParams.downweighFrequentWords,
						qgParams.preferWHQuestions,
						qgParams.downweighPronouns,
						qgParams.doStemming);
				QuestionRanker.sortQuestions(outputQuestionList, false);
			}

			for (Question question : outputQuestionList) {
				if (question.getTree().getLeaves().size() > Constants.GENERATOR_MAX_GENERATED_TREE_LEAF_COUNT)
					continue;

				if (qgParams.onlyWHQuestions && question.getFeatureValue("whQuestion") != 1.0)
					continue;

				Tree questionTree = question.getTree();
				Tree answerTree = question.getAnswerPhraseTree();

				if (questionTree == null) {
					ServerLogger.get().warn("No question tree was generated! Dump: " + question.toString());
					continue;
				} else if (answerTree == null) {
					ServerLogger.get().warn("No answer tree was generated! Dump: " + question.toString());
					continue;
				}

				out.generated.add(postProcess(question));
			}
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Question generation task encountered an error. Exception: " + ex.toString());
		}

		return out;
	}

	static final class Result {
		final QuestionGeneratorParams qgParams;
		final ParserAnnotations.Sentence sourceSentence;
		final List<GeneratedQuestion> generated;        // ordered by their rank (highest first)

		Result(QuestionGeneratorParams qgParams, ParserAnnotations.Sentence sourceSentence) {
			this.qgParams = qgParams;
			this.sourceSentence = sourceSentence;
			this.generated = new ArrayList<>();
		}
	}
}