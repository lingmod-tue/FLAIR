/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.testing;

/**
 * Executable test for the web result framework. Takes a single param - absolute path to a directory
 * @author shadeMe
 */
/*
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
	   WebSearchAgent agent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING, Language.ENGLISH, itr, 50);
	   agent.performSearch();
	   
	   List<SearchResult> searchResults = agent.getResults();
	   long startTime = System.currentTimeMillis();
	   AbstractPipelineOperation op = MasterJobPipeline.get().parseSearchResults(Language.ENGLISH, searchResults);
	   DocumentCollection docCol = op.getOutput();
	   long endTime = System.currentTimeMillis();
	   File outFile = new File(rootOutPath + "/" + itr);
	   outFile.mkdirs();
	   docCol.serialize(serializer, outFile.getAbsolutePath());
	   
	   FLAIRLogger.get().trace(op.toString());
	   FLAIRLogger.get().trace("WebCrawlerTest for query '"+ itr + "' completed in " + (endTime - startTime) + " ms");
	}
	
	System.exit(0);
    }
}
*/