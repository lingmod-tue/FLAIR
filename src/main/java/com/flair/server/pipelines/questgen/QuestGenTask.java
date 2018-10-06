package com.flair.server.pipelines.questgen;

import arkref.parsestuff.AnalysisUtilities;
import com.flair.server.parser.CoreNlpParserAnnotations;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.utilities.ServerLogger;
import edu.cmu.ark.InitialTransformationStep;
import edu.cmu.ark.Question;
import edu.cmu.ark.QuestionRanker;
import edu.cmu.ark.QuestionTransducer;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestGenTask implements AsyncTask<QuestGenTask.Result> {
	static QuestGenTask factory(List<ParserAnnotations.Sentence> sentenceChunk, QuestionGeneratorParams qgParams) {
		return new QuestGenTask(sentenceChunk, qgParams);
	}

	private final List<ParserAnnotations.Sentence> sourceSentChunk;
	private final QuestionGeneratorParams qgParams;

	private QuestGenTask(List<ParserAnnotations.Sentence> sentenceChunk, QuestionGeneratorParams qgParams) {
		this.sourceSentChunk = sentenceChunk;
		this.qgParams = qgParams;
	}


	@Override
	public Result run() {
		Result out = new Result(qgParams, sourceSentChunk);

		try {
			QuestionTransducer questionTransducer = new QuestionTransducer();
			InitialTransformationStep initTransformer = new InitialTransformationStep();
			QuestionRanker questionRanker = null;
			if (!qgParams.rankerModelPath.isEmpty()) {
				questionRanker = new QuestionRanker();
				questionRanker.loadModel(qgParams.rankerModelPath);
			}

			List<Tree> inputTrees = sourceSentChunk.stream().map(s -> s.data(CoreNlpParserAnnotations.Sentence.class).parseTree()).collect(Collectors.toList());
			List<Question> transformationOutput = initTransformer.transform(inputTrees);
			List<Question> outputQuestionList = new ArrayList<>();

			for (Question question : transformationOutput) {
				questionTransducer.generateQuestionsFromParse(question);
				outputQuestionList.addAll(questionTransducer.getQuestions());
			}

			QuestionTransducer.removeDuplicateQuestions(outputQuestionList);

			if (questionRanker != null) {
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

				if (questionTree == null)
					ServerLogger.get().warn("No question tree was generated! Dump: " + question.toString());

				String questionString = AnalysisUtilities.getCleanedUpYield(questionTree);
				String answerString = answerTree != null ? AnalysisUtilities.getCleanedUpYield(answerTree) : "";

				out.generated.add(new GeneratedQuestion(qgParams.type, questionString, answerString));
			}
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Question generation task encountered an error. Exception: " + ex.toString());
		}

		return out;
	}

	static final class Result {
		final QuestionGeneratorParams qgParams;
		final List<ParserAnnotations.Sentence> sourceSentChunk;
		final List<GeneratedQuestion> generated;        // ordered by their rank (highest first)

		Result(QuestionGeneratorParams qgParams, List<ParserAnnotations.Sentence> s) {
			this.qgParams = qgParams;
			this.sourceSentChunk = s;
			this.generated = new ArrayList<>();
		}
	}
}