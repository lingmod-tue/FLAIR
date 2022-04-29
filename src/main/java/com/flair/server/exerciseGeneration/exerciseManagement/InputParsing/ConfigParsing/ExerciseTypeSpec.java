package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class ExerciseTypeSpec {

	private String feedbookType;
		
	public ExerciseTypeSpec(String feedbookType) {
		super();
		this.feedbookType = feedbookType;
	}
	
	public ExerciseTypeSpec() {
		super();
	}
	
	public String getFeedbookType() {
		return feedbookType;
	}
	public void setFeedbookType(String feedbookType) {
		this.feedbookType = feedbookType;
	}
	
}
