package com.flair.shared.interop;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;

/*
 * Represents an automatically-generated question
 */
public class QuestionDTO implements IsSerializable {
	private String question;
	private String answer;
	private ArrayList<String> distractors;

	public QuestionDTO(String question, String answer, ArrayList<String> distractors) {
		this.question = question;
		this.answer = answer;
		this.distractors = distractors;
	}
	public QuestionDTO() {}

	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	public ArrayList<String> getDistractors() {
		return distractors;
	}
}
