package com.flair.shared.interop.messaging.client;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;
import com.flair.shared.interop.messaging.Message;

public class CmExGenStart implements Message.Payload {
	private ArrayList<IExerciseSettings> settings;

	public CmExGenStart() {}

	public ArrayList<IExerciseSettings> getSettings() {
		return settings;
	}
	
	public void setSettings(ArrayList<IExerciseSettings> settings) {
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
