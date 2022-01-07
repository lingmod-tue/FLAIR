package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.HashMap;


public class ResultComponents {

    public ResultComponents(HashMap<String, byte[]> h5pFiles, HashMap<String, String> previews, 
    		HashMap<String, byte[]> xmlFiles) {
    	this.h5pFiles = h5pFiles;
    	this.previews = previews;
    	this.xmlFiles = xmlFiles;
    }

	public HashMap<String, String> getPreviews() {
		return previews;
	}

	public HashMap<String, byte[]> getXmlFiles() {
		return xmlFiles;
	}
	
	public HashMap<String, byte[]> getH5pFiles() {
		return h5pFiles;
	}

    private HashMap<String, String> previews;
    private HashMap<String, byte[]> xmlFiles;
    private HashMap<String, byte[]> h5pFiles;

}