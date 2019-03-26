package com.flair.server.pipelines.questgen;

import java.util.ArrayList;
import java.util.Collection;

public class GeneratedQuestion {
	public final QuestionKind type;
	public final String question;
	public final String answer;
	public final ArrayList<String> distractors;

	public GeneratedQuestion(QuestionKind type, String question, String answer) {
		this.type = type;
		this.question = question;
		this.answer = answer;
		this.distractors = new ArrayList<>();
	}

	public void setDistractors(Collection<String> distractors) {
		this.distractors.clear();
		this.distractors.addAll(distractors);
	}
}
