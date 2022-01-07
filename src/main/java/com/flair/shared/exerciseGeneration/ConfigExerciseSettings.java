package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigExerciseSettings implements IsSerializable, IExerciseSettings {
	private String fileContent;
    private String fileName;
    private int id;
    private ArrayList<OutputFormat> outputFormats;
    private byte[] fileStream;
    private String topic;
    String quiz;
    private boolean generateFeedback;
    private String taskName;
	
	public ConfigExerciseSettings() {}

	public ConfigExerciseSettings(String fileName, int id, ArrayList<OutputFormat> outputFormats, String topic,
			String quiz, boolean generateFeedback) {
		this.fileName = fileName;
		this.id = id;
		this.outputFormats = outputFormats;
		this.topic = topic;
		this.quiz = quiz;
		this.generateFeedback = generateFeedback;
	}

	@Override
	public String getFileContent() { return fileContent; }
	@Override
    public String getQuiz() { return quiz; }
	@Override
	public String getFileName() { return fileName; }
	@Override
    public int getId() { return id; }
	@Override
    public String getUrl() { return ""; }
	@Override
	public ArrayList<OutputFormat> getOutputFormats() { return outputFormats; }
	public byte[] getFileStream() { return fileStream; }
	@Override
	public String getTopic() { return topic; }
	@Override
	public boolean isGenerateFeedback() { return generateFeedback; }
    @Override
	public String getTaskName() { return taskName; }
	
	public void setFileStream(byte[] fileStream) { this.fileStream = fileStream; }
	@Override
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
	@Override
	public void setTaskName(String taskName) { this.taskName = taskName; }
	
}
