package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

public interface IExerciseSettings {

    public String getQuiz();
	public String getFileName();
    public String getUrl();
	public ArrayList<OutputFormat> getOutputFormats();
	public int getId();
	public String getFileContent();
	public boolean isGenerateFeedback();
	public String getTopic();
	public String getTaskName();

	public void setFileContent(String fileContent);
	public void setTaskName(String taskName);
}
