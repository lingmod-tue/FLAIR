package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigExerciseSettings extends ExerciseSettings implements IsSerializable {
	
	private byte[] fileStream;

	public ConfigExerciseSettings() {
		super();
	}

	public ConfigExerciseSettings(String fileName, int id, ArrayList<String> outputFormats, String topic,
			String quiz, boolean generateFeedback) {
    	super(quiz, fileName, "", outputFormats, id, generateFeedback, topic);
	}
	
	public byte[] getFileStream() { return fileStream; }
	
	public void setFileStream(byte[] fileStream) { this.fileStream = fileStream; }

}
