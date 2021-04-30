package com.flair.server.exerciseGeneration.downloadManagement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Downloads the DOM of a web page consisting of the initial HTML
 */
public class WebBasicHtmlDownloader implements WebDownloader{

    @Override
    public Document download(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }
}