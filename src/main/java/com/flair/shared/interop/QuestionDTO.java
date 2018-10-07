package com.flair.shared.interop;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Represents an automatically-generated question
 */
public class QuestionDTO implements IsSerializable {
	private String question;
	private String answer;

	public QuestionDTO(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public QuestionDTO() {}

	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
}
