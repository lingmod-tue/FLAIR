package com.flair.server.pipelines.questgen;

import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.Collection;

public class GeneratedQuestion {
	public final QuestionKind type;
	public final String sourceSentence;
	public final String question;
	public final String answer;
	public final ArrayList<String> distractors;
	public final double score;
	public final Tree questionTree;
	public final Tree answerTree;

	GeneratedQuestion(QuestionKind type,
	                  String sourceSentence,
	                  String question, String answer,
	                  double score,
	                  Tree questionTree,
	                  Tree answerTree) {
		this.type = type;
		this.sourceSentence = sourceSentence;
		this.question = question;
		this.answer = answer;
		this.distractors = new ArrayList<>();
		this.score = score;
		this.questionTree = questionTree;
		this.answerTree = answerTree;
	}

	void setDistractors(Collection<String> distractors) {
		this.distractors.clear();
		this.distractors.addAll(distractors);
	}
}
