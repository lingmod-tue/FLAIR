package com.flair.server.exerciseGeneration.downloadManagement;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.util.Pair;


public class CssManager {

    /**
     * Finds url() components in CSS strings.
     * Downloads the resource if possible and sets the url to the name of the downloaded file;
     * otherwise, the URL is set to an absolute URL
     * @param siteUrl The URL of the base site
     * @param cssContent The CSS string
     * @param resourceDownloader The resource downloader
     * @param parentUrl The URL of the css file from which the URL was extracted
     * @return The new CSS string with replaced URLs and the downloaded resources
     */
    public Pair<String, ArrayList<DownloadedResource>> handleUrls(String siteUrl, String cssContent, ResourceDownloader resourceDownloader, String parentUrl) {
        Matcher m = Pattern.compile("(?<=url\\()(.*?)(?=\\))").matcher(cssContent);
        ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();
        while (m.find()) {
            String url = m.group();
            int dotIndex = url.lastIndexOf(".");
            if(dotIndex != -1) {
                URL absoluteUrl = UrlManager.getUrl(url, siteUrl);
                URL absoluteParentUrl = null;
                if(parentUrl != null){
                    absoluteParentUrl = UrlManager.getUrl(url, parentUrl);
                }
                if(absoluteUrl != null) {
                	DownloadedResource resource = resourceDownloader.downloadFile(absoluteUrl, absoluteParentUrl);
                	downloadedResources.add(resource);
                    String outputName = resource.getFileName();
                    cssContent = cssContent.replace("url(" + url + ")", "url(" + outputName + ")");
                }
            }
        }

        return new Pair<>(cssContent, downloadedResources);
    }

    /**
     * Parses a CSS string and replaces the html and body selectors
     * with class selectors of the classes added to the elements with which html and body are replaced
     * @param cssContent The CSS string
     * @return The modified CSS string
     */
    public String replaceNonEmbeddableTagSelectors(String cssContent) {
    	// the body or html selector must not be preceded by anything but a whitespace character
    	// it must be followed by a whitespace, something introducing another selector, or an opening curly bracket
    	// This only gives us an approximation, but CSSOMParser does not work in a multi-threaded context (even when using ThreadLocal)
    	// and javafx needs Java 9 or higher
    	return cssContent.replaceAll("(?<![^\\s\\h])(body)(?=[\\s\\h.{~>,+\\[:#])", ".bodyreplacement")
    		.replaceAll("(?<![^\\s\\h])(html)(?=[\\s\\h.{~>,+\\[:#])", ".htmlreplacement");    	
    }
	
}