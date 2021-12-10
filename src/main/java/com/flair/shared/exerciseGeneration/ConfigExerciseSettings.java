package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigExerciseSettings implements IsSerializable, IExerciseSettings {
	private String fileContent;
    private String fileName;
    private int id;
    private ArrayList<OutputFormat> outputFormats;
    private byte[] fileStream;
	
	public ConfigExerciseSettings() {}

	public ConfigExerciseSettings(String fileName, int id, ArrayList<OutputFormat> outputFormats) {
		this.fileName = fileName;
		this.id = id;
		this.outputFormats = outputFormats;
	}

	@Override
	public String getFileContent() { return fileContent; }
	@Override
    public String getQuiz() { return ""; }
	@Override
	public String getFileName() { return fileName; }
	@Override
    public int getId() { return id; }
	@Override
    public String getUrl() { return ""; }
	@Override
	public ArrayList<OutputFormat> getOutputFormats() { return outputFormats; }
	public byte[] getFileStream() { return fileStream; }
	
	public void setFileStream(byte[] fileStream) { this.fileStream = fileStream; }
	@Override
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }

}
