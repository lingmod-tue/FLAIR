package com.flair.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.AbstractDocumentSource;
import com.flair.server.parser.DocumentCollection;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.StreamDocumentSource;
import com.flair.server.taskmanager.CustomParseOperation;
import com.flair.server.taskmanager.MasterJobPipeline;
import com.flair.shared.grammar.Language;

/*
 * Runs a simple custom corpus operation. Refer to WebSearchTest for more information
 */
public class CustomCorpusTest
{
	public static void processParsedDocs(DocumentCollection dc) {
		for (AbstractDocument doc : dc) {
			// do things with the parsed document
		}
	}

	public static void main(String[] args)
	{
		/*
		 * Can be any document file, e.g: pdf, doc, html, etc.
		 * FLAIR will attempt to automatically detect the file type and extract its contents
		 */
		String textFilePath = "C:\\Users\\shadeMe\\Documents\\calling_conventions.pdf";
		Language lang = Language.ENGLISH;
		String[] keywords = new String[] {
			"keywords", "to", "highlight"
		};

		/*
		 * Multiple files can be added in the following manner:
		 */
		List<AbstractDocumentSource> files = new ArrayList<>();
		try
		{
			files.add(new StreamDocumentSource(new FileInputStream(textFilePath), "File name associated with this stream", lang));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CustomParseOperation operation = MasterJobPipeline.get().doDocumentParsing(lang,
				files,
				new KeywordSearcherInput(Arrays.asList(keywords)));
		
		operation.setParseCompleteHandler(d -> {
			System.out.println("Do something with the parsed document: " + d.toString());
		});
		operation.setJobCompleteHandler(dc -> {
			System.out.println("Do something with the document collection: " + dc.toString());
			processParsedDocs(dc);
			
		});
		operation.begin();
		operation.waitForCompletion();
		System.out.println("Operation Complete");
	}
}
