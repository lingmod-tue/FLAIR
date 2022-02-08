package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

public class Item {
	private String text = "";
	private String target = "";
	private String example = "null";
	private String inputType = "null";
	private String feedback = null;
	private String languageConstruct;
	
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getLanguageConstruct() {
		return languageConstruct;
	}
	public void setLanguageConstruct(String languageConstruct) {
		this.languageConstruct = languageConstruct;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
