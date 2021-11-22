package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.flair.server.exerciseGeneration.exerciseManagement.ResultComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.Pair;

public class ZipManager {

    /**
     * Zips all H5P packages into a zip file.
     * @param generatedPackages The H5P packages representing the generated tasks
     * @return      The byte array of the created zip file
     */
    public static byte[] zipH5PPackages(ArrayList<ResultComponents> generatedPackages) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
            for (int i = 0; i < generatedPackages.size(); i++) {
                byte[] task = generatedPackages.get(i).getFileContent();
                ZipEntry zipEntry = new ZipEntry(generatedPackages.get(i).getFileName() + ".h5p");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(task);
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
			ServerLogger.get().error(e, "File could not be zipped. Exception: " + e.toString());
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
    			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
            }
        }
        
        byte[] outputArray = byteArrayOutputStream.toByteArray();
        try {
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return outputArray;
    }
    
    public static byte[] zipXml(ArrayList<ResultComponents> generatedPackages) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
            for (int i = 0; i < generatedPackages.size(); i++) {
            	for(Entry<String, byte[]> entry : generatedPackages.get(i).getXmlFile().entrySet()) {
	                ZipEntry zipEntry = new ZipEntry(entry.getKey() + ".xml");
	                zipOutputStream.putNextEntry(zipEntry);
	                zipOutputStream.write(entry.getValue());
	                zipOutputStream.closeEntry();
            	}
            }
        } catch (IOException e) {
			ServerLogger.get().error(e, "File could not be zipped. Exception: " + e.toString());
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
    			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
            }
        }
        
        byte[] outputArray = byteArrayOutputStream.toByteArray();
        try {
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return outputArray;
    }

    /**
     * Modifies the content.json file of the H5P file on the input file path.
     * @param inputFilePath         The path to the resource H5P file
     * @param contentFileContent    The content to put into the content.json file
     * @return                      The byte array of the modified H5P file
     */
    public static byte[] generateModifiedZipFile(String inputFilePath, String contentFileContent,
                                                  ArrayList<Pair<String, byte[]>> resources) {
        try(ZipInputStream zipStream = new ZipInputStream(ResourceLoader.loadFile(inputFilePath))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final ZipOutputStream outputStream = new ZipOutputStream(byteArrayOutputStream);

            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                if (!entry.getName().equalsIgnoreCase("content/content.json")) {
                    cloneZipFileToOutput(zipStream, entry, outputStream);
                } else {
                    modifyZipFileAndAddToOutput(outputStream, contentFileContent);
                }
                outputStream.closeEntry();
            }

            for(Pair<String, byte[]> resource : resources) {
                addResourceToPackage(outputStream, resource.second, resource.first);
            }

            outputStream.close();

            byte[] outputArray = byteArrayOutputStream.toByteArray();
            try {
    			byteArrayOutputStream.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            return outputArray;            
        } catch (IOException e) {
			ServerLogger.get().error(e, "File could not be zipped. Exception: " + e.toString());
        }

        return null;
    }

    /**
     * Takes the content of the zip entry and writes it to the output stream.
     * @param zipStream     The file stream of the current zip entry
     * @param entry         The entry for the file to be copied
     * @param outputStream  The stream to write the file to
     * @throws IOException  Exception if anything goes wrong in reading or writing
     */
    private static void cloneZipFileToOutput(ZipInputStream zipStream, ZipEntry entry, ZipOutputStream outputStream) throws IOException {
        ZipEntry temp = new ZipEntry(entry.getName());
        outputStream.putNextEntry(temp);
        byte[] buffer = new byte[1024];
        int streamLength;
        while ((streamLength = zipStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, Math.min(streamLength, buffer.length));
        }
    }

    /**
     * Writes the given string content to the content.json file in the zip file.
     * @param outputStream          The output stream in which to create the content.json file
     * @param contentFileContent    The text to write into the content.json file
     */
    private static void modifyZipFileAndAddToOutput(ZipOutputStream outputStream, String contentFileContent) {
        try {
            outputStream.putNextEntry(new ZipEntry("content/content.json"));
            byte[] output = contentFileContent.getBytes();
            outputStream.write(output, 0, output.length);
        } catch (IOException e) {
			ServerLogger.get().error(e, "File could not be zipped. Exception: " + e.toString());
        }
    }

    /**
     * Adds the downloaded resources to the package.
     * @param outputStream  The output stream to write the resources to
     * @param resource      The resource byte array
     * @param resourceName  The file name for the resource
     */
    private static void addResourceToPackage(ZipOutputStream outputStream, byte[] resource, String resourceName) {
        try {
            outputStream.putNextEntry(new ZipEntry("content/" + resourceName));
            outputStream.write(resource, 0, resource.length);
        } catch (IOException e) {
			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());

        }
    }
}
