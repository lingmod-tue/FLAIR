package com.flair.server.exerciseGeneration.exerciseManagement;

public class DownloadedResource {

    public DownloadedResource(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    private String fileName;
    private byte[] fileContent;

    public String getFileName() { return fileName; }
    public byte[] getFileContent() { return fileContent; }

}