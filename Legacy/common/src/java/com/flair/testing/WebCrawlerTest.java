/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.testing;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
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
	
	FLAIRLogger.get().trace("Root Output Path: " + rootOutPath);
	for (String itr : queries)
	{
	    long startTime = System.currentTimeMillis();
	    AbstractPipelineOperation op = MasterJobPipeline.get().performWebSearch(Language.ENGLISH, itr, 10);
	    op.begin();
	    Object output = op.getOutput();
	    List<SearchResult> searchResults = (List<SearchResult>)output;
	    long endTime = System.currentTimeMillis();
	   
	   FLAIRLogger.get().trace(op.toString());
	   FLAIRLogger.get().trace("WebCrawlerTest for query '"+ itr + "' completed in " + (endTime - startTime) + " ms");
	}
	
	System.exit(0);
    }
}