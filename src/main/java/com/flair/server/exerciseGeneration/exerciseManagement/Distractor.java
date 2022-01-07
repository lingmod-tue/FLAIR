package com.flair.server.exerciseGeneration.exerciseManagement;

public class Distractor {

	public Distractor(String value) {
		this.value = value;
	}
	
	private String value;
	private String feedback = null;
	private boolean isIllFormed;
	
	public String getValue() {
		return value;
	}
	public String getFeedback() {
		return feedback;
	}
	public boolean isIllFormed() {
		return isIllFormed;
	}
	public void setIllFormed(boolean isIllFormed) {
		this.isIllFormed = isIllFormed;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
}
