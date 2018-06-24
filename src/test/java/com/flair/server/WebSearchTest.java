package com.flair.server;

import java.util.Arrays;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.DocumentCollection;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.taskmanager.MasterJobPipeline;
import com.flair.server.taskmanager.SearchCrawlParseOperation;
import com.flair.shared.grammar.Language;

/*
 * Runs a simple web search operation
 */
public class WebSearchTest
{
	public static void processParsedDocs(DocumentCollection dc) {
		// iterate through the document collection like you would a list
		for (AbstractDocument doc : dc) {
			// do things with the parsed document
		}
	}

	public static void main(String[] args)
	{
		String query = "Blue Oyster Cult";
		int numResults = 10;
		Language lang = Language.ENGLISH;
		String[] keywords = new String[] {
			"keywords", "to", "highlight"
		};

		/*
		 * The AbstractPipelineOperation (base class of SearchCrawlParseOperation) encapsulates a multi-threaded operation. It can contain multiple sub-operations (a.k.a tasks)
		 * Tasks can be one of three types:
		 * 	Web-Search - Takes the above input and returns a list of search results
		 * 	Web-Crawl - Takes a search result and attempts to retrieve its text
		 * 	Parse - Takes some text and parses its contents to generate an AbstractDocument object
		 * 
		 * All of these tasks execute in parallel and are linked to each other.
		 * When a particular task is complete, it sends out a notification that it's complete.
		 * This notification might contain data about the completed task.
		 */
		SearchCrawlParseOperation operation = MasterJobPipeline.get().doSearchCrawlParse(lang,
				query,
				numResults,
				new KeywordSearcherInput(Arrays.asList(keywords)));
		
		/*
		 * Set up handlers to process the different notifications sent out by the operation
		 * Then, start the operation
		 */
		operation.setCrawlCompleteHandler(sr -> {
			System.out.println("Do something with the search result: " + sr.toString());
		});
		operation.setParseCompleteHandler(d -> {
			System.out.println("Do something with the parsed document: " + d.toString());
		});
		operation.setJobCompleteHandler(dc -> {
			System.out.println("Do something with the document collection: " + dc.toString());
			processParsedDocs(dc);
			
		});
		operation.begin();
		/*
		 * At this point, the execution has started and is executing in the background
		 * Each of these handlers will be triggered in a fixed sequence ***for each search result***
		 * 	(CrawlComplete -> ParseComplete => JobComplete (when all the results have been parsed and the operation was not cancelled)
		 * The final result is returned by the JobComplete handler, which is usually a DocumentCollection of parsed documents
		 * Since the operation executes in parallel, we cannot be sure when it'll end/run to completion
		 * So, we wait for the operation to complete and then proceed. This can be done in the following way:
		 */
		operation.waitForCompletion();
		/*
		 * Any statements following the above call to waitForCompletion() will not be executed until the operation is complete (or was cancelled)
		 * At this point, the JobComplete handle would have executed
		 */
		System.out.println("Operation Complete");
	}

}
