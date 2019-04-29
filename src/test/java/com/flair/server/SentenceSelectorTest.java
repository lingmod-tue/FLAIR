package com.flair.server;

import com.flair.server.document.AbstractDocument;
import com.flair.server.document.SearchResultDocumentSource;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.gramparsing.SearchCrawlParseOp;
import com.flair.server.sentencesel.SentenceSelector;
import com.flair.server.sentencesel.SentenceSelectorFactory;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

import java.util.Arrays;

public class SentenceSelectorTest {
	private static final boolean USE_CORPUS = false;

	public static void main(String[] args) {
		String query = "site:theguardian.com donald trump";
		int numResults = 5;
		Language lang = Language.ENGLISH;
		String[] keywords = new String[]{
				"keywords", "to", "highlight"
		};

		SearchCrawlParseOp.Output output = GramParsingPipeline.get()
				.searchCrawlParse()
				.lang(lang)
				.query(query)
				.results(numResults)
				.keywords(new KeywordSearcherInput(Arrays.asList(keywords)))
				.build()
				.yield();

		long start = System.currentTimeMillis();
		for (int i = 0; i < output.parsedDocs.size(); ++i) {
			AbstractDocument currentDoc = output.parsedDocs.get(i);
			SentenceSelector.Builder builder = SentenceSelectorFactory.create(SentenceSelectorFactory.Type.TEXTRANK);

			builder.similarityMeasure(SentenceSelector.SimilarityMeasure.JACCARD_COEFFICIENT)
					.granularity(SentenceSelector.Granularity.SENTENCE)
					.ignoreStopwords(true)
					.stemWords(true)
					.useSynsets(false)
					.dropDuplicates(true)
					.duplicateCooccurrenceThreshold(0.6)
					.mainDocument(currentDoc);

			if (USE_CORPUS) {
				for (int j = 0; j < output.parsedDocs.size(); ++j) {
					if (i != j)
						builder.corpusDocument(output.parsedDocs.get(j));
				}
			}

			SentenceSelector selector = builder.build();
			ServerLogger.get()
					.info("\n\nDocument: " + currentDoc.getDescription())
					.info("URL: " + ((SearchResultDocumentSource) currentDoc.getDocumentSource()).getSearchResult().getURL())
					.indent();
			int k = 1;
			for (SentenceSelector.SelectedSentence sent : selector.topK(10)) {
				ServerLogger.get()
						.info("[" + String.format("%.4f", sent.score()) + "] " + sent.annotation().index() + ": " + sent.annotation().text());
				k += 1;
			}

			ServerLogger.get().exdent();
		}

		long end = System.currentTimeMillis();

		ServerLogger.get().info("SentSel Operation Complete in " + (end - start) / 1000. + " seconds");
		System.exit(0);
	}
}
