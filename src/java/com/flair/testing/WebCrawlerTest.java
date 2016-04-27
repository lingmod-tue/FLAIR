/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.testing;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
import com.flair.utilities.JSONWriter;
import java.util.List;

/**
 * Executable test for the web result framework. Takes a single param - absolute path to a directory
 * @author shadeMe
 */

public class WebCrawlerTest
{
    public static void main(String[] args)
    {
	String[] queries = 
	{
	    "futurama",
	    "david bowie",
	    "alan rickman",
	    "opeth",
	    "how to destroy angels",
	    "rush band",
	    "tarja",
	    "the alan parsons project",
	    "rob roy",
	    "blackadder"
	};
	
	String rootOutPath = System.getProperty("user.home") + "/FLAIRTest";
	if (args.length != 0)
	   rootOutPath = args[0];
	
	JSONWriter serializer = new JSONWriter();
	FLAIRLogger.get().trace("Root Output Path: " + rootOutPath);
	for (String itr : queries)
	{
	    long startTime = System.currentTimeMillis();
	    AbstractPipelineOperation op = MasterJobPipeline.get().performWebSearch(Language.ENGLISH, itr, 100);
	    op.begin();
	    Object output = op.getOutput();
	    List<SearchResult> searchResults = (List<SearchResult>)output;
/*	    
	    List<String> urls = new ArrayList<>();
	    for (SearchResult res : searchResults)
	    {
		if (urls.contains(res.getURL()) == true)
		   FLAIRLogger.get().trace("Duplicate URL: " + res.getURL());
		else {
		//   FLAIRLogger.get().trace(res.getURL());
		   urls.add(res.getURL());
		}

	    }
	*/    
	    long endTime = System.currentTimeMillis();
	   
	   FLAIRLogger.get().trace(op.toString());
	   FLAIRLogger.get().trace("WebCrawlerTest for query '"+ itr + "' completed in " + (endTime - startTime) + " ms");
	}
	
	System.exit(0);
    }
}