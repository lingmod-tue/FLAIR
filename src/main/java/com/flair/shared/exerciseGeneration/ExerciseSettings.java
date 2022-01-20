package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExerciseSettings implements IsSerializable {

    private String quiz;
    private String fileName;
    private String url;
    private ArrayList<String> outputFormats;
    private int id;
    private boolean generateFeedback;
    private String topic;
   
	public ExerciseSettings() {	}

	public ExerciseSettings(String quiz, String fileName, String url, ArrayList<String> outputFormats, int id,
			boolean generateFeedback, String topic) {
		this.quiz = quiz;
		this.fileName = fileName;
		this.url = url;
		this.outputFormats = outputFormats;
		this.id = id;
		this.generateFeedback = generateFeedback;
		this.topic = topic;
	}
	
	public String getQuiz() {
		return quiz;
	}
	public String getFileName() {
		return fileName;
	}
	public String getUrl() {
		return url;
	}
	public ArrayList<String> getOutputFormats() {
		return outputFormats;
	}
	public int getId() {
		return id;
	}
	public boolean isGenerateFeedback() {
		return generateFeedback;
	}
	public String getTopic() {
		return topic;
	}
	
}
