package com.flair.server.pipelines.questgen;

public class GeneratedQuestion {
	public final QuestionKind type;
	public final String question;
	public final String answer;

	public GeneratedQuestion(QuestionKind type, String question, String answer) {
		this.type = type;
		this.question = question;
		this.answer = answer;
	}
}
