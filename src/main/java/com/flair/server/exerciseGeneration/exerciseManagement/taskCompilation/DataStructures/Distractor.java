package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures;

public class Distractor {
	
	public Distractor(String value, boolean isIllFormed) {
		super();
		this.value = value;
		this.isIllFormed = isIllFormed;
	}
	
	/**
	 * The string value of the incorrect answer
	 */
	private String value;
	/**
	 * The feedback associated with the incorrect answer
	 */
	private String feedback;
	/**
	 * Indicates whether it's an ill-formed form
	 */
	private boolean isIllFormed;

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getValue() {
		return value;
	}

	public boolean isIllFormed() {
		return isIllFormed;
	}
	
	
}