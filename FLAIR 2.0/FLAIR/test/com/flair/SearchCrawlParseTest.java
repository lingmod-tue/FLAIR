package com.flair;

import java.util.ArrayList;

import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.taskmanager.MasterJobPipeline;
import com.flair.server.taskmanager.SearchCrawlParseOperation;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

/**
 * Executable test for the web result framework. Takes a single param - absolute path to a directory
 * 
 * @author shadeMe
 */

public class SearchCrawlParseTest
{
	public static void main(String[] args) 
	{
		String[] queries = { "futurama", "david bowie", "alan rickman", "opeth"};

		String rootOutPath = System.getProperty("user.home") + "/FLAIRTest";
		if (args.length != 0)
			rootOutPath = args[0];

		ServerLogger.get().trace("Root Output Path: " + rootOutPath);
		for (String itr : queries)
		{
			long startTime = System.currentTimeMillis();
			SearchCrawlParseOperation op = MasterJobPipeline.get().doSearchCrawlParse(Language.ENGLISH, itr, 10,
					new KeywordSearcherInput(new ArrayList<>()));
			op.begin();
			op.waitForCompletion();
			long endTime = System.currentTimeMillis();

			ServerLogger.get().trace(op.toString());
			ServerLogger.get()
					.trace("WebCrawlerTest for query '" + itr + "' completed in " + (endTime - startTime) + " ms");
		}

		System.exit(0);
	}
}