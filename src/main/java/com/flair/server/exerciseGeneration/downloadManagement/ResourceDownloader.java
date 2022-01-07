package com.flair.server.exerciseGeneration.downloadManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

import com.flair.server.utilities.ServerLogger;

public class ResourceDownloader {

    /**
     * Initializes a new Instance
     */
    public ResourceDownloader(boolean downlaod){
        this.download = downlaod;
    }
    
    /**
     * Mapping of original resource names against downloaded resources
     * Downloaded resources contain the file names of the downloaded resources with the corresponding byte content and HTML document to which it belongs
     */
    private Map<String, DownloadedResource> downloadedResources = Collections.synchronizedMap(new HashMap<>());

    /**
     * Retrieves the downloaded resources.
     * @return The downloaded resources
     */
    public Map<String, DownloadedResource> getDownloadedResources() { return downloadedResources; }

    /**
     * Indicates whether to really download the resources.
     * Can be used to turn off downloading for debugging.
     */
    private Boolean download;

    /**
     * Counter for processed files to be used to generate unique file names
     */
    private int fileNumber = 1;

    /**
     * File extensions of file formats allowed as content of H5P exercises
     */
    private final String[] allowedFileExtensions = new String[] {"bmp", "csv", "diff", "doc", "docx", "eot", "gif", "jpeg", "jpg", "json", 
    		"mp3", "mp4", "m4a", "odp", "ods", "odt", "ogg", "otf", "patch", "pdf", "png", "ppt", "pptx", "rtf", "svg", "swf", "textile", 
    		"tif", "tiff", "ttf", "txt", "wav", "webm", "woff", "woff2", "xls", "xlsx", "xml", "md", "vtt", "webvtt"};

    ReentrantLock lock = new ReentrantLock();
    ReentrantLock writeLock = new ReentrantLock();
    
    /**
     * Downloads the file form the provided url and assigns a new name.
     * If the file format is supported by H5P, the extension is kept; otherwise, it is set to jpeg
     * @param url           The URL from which the resource needs to be downloaded
     * @param parentFileUrl The URL of the CSS file if it was extracted from a CSS file; otherwise null
     * @return              The name of the new file if it has been downloaded successfully; otherwise the absolute url
     */
    public DownloadedResource downloadFile(URL url, URL parentFileUrl) {
        String urlString = url.toString();
        String outputName = urlString;
        if(downloadedResources.containsKey(urlString)){
        	return downloadedResources.get(urlString);
        } else if(download){
    		String fileExtension = urlString.substring(urlString.lastIndexOf(".") + 1);

        	if(Arrays.asList(allowedFileExtensions).contains(fileExtension)) {
                lock.lock();	// make sure that the fileNumber is really unique
                String nextOutputName = "tempResourceName" + fileNumber++ + "." + fileExtension;
                lock.unlock();
                
                try (InputStream inputStream = url.openStream()){
                    byte[] resourceContent = IOUtils.toByteArray(inputStream);
                    DownloadedResource resource = new DownloadedResource(nextOutputName, resourceContent);
                    writeLock.lock();
                    try {
	                    if(!downloadedResources.containsKey(urlString)) {
	                    	downloadedResources.put(urlString, resource);
	                    }
                    } finally {
                    	writeLock.unlock();
                    }
                    // Make sure that we return the resource that is in the hash set
                    // if another thread put it in since we checked, it doesn't actually contain our resource
                    // we would get problems then when accumulating the resources for a quiz
                    return downloadedResources.get(urlString);
                } catch (IOException e) {
                    // If the url was taken form a css resource file, sometimes the relative paths are formed from the base site
                    // and sometimes from the css file path
                    // we therefore try both
                    if(parentFileUrl != null){
                        return downloadFile(parentFileUrl, null);
                    }
                    ServerLogger.get().info("Could not open file " + urlString);
                }
        	}            
        }

        return new DownloadedResource(outputName);
    }

    /**
     * Downloads the resource from the provided url and returns the string content
     * @param url   The URL from which the resource needs to be downloaded
     * @return      The content of the resource
     */
    public static String downloadFileContent(URL url) {
        try {
            URLConnection con = url.openConnection();
            StringBuilder content = new StringBuilder();
            
            try(InputStream is = con.getInputStream()) {
	            Reader r = "gzip".equals(con.getContentEncoding()) ?
	                    new InputStreamReader(new GZIPInputStream(is)) :
	                    new InputStreamReader(is);

	            try(BufferedReader reader = new BufferedReader(r)) {
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    content.append(line);
	                }
	            }
            }            
            return content.toString();
        } catch (IOException e) {
			//ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
            return null;
        }
    }
}