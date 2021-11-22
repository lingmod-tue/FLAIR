package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.Pair;

public abstract class JsonManager {

    /**
     * Adds the htmlElements and the questions to the content.json file
     *
     * @param jsonComponents    The extracted exercise components necessary to @Override
	build the JSON configuration
     * @param folderName        The resource folder of the content type
     * @return                  The JSON object for the entire JSON content file and the HTML string for the preview in FLAIR
     * @throws IOException    	File exception if the writer cannot write to the output file
     * @throws ParseException 	JSON exception if the file cannot be parsed
     */
    public abstract OutputComponents modifyJsonContent(ContentTypeSettings settings, ArrayList<JsonComponents> jsonComponents, String folderName)
            throws IOException, ParseException;

    /**
     * Retrieves the content of the content.json file of the resource file
     * @param fileName  The file name of the h5p resource file
     * @return          The content of the content.json file
     */
    protected InputStream getContentFileContent(String fileName) {
    	ZipFile zipFile = null;
        try(ZipInputStream zipStream = new ZipInputStream(ResourceLoader.loadFile(fileName))) {
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                if (entry.getName().equalsIgnoreCase("content/content.json")) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int streamLength;
                    while ((streamLength = zipStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, Math.min(streamLength, buffer.length));
                    }
                    
                    ByteArrayInputStream returnStream = new ByteArrayInputStream(outputStream.toByteArray());
                    outputStream.close();
                    return returnStream;
                }
            }
        } catch (IOException e) {
			ServerLogger.get().error(e, "Resource file could not be opened. Exception: " + e.toString());
        } finally {
        	if(zipFile != null) {
        		try {
					zipFile.close();
				} catch (IOException e) {
					ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
				}
        	}
        }

        return null;
    }

}