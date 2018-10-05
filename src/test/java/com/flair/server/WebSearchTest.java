package com.flair.server;

import com.flair.server.document.DocumentCollection;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.shared.grammar.Language;

import java.util.Arrays;

/*
 * Runs a simple web search operation
 */
public class WebSearchTest
{
	public static void processParsedDocs(DocumentCollection dc) {

	}

	public static void main(String[] args)
	{
		String query = "site:theguardian.com helsinki summit russia trump";
		int numResults = 10;
		Language lang = Language.ENGLISH;
		String[] keywords = new String[] {
			"keywords", "to", "highlight"
		};

		/*
		 * The PipelineOp class encapsulates a multi-threaded operation. It can contain multiple sub-operations (a.k.a tasks)
		 * Tasks can be one of three types (in the gram parsing pipeline):
		 * 	Web-Search - Takes the above input and returns a list of search results
		 * 	Web-Crawl - Takes a search result and attempts to retrieve its text
		 * 	Parse - Takes some text and parses its contents to generate an AbstractDocument object
		 * 
		 * All of these tasks execute in parallel and are linked to each other.
		 * When a particular task is complete, it sends out a notification that it's complete.
		 * This notification might contain data about the completed task.
		 */

		/*
		 * Use the builder instance to set up the operation's parameters.
		 * Set up handlers to process the different notifications sent out by the operation
		 * Then, start the operation
		 */
		GramParsingPipeline.get()
				.searchCrawlParse()
				.lang(lang)
				.query(query)
				.results(numResults)
				.keywords(new KeywordSearcherInput(Arrays.asList(keywords)))
				.onCrawl(sr -> {
					//	System.out.println("Do something with the search result: " + sr.toString());
				})
				.onParse(d -> {
					//	System.out.println("Do something with the search result: " + sr.toString());
				})
				.onComplete(dc -> {
					//  System.out.println("Do something with the document collection: " + dc.toString());
					processParsedDocs(dc);

				})
				.launch()
				.await();
		/*
		 * At this point, the execution has started and is executing in the background
		 * Each of these handlers will be triggered in a fixed sequence ***for each search result***
		 * 	(CrawlComplete -> ParseComplete => JobComplete (when all the results have been parsed and the operation was not cancelled)
		 * The final result is returned by the JobComplete handler, which is usually a DocumentCollection of parsed documents
		 */
		System.out.println("Operation Complete");
		System.exit(0);
	}

}
