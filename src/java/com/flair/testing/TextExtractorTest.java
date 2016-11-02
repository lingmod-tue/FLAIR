/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.testing;

import com.flair.utilities.FLAIRLogger;
import java.io.File;
import org.apache.tika.Tika;

/**
 *
 * @author shadeMe
 */
public class TextExtractorTest
{
    public static void main(String[] args)
    {
	String rootInPath = System.getProperty("user.home") + "/FLAIRLocalTest";
	String rootOutPath = rootInPath + "/FLAIROutput";
	if (args.length != 0)
	{
	   rootInPath = args[0];
	   rootOutPath = rootInPath + "/FLAIROutput";
	}

	FLAIRLogger.get().trace("Root Input Path: " + rootInPath+ "\nRoot Output Path: " + rootOutPath);
	File rootDir = new File(rootInPath);
	if (rootDir.isDirectory() == false)
	{
	    FLAIRLogger.get().error("Root input path is not a directory");
	    System.exit(0);
	}
	
	File[] inputFiles = rootDir.listFiles();
	Tika pipeline = new Tika();
	pipeline.setMaxStringLength(-1);
	
	for (File itr : inputFiles)
	{
	    if (itr.isDirectory())
		continue;
		
	    try {
		FLAIRLogger.get().info("File: " + itr.getPath() + " | Type: " + pipeline.detect(itr));
		String text = pipeline.parseToString(itr);
		
		FLAIRLogger.get().info("Text parsed! Length: " + text.length());
	    }
	    catch (Exception ex) {
		FLAIRLogger.get().error("Exception: " + ex.getMessage());
	    }
	}
	
	System.exit(0);
     }
}
