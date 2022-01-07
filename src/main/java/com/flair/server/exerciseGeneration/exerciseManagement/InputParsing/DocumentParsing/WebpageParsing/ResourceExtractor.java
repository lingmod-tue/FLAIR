package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.downloadManagement.UrlManager;
import com.flair.server.utilities.ServerLogger;

public class ResourceExtractor {

	public static void extractResources(String url, ResourceDownloader resourceDownloader, Element doc, WebpageData data) {  	
    	// Don't try to download anything for corpus upload files
    	if(url.length() == 0) {
    		return;
    	}
    	
        if(doc != null) {
        	for(Element element : doc.select("base")) {
        		if (Thread.currentThread().isInterrupted()) {
    	        	return;
    	        }
        		
        		url = getAttributeValue(element, "href");
        	}
        	
        	for (Element element : doc.getAllElements()) {
        		if (Thread.currentThread().isInterrupted()) {
    	        	return;
    	        }
                String tag = element.tagName().toLowerCase();

                if(tag.equalsIgnoreCase("blockquote") || tag.equalsIgnoreCase("q") || tag.equalsIgnoreCase("del") || 
                		tag.equalsIgnoreCase("ins") || tag.equalsIgnoreCase("a") || tag.equalsIgnoreCase("area") || 
                		tag.equalsIgnoreCase("button") || tag.equalsIgnoreCase("input") || tag.equalsIgnoreCase("form")){
                	DownloadedResource resource = handleResourceAttribute(element, url, getAttributeName(tag), resourceDownloader, true);
            		if(resource != null) {
            			data.getDownloadedResources().add(resource);
            		}
                } else if(tag.equalsIgnoreCase("audio") || tag.equalsIgnoreCase("track") || tag.equalsIgnoreCase("video") || 
                		tag.equalsIgnoreCase("embed")){
                	DownloadedResource resource = handleResourceAttribute(element, url, "src", resourceDownloader, true);
            		if(resource != null) {
            			data.getDownloadedResources().add(resource);
            		}
                } else if(tag.equalsIgnoreCase("object")){
                	String baseUrl = getAttributeValue(element, "codebase");
                    if(baseUrl == null){
                        baseUrl = url;
                    }

                    String archiveValue = getAttributeValue(element, "archive");
                    if(archiveValue != null){
                        String[] resources = archiveValue.trim().split(" ");
                        ArrayList<String> newResources = new ArrayList<>();
                        for(String resource : resources) {
                            URL absoluteUrl = UrlManager.getUrl(resource, baseUrl);
                            DownloadedResource downloadedResource = resourceDownloader.downloadFile(absoluteUrl, null);
                            data.getDownloadedResources().add(downloadedResource);
                            newResources.add(downloadedResource.getFileName());
                        }
                        String newValue = String.join(" ", newResources);
                        if(!newValue.equals(archiveValue)) {
                            element.attr("archive", newValue);
                        }
                    }

                    DownloadedResource resource = handleResourceAttribute(element, baseUrl, "data", resourceDownloader, false);
            		if(resource != null) {
            			data.getDownloadedResources().add(resource);
            		}
                } else if(tag.equalsIgnoreCase("img") || tag.equalsIgnoreCase("source")){
                	DownloadedResource resource = handleResourceAttribute(element, url, "src", resourceDownloader, false);
            		if(resource != null) {
            			data.getDownloadedResources().add(resource);
            		}
            		data.getDownloadedResources().addAll(handleSrcset(element, url, resourceDownloader, "srcset"));
                } else if(tag.equalsIgnoreCase("link")){
                	DownloadedResource resource = handleResourceAttribute(element, url, "href", resourceDownloader, false);
            		if(resource != null) {
            			data.getDownloadedResources().add(resource);
            		}
            		data.getDownloadedResources().addAll(handleSrcset(element, url, resourceDownloader, "imagesrcset"));
                } else if(tag.equalsIgnoreCase("iframe")){
                	String content = getAttributeValue(element, "srcdoc");
                    if(content != null) {
                    	try {
    	                	extractResources(getAttributeValue(element, "src"), resourceDownloader, 
    	                			Jsoup.parse(content), data);
                    	}
                    	catch(Exception e) {}
                    }
                } 
        	}
        }   
    }
	
