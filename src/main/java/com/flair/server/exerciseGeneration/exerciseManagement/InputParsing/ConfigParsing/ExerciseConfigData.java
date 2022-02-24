package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

public class ExerciseConfigData {
	
	public ExerciseConfigData() { }
		
	private String stamp;
	private int activity;
	private ArrayList<ExerciseItemConfigData> itemData = new ArrayList<>();
	private String contextBefore;
	private String contextAfter;

	public String getStamp() { return stamp; }
	public int getActivity() { return activity; }
	
	public void setStamp(String stamp) { this.stamp = stamp; }
	public void setActivity(int activity) { this.activity = activity; }
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
	public ArrayList<ExerciseItemConfigData> getItemData() {
		return itemData;
	}
	public void setItemData(ArrayList<ExerciseItemConfigData> itemData) {
		this.itemData = itemData;
	}
	
}
