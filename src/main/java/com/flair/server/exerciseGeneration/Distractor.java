package com.flair.server.exerciseGeneration;

public class Distractor {

	public Distractor(String value) {
		this.value = value;
	}
	
	private String value;
	private String feedback;
	public String getValue() {
		return value;
	}
	public String getFeedback() {
		return feedback;
	}
	
}