    /**
     * Extracts the value of an attribute
     * @param element       The HTML element with the specified attribute
     * @param attributeName The name of the attribute whose value needs to be extracted
     * @return              The value of the specified attribute if the element contains the attribute; otherwise null
     */
    private static String getAttributeValue(Element element, String attributeName){
        for (Attribute attribute : element.attributes()) {
            if (attribute.getKey().toLowerCase().equals(attributeName)) {
                return attribute.getValue();
            }
        }
        return null;
    }
    
    /**
     * Determines the relevant resource attribute of a HTML tag.
     * @param tag	The HTML tag
     * @return		The name of the resource attribute
     */
    private static String getAttributeName(String tag) {
    	if(tag.equals("blockquote") || tag.equals("q") || tag.equals("del") || tag.equals("ins")) {
    		return "cite";
    	}
    	if(tag.equals("a") || tag.equals("area")) {
    		return "href";
    	}
    	if(tag.equals("button") || tag.equals("input")) {
    		return "formaction";
    	}
    	if(tag.equals("form")) {
    		return "action";
    	}
    	return null;
    }
    
    /**
     * Downloads a resource if the downloadResource flag is set.
     * Replaces the resource's path in the HTML element with the new name or with the absolute path if it wasn't downloaded
     * @param element               The HTML element in which the resource is specified
     * @param siteUrl               The URL of the base site
     * @param srcAttribute          The name of the attribute in which the resource is specified
     * @param resourceDownloader    The HtmlManagement.ResourceDownloader
     * @param downloadResource      Indicates whether to download the resource
     * @param downloadWebpages      <code>true</code> if the resource should be downloaded if it is a web page; otherwise <code>false</code>
     */
    private static DownloadedResource handleResourceAttribute(Element element, String siteUrl, String srcAttribute,
                                                ResourceDownloader resourceDownloader,
                                                boolean downloadWebpages) {
        String src = getAttributeValue(element, srcAttribute);
        
        DownloadedResource downloadedResource = null;
        if (src != null) {
            boolean downloadResource = src.contains(".");
            URL absoluteUrl = UrlManager.getUrl(src, siteUrl);

            if(!downloadWebpages){
                downloadResource = !checkIsWebpage(absoluteUrl);
            }

            String outputName;
            if(downloadResource) {
            	downloadedResource = resourceDownloader.downloadFile(absoluteUrl, null);
                outputName = downloadedResource.getFileName();
            } else {
                outputName = absoluteUrl.toString();
            }

            if(!src.equals(outputName)) {
                element.attr(srcAttribute, outputName);
            }
        }
        
        return downloadedResource;
    }
    
    /**
     * Checks the resource paths of all resources in a resource set, downloads them and adjusts the resource path to the new resource name
     * @param element               The html element in which the resource set attribute was found
     * @param url                   The url of the base site
     * @param resourceDownloader    The HtmlManagement.ResourceDownloader
     * @param srcSetName            The name of the resource set attribute
     */
    private static ArrayList<DownloadedResource> handleSrcset(Element element, String url, ResourceDownloader resourceDownloader, String srcSetName){
        String srcSetValue = getAttributeValue(element, srcSetName);
        ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();
        if(srcSetValue != null){
            String [] resources = srcSetValue.split(",");
            ArrayList<String> newResources = new ArrayList<>();
            for(String resource : resources){
                String[] resourceSpecifics = resource.trim().split(" ");
                URL absoluteUrl = UrlManager.getUrl(resourceSpecifics[0], url);
                DownloadedResource downloadedResource = resourceDownloader.downloadFile(absoluteUrl, null);
                resourceSpecifics[0] = downloadedResource.getFileName();
                downloadedResources.add(downloadedResource);
                newResources.add(String.join(" ", resourceSpecifics));
            }
            String newValue = String.join(", ", newResources);
            if(!newValue.equals(srcSetValue)) {
                element.attr(srcSetName, newValue);
            }
        }
        
        return downloadedResources;
    }
    
    /**
     * Checks whether the resource at the provided URL is a webpage
     * @param url The URL of the resource
     * @return true if it is a webpage; false otherwise
     */
    private static boolean checkIsWebpage(URL url){
        URLConnection connection;
        try {
            connection = url.openConnection();
            String contentType = connection.getContentType();
            if(contentType != null && contentType.contains("text/html")){
                return true;
            }
        } catch (IOException e) {
			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
        }

        return false;
    }
    
}
