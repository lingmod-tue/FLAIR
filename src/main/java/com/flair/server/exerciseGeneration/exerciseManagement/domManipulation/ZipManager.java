package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;


import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import edu.stanford.nlp.util.Pair;

public class ZipManager {

    /**
     * Zips all H5P packages into a zip file.
     * @param tasks The H5P packages representing the generated tasks
     * @return      The byte array of the created zip file
     */
    public static byte[] zipH5PPackages(ArrayList<byte[]> tasks) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
            for (int i = 0; i < tasks.size(); i++) {
                byte[] task = tasks.get(i);
                ZipEntry zipEntry = new ZipEntry("task" + (i + 1) + ".h5p");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(task);
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Modifies the content.json file of the H5P file on the input file path.
     * @param inputFilePath         The path to the resource H5P file
     * @param contentFileContent    The content to put into the content.json file
     * @return                      The byte array of the modified H5P file
     */
    public static byte[] generateModifiedZipFile(String inputFilePath, String contentFileContent,
                                                  ArrayList<Pair<String, byte[]>> resources) {
        try {
        	ZipInputStream zipStream = new ZipInputStream(ZipManager.class.getResourceAsStream(inputFilePath));

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

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
