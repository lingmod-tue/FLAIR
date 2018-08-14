package com.flair.server;

import com.flair.server.document.AbstractDocumentSource;
import com.flair.server.document.DocumentCollection;
import com.flair.server.document.StreamDocumentSource;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.questgen.selection.DocumentSentenceSelector;
import com.flair.server.questgen.selection.DocumentSentenceSelectorGenerator;
import com.flair.shared.grammar.Language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Runs a simple custom corpus operation. Refer to WebSearchTest for more information
 */
public class CustomCorpusTest
{
	public static void processParsedDocs(DocumentCollection dc) {
		DocumentSentenceSelector.Builder rankeBuilder = DocumentSentenceSelectorGenerator.create(DocumentSentenceSelectorGenerator.SelectorType.TEXTRANK, Language.ENGLISH);
		rankeBuilder.stemWords(false)
				.ignoreStopwords(true)
				.useSynsets(true)
				.source(DocumentSentenceSelector.Source.DOCUMENT)
				.granularity(DocumentSentenceSelector.Granularity.SENTENCE)
				.mainDocument(dc.get(0));

		DocumentSentenceSelector sel = rankeBuilder.build();

		int i = 1;
		for (ParserAnnotations.Sentence itr : dc.get(0).getParserAnnotations().sentences()) {
			StringBuilder line = new StringBuilder();
			line.append(i).append(": ").append(itr.text());
			System.out.println(line.toString());
			i++;
		}

		System.out.println("\nRanked:\n");

		i = 1;
		for (DocumentSentenceSelector.SelectedSentence itr : sel.topK(10)) {
			StringBuilder line = new StringBuilder();
			line.append(i).append(": [").append(itr.getScore()).append("] ").append(itr.getText());
			System.out.println(line.toString());
			i++;
		}
	}

	public static void main(String[] args)
	{
		/*
		 * Can be any document file, e.g: pdf, doc, html, etc.
		 * FLAIR will attempt to automatically detect the file type and extract its contents
		 */
		String textFilePath = "C:\\Users\\shadeMe\\Documents\\FLAIR\\Corpus\\QG\\1\\score-test.txt";
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
			e.printStackTrace();
		}

		GramParsingPipeline.get()
				.documentParse()
				.lang(lang)
				.docSource(files)
				.keywords(new KeywordSearcherInput(Arrays.asList(keywords)))
				.onParse(d -> {
					System.out.println("Do something with the parsed document: " + d.toString());
				})
				.onComplete(dc -> {
					System.out.println("Do something with the document collection: " + dc.toString());
					processParsedDocs(dc);
				})
				.launch()
				.await();

		System.out.println("Operation Complete");
		System.exit(0);
	}
}
