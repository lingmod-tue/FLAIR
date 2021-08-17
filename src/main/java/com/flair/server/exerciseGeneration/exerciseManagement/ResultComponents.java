package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.HashMap;


public class ResultComponents {

    public ResultComponents(String fileName, byte[] fileContent, HashMap<String, String> previews) {
    	this.fileName = fileName;
    	this.fileContent = fileContent;
    	this.previews = previews;
    }

    public String getFileName() {
		return fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public HashMap<String, String> getPreviews() {
		return previews;
	}

	private String fileName;
    private byte[] fileContent;
    private HashMap<String, String> previews;
    
    

}