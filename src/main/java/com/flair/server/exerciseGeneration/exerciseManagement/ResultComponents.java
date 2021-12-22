package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.HashMap;


public class ResultComponents {

    public ResultComponents(String fileName, byte[] fileContent, HashMap<String, String> previews, 
    		HashMap<String, byte[]> xmlFile, HashMap<String, byte[]> zipFiles) {
    	this.fileName = fileName;
    	this.fileContent = fileContent;
    	this.previews = previews;
    	this.xmlFile = xmlFile;
    	this.zipFiles = zipFiles;
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

	public HashMap<String, byte[]> getZipFiles() {
		return zipFiles;
	}

	private String fileName;
    private byte[] fileContent;
    private HashMap<String, String> previews;
    private HashMap<String, byte[]> xmlFile;
    private HashMap<String, byte[]> zipFiles;


}