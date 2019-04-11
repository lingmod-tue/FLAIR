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
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
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
	private final TokenSequencePattern simpleQuestionPattern;

	private QuestGenTask(ParserAnnotations.Sentence sourceSentence, QuestionGeneratorParams qgParams) {
		this.sourceSentence = sourceSentence;
		this.qgParams = qgParams;
		this.simpleQuestionPattern = TokenSequencePattern.compile("^[{tag:/W.?.?/}] [{tag:/VB.?/}] [] [{tag:/\\./}]$");
	}

	private boolean tokenRegionMatches(List<CoreLabel> lhs, int toffset, List<CoreLabel> rhs, int ooffset, int len) {
		// copy of String.regionMatches
		int to = toffset;
		int po = ooffset;
		// Note: toffset, ooffset, or len might be near -1>>>1.
		if ((ooffset < 0) || (toffset < 0)
				|| (toffset > (long) lhs.size() - len)
				|| (ooffset > (long) rhs.size() - len)) {
			return false;
		}

		while (len-- > 0) {
			CoreLabel t1 = lhs.get(to++);
			CoreLabel t2 = rhs.get(po++);

			String w1 = t1.word();
			String w2 = t2.word();
			if (!w1.equals(w2))
				return false;
		}

		return true;
	}

	private List<String> longestCommonTokenSequence(List<CoreLabel> lhs, List<CoreLabel> rhs) {
		List<CoreLabel> lcs = new ArrayList<>();
		if (lhs.isEmpty() || rhs.isEmpty())
			return new ArrayList<>();
		else if (lhs.size() > rhs.size())
			return longestCommonTokenSequence(rhs, lhs);

		for (int ai = 0; ai < lhs.size(); ai++) {
			for (int len = lhs.size() - ai; len > 0; len--) {
				for (int bi = 0; bi < rhs.size() - len; bi++) {
					if (tokenRegionMatches(lhs, ai, rhs, bi, len) && len > lcs.size())
						lcs = lhs.subList(ai, ai + len);
				}
			}
		}

		if (lcs.isEmpty())
			return new ArrayList<>();
		else
			return lcs.stream().map(CoreLabel::word).collect(Collectors.toList());
	}

	private GeneratedQuestion validateAndPostprocess(Question q) {
		Tree questionTree = q.getTree();
		Tree answerTree = q.getAnswerPhraseTree();

		String questionString = AnalysisUtilities.getCleanedUpYield(questionTree);
		String answerString = AnalysisUtilities.getCleanedUpYield(answerTree);
		StringBuilder answerBuilder = new StringBuilder();

		List<CoreLabel> questionTokens = questionTree.yield().stream().map(CoreLabel.class::cast).collect(Collectors.toList());
		List<CoreLabel> answerTokens = answerTree.yield().stream().map(CoreLabel.class::cast).collect(Collectors.toList());

		if (questionTree.getLeaves().size() > Constants.GENERATOR_MAX_GENERATED_TREE_LEAF_COUNT)
			return null;
		else if (qgParams.onlyWHQuestions && q.getFeatureValue("whQuestion") != 1.0)
			return null;
		else if (qgParams.avoidSimpleQuestions && simpleQuestionPattern.getMatcher(questionTokens).matches()) {
			ServerLogger.get().trace("Skipped simple question '" + questionString + "'");
			return null;
		}

		if (Constants.QUESTGEN_REMOVE_QUESTION_COMMON_TOKEN_SEQUENCE_FROM_ANSWER) {
			// remove common token sequences from the answer string
			List<String> commonTokens = longestCommonTokenSequence(questionTokens, answerTokens);
			String lcs = String.join(" ", commonTokens);
			if (commonTokens.size() > Constants.QUESTGEN_MIN_QUESTION_COMMON_TOKEN_SEQUENCE_LENGTH) {
				int subseqIndex = Collections.indexOfSubList(answerTokens.stream().map(CoreLabel::word).collect(Collectors.toList()), commonTokens);

				if (subseqIndex == -1)
					ServerLogger.get().warn("Question substring '" + lcs + "' unexpectedly missing in answer string '" + answerString + "' (question: " + questionString + ")");
				else {
					List<CoreLabel> substringFreeAnswerTokens = new ArrayList<>(answerTokens.subList(0, subseqIndex));
					substringFreeAnswerTokens.addAll(answerTokens.subList(subseqIndex + commonTokens.size(), answerTokens.size()));
					answerTokens = substringFreeAnswerTokens;

					ServerLogger.get().trace("Removed question substring '" + lcs + "' from answer string '" + answerString + "' (question: " + questionString + ")");
				}
			}
		}

		boolean strippedHead = false;
		for (CoreLabel token : answerTokens) {
			// strip common prepositions, demonstratives and pronouns from the head
			if (!strippedHead) {
				if (EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(token.word().toLowerCase()))
					continue;
				else if (EnglishGrammaticalConstants.SUBJECTIVE_PRONOUNS.contains(token.word().toLowerCase()))
					continue;
				else if (EnglishGrammaticalConstants.SIMPLE_PREPOSITIONS.contains(token.word().toLowerCase()))
					continue;
				else if (EnglishGrammaticalConstants.RELATIVE_PRONOUNS.contains(token.word().toLowerCase()))
					continue;
				else if (EnglishGrammaticalConstants.DEMONSTRATIVES.contains(token.word().toLowerCase()))
					continue;
			}

			strippedHead = true;
			answerBuilder.append(token.word()).append(" ");
		}

		answerString = AnalysisUtilities.cleanUpSentenceString(answerBuilder.toString().trim());
		if (!answerString.isEmpty() && Character.isLowerCase(answerString.codePointAt(0)))
			answerString = answerString.substring(0, 1).toUpperCase() + answerString.substring(1);

		// fix mangled quotes
		questionString = questionString.replaceAll("``", "\"").replaceAll("''", "\"");
		answerString = answerString.replaceAll("``", "\"").replaceAll("''", "\"");

		NerCorefAnnotation corefAnnotation = sourceSentence.data(CoreNlpParserAnnotations.Sentence.class).coreMap().get(NerCorefAnnotation.class);
		return new GeneratedQuestion(qgParams.type,
				corefAnnotation != null ? corefAnnotation.resolvedText() : sourceSentence.text(),
				corefAnnotation != null ? corefAnnotation.originalText() : "",
				questionString, answerString,
				q.getScore(),
				questionTree, answerTree);
	}

	private Tree getQuestionTree() {
		NerCorefAnnotation corefAnnotation = sourceSentence.data(CoreNlpParserAnnotations.Sentence.class).coreMap().get(NerCorefAnnotation.class);
		if (corefAnnotation != null)
			return corefAnnotation.parseTree();
		else
			return sourceSentence.data(CoreNlpParserAnnotations.Sentence.class).parseTree();
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

			List<Tree> inputTrees = Collections.singletonList(getQuestionTree());
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
				Tree questionTree = question.getTree();
				Tree answerTree = question.getAnswerPhraseTree();

				if (questionTree == null) {
					ServerLogger.get().warn("No question tree was generated! Dump: " + question.toString());
					continue;
				} else if (answerTree == null) {
					ServerLogger.get().warn("No answer tree was generated! Dump: " + question.toString());
					continue;
				}

				GeneratedQuestion gq = validateAndPostprocess(question);
				if (gq != null)
					out.generated.add(gq);
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