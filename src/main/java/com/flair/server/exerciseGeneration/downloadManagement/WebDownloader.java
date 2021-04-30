package com.flair.server.exerciseGeneration.downloadManagement;

public interface WebDownloader {

    /**
     * Downloads the DOM of a webpage.
     * @param url   The URL of the webpage
     * @return      The DOM of the webpage
     */
    Object download(String url);
}