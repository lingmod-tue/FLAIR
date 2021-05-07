package com.flair.server.exerciseGeneration.downloadManagement;

import org.apache.commons.io.IOUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.DownloadedResource;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

public class ResourceDownloader {

    /**
     * Initializes a new Instance
     */
    public ResourceDownloader(boolean downlaod){
        this.download = downlaod;
    }

    /**
     * The file names of the downloaded resources with the corresponding byte content and HTML document to which it belongs
     */
    private ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();

    /**
     * Retrieves the downloaded resources.
     * @return The downloaded resources
     */
    public ArrayList<DownloadedResource> getDownloadedResources() { return downloadedResources; }

    /**
     * Indicates whether to really download the resources.
     * Can be used to turn off downloading for debugging.
     */
    private Boolean download;

    /**
     * Mapping of original resource names against newly assigned names of the downloaded resources
     */
    private HashMap<String, String> resources = new HashMap<>();

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

    /**
     * Downloads the file form the provided url and assigns a new name.
     * If the file format is supported by H5P, the extension is kept; otherwise, it is set to jpeg
     * @param url           The URL from which the resource needs to be downloaded
     * @param parentFileUrl The URL of the CSS file if it was extracted from a CSS file; otherwise null
     * @return              The name of the new file if it has been downloaded successfully; otherwise the absolute url
     */
    public String downloadFile(URL url, URL parentFileUrl) {
        String urlString = url.toString();
        String outputName = urlString;
        if(resources.containsKey(urlString)){
            outputName = resources.get(urlString);
        } else if(download){
            String fileExtension = urlString.substring(urlString.lastIndexOf(".") + 1);
            String nextOutputName = Arrays.asList(allowedFileExtensions).contains(fileExtension) ?
                    "tempResourceName" + fileNumber++ + "." + fileExtension :
                    "tempResourceName" + fileNumber++ + ".jpeg";

            try (InputStream inputStream = url.openStream()){
                byte[] resourceContent = IOUtils.toByteArray(inputStream);
                resources.put(urlString, nextOutputName);
                downloadedResources.add(new DownloadedResource(nextOutputName, resourceContent));
                outputName = nextOutputName;
            } catch (IOException e) {
                // If the url was taken form a css resource file, sometimes the relative paths are formed from the base site
                // and sometimes from the css file path
                // we therefore try both
                if(parentFileUrl != null){
                    return downloadFile(parentFileUrl, null);
                }
                System.out.println("Could not open file " + urlString);
            }
        }

        return outputName;
    }

    /**
     * Downloads the resource from the provided url and returns the string content
     * @param url   The URL from which the resource needs to be downloaded
     * @return      The content of the resource
     */
    public String downloadFileContent(URL url) {
        try {
            URLConnection con = url.openConnection();

            Reader r = "gzip".equals(con.getContentEncoding()) ?
                    new InputStreamReader(new GZIPInputStream(con.getInputStream())) :
                    new InputStreamReader(con.getInputStream());

            StringBuilder content = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(r)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}