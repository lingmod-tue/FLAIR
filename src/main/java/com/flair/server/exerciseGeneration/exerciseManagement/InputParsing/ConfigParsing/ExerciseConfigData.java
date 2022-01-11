package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public abstract class ExerciseConfigData {
	
	public ExerciseConfigData() { }
		
	private String stamp;
	private int item;
	private int activity;

	public String getStamp() { return stamp; }
	public int getItem() { return item; }
	public int getActivity() { return activity; }
	
	public void setStamp(String stamp) { this.stamp = stamp; }
	public void setItem(int item) { this.item = item; }
	public void setActivity(int activity) { this.activity = activity; }
	
}
