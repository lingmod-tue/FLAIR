package com.flair.server.exerciseGeneration.downloadManagement;

public class DownloadedResource {

	public DownloadedResource(String fileName) {
		this(fileName, null);
    }
	
    public DownloadedResource(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    private String fileName;
    private byte[] fileContent;

    public String getFileName() { return fileName; }
    public byte[] getFileContent() { return fileContent; }

	public void setFileContent(byte[] fileContent) { this.fileContent = fileContent; }

}