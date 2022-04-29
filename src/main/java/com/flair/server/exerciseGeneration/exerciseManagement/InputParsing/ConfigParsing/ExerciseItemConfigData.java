package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public abstract class ExerciseItemConfigData {
	
	public ExerciseItemConfigData() { }
		
	private int item;
	private String contextBefore = "";
	private String contextAfter = "";
	private String feedback = null;

	public int getItem() { return item; }
	
	public void setItem(int item) { this.item = item; }
	public String getContextBefore() {
		return contextBefore;
	}
	public void setContextBefore(String contextBefore) {
		this.contextBefore = contextBefore;
	}
	public String getContextAfter() {
		return contextAfter;
	}
	public void setContextAfter(String contextAfter) {
		this.contextAfter = contextAfter;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
