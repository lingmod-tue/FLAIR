package com.flair.server;

import com.flair.server.document.AbstractDocument;
import com.flair.server.document.SearchResultDocumentSource;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.gramparsing.SearchCrawlParseOp;
import com.flair.server.pipelines.questgen.GeneratedQuestion;
import com.flair.server.pipelines.questgen.QuestionGenerationOp;
import com.flair.server.pipelines.questgen.QuestionGenerationPipeline;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionGeneratorTest {
	private static void printResults(QuestionGenerationOp.Output qgOutput) {
		AbstractDocument doc = qgOutput.sourceDoc;

		ServerLogger.get()
				.info("\n\nDocument: " + doc.getDescription())
				.info("URL: " + ((SearchResultDocumentSource) doc.getDocumentSource()).getSearchResult().getURL())
				.indent();

		for (GeneratedQuestion gq : qgOutput.generatedQuestions) {
			if (!gq.sourceSentenceOriginal.isEmpty())
				ServerLogger.get().info("Sentence (org): '" + gq.sourceSentenceOriginal + "'");

			ServerLogger.get()
					.info("Sentence: '" + gq.sourceSentence + "'")
					.indent()
					.info("Question: '" + gq.question + "'")
					.info("Answer: '" + gq.answer + "'")
					.exdent();
		}

		ServerLogger.get().exdent();
	}

	public static void main(String[] args) {
		String query = "site:nytimes.com donald trump";
		int numResults = 3;
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
				.launch()
				.yield();

		long start = System.currentTimeMillis();
		List<PipelineOp<?, QuestionGenerationOp.Output>> jobs = new ArrayList<>();
		for (AbstractDocument doc : output.parsedDocs) {
			jobs.add(QuestionGenerationPipeline.get()
					.generateQuestions()
					.sourceDoc(doc)
					.numQuestions(10)
					.onComplete(QuestionGeneratorTest::printResults)
					.launch());
		}

		for (PipelineOp<?, ?> jerb : jobs)
			jerb.await();

		long end = System.currentTimeMillis();

		ServerLogger.get().info("QG Operation Complete in " + (end - start) / 1000. + " seconds");
		System.exit(0);
	}
}
