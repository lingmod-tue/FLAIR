package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigExerciseSettings extends ExerciseSettings implements IsSerializable {
	
	private byte[] fileStream;
	private String fileExtension = null;

	public ConfigExerciseSettings() {
		super();
	}

	public ConfigExerciseSettings(String fileName, int id, ArrayList<String> outputFormats, String topic,
			String quiz, boolean generateFeedback, String fileExtension) {
    	super(quiz, fileName, "", outputFormats, id, generateFeedback, topic);
    	this.fileExtension = fileExtension;
	}
	
	public byte[] getFileStream() { return fileStream; }
	
	public void setFileStream(byte[] fileStream) { this.fileStream = fileStream; }

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

}
