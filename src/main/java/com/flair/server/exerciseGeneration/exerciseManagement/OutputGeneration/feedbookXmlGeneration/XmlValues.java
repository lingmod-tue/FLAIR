package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;

public class XmlValues {
	
	private int index = 1;
	private String instructions;
	private ArrayList<Item> items = new ArrayList<>();
	private String taskType;
	private String taskOrient = "Offen";
	private String taskFocus = "Form";
	private boolean givenWordsDraggable = false;
	private boolean feedbackDisabled = false;
	private String support = "null";
	private String givenWords = "null";
	private boolean evaluateAtComplete = false;
	
	public XmlValues(String instructions, String taskType) {
		this.instructions = instructions;
		this.taskType = taskType;
	}
	
	public int getIndex() {
		return index;
	}
	public String getInstructions() {
		return instructions;
	}
	public String getTaskType() {
		return taskType;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public String getTaskOrient() {
		return taskOrient;
	}
	public String getTaskFocus() {
		return taskFocus;
	}
	public boolean isGivenWordsDraggable() {
		return givenWordsDraggable;
	}
	public boolean isFeedbackDisabled() {
		return feedbackDisabled;
	}
	public String getSupport() {
		return support;
	}
	public String getGivenWords() {
		return givenWords;
	}
	public boolean isEvaluateAtComplete() {
		return evaluateAtComplete;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	public void setTaskOrient(String taskOrient) {
		this.taskOrient = taskOrient;
	}
	public void setTaskFocus(String taskFocus) {
		this.taskFocus = taskFocus;
	}
	public void setGivenWordsDraggable(boolean givenWordsDraggable) {
		this.givenWordsDraggable = givenWordsDraggable;
	}
	public void setFeedbackDisabled(boolean feedbackDisabled) {
		this.feedbackDisabled = feedbackDisabled;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	public void setGivenWords(String givenWords) {
		this.givenWords = givenWords;
	}
	public void setEvaluateAtComplete(boolean evaluateAtComplete) {
		this.evaluateAtComplete = evaluateAtComplete;
	}
		
}
