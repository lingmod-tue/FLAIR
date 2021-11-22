package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.HashMap;


public class ResultComponents {

    public ResultComponents(String fileName, byte[] fileContent, HashMap<String, String> previews, HashMap<String, byte[]> xmlFile) {
    	this.fileName = fileName;
    	this.fileContent = fileContent;
    	this.previews = previews;
    	this.xmlFile = xmlFile;
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

	public HashMap<String, byte[]> getXmlFile() {
		return xmlFile;
	}

	private String fileName;
    private byte[] fileContent;
    private HashMap<String, String> previews;
    private HashMap<String, byte[]> xmlFile;
    

}