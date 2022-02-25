package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

public class ExerciseConfigData {
	
	public ExerciseConfigData() { }
		
	private String stamp;
	private int activity;
	private ArrayList<ExerciseItemConfigData> itemData = new ArrayList<>();
	private ArrayList<ExerciseTypeSpec> exerciseType = new ArrayList<>();

	public String getStamp() { return stamp; }
	public int getActivity() { return activity; }
	
	public void setStamp(String stamp) { this.stamp = stamp; }
	public void setActivity(int activity) { this.activity = activity; }

	public ArrayList<ExerciseItemConfigData> getItemData() {
		return itemData;
	}
	public void setItemData(ArrayList<ExerciseItemConfigData> itemData) {
		this.itemData = itemData;
	}
	public ArrayList<ExerciseTypeSpec> getExerciseType() {
		return exerciseType;
	}
	public void setExerciseType(ArrayList<ExerciseTypeSpec> exerciseType) {
		this.exerciseType = exerciseType;
	}
	
}
