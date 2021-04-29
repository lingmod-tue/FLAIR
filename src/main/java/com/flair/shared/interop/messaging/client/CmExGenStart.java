package com.flair.shared.interop.messaging.client;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.interop.messaging.Message;

public class CmExGenStart implements Message.Payload {
	private ArrayList<ExerciseSettings> settings;

	public CmExGenStart() {}

	public ArrayList<ExerciseSettings> getSettings() {
		return settings;
	}
	
	public void setSettings(ArrayList<ExerciseSettings> settings) {
		this.settings = settings;
	}
	
	@Override
	public String name() {
		return "CmExGenStart";
	}
	
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("numExercises=").append(settings.size());
		return sb.toString();
	}
}
