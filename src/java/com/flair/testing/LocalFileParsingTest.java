/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.testing;

import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.CompactDocumentData;
import com.flair.parser.DocumentCollection;
import com.flair.parser.LocalFileDocumentSource;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
import com.flair.utilities.JSONWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Executable test for the local document parsing framework. Takes a single param - absolute path to a directory with the text documents
 * @author shadeMe
 */

public class LocalFileParsingTest
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
	
	JSONWriter serializer = new JSONWriter();
	FLAIRLogger.get().trace("Root Input Path: " + rootInPath+ "\nRoot Output Path: " + rootOutPath);
	File rootDir = new File(rootInPath);
	if (rootDir.isDirectory() == false)
	{
	    FLAIRLogger.get().error("Root input path is not a directory");
	    System.exit(0);
	}
	
	File[] inputFiles = rootDir.listFiles();
	List<AbstractDocumentSource> inputSource = new ArrayList<>();
	for (File itr : inputFiles)
	{
	    if (itr.isDirectory() == false)
		inputSource.add(new LocalFileDocumentSource(itr, Language.ENGLISH));
	}
	
	FLAIRLogger.get().info("Files to process:");
	for (AbstractDocumentSource itr: inputSource)
	     FLAIRLogger.get().info(itr.getDescription());
   
	FLAIRLogger.get().info("Begin job...");
	long startTime = System.currentTimeMillis();
	AbstractPipelineOperation op = MasterJobPipeline.get().performDocumentParsing(Language.ENGLISH, inputSource);
	op.begin();
	DocumentCollection docCol = (DocumentCollection)op.getOutput();
	long endTime = System.currentTimeMillis();

	File outFile = new File(rootOutPath);
	outFile.mkdirs();
	for (int i = 0; i < docCol.size(); i++)
	{
	    String outfile = outFile.getAbsolutePath() + "/";
	    serializer.writeObject(new CompactDocumentData(docCol.get(i)), String.format("%03d", i), outfile);
	}

	try (BufferedWriter bw = new BufferedWriter(new FileWriter(rootOutPath + "/constructions.csv"))) {
	     bw.write(docCol.generateFrequencyTable());
	} catch (IOException ex) {
	}

	FLAIRLogger.get().trace(op.toString());
	FLAIRLogger.get().trace("LocalFileParsingTest parsed " + docCol.size() + " documents in " + (endTime - startTime) + " ms");
	System.exit(0);
     }
}
