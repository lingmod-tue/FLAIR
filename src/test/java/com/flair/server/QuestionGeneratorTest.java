package com.flair.server;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.gramparsing.SearchCrawlParseOp;
import com.flair.server.pipelines.questgen.GeneratedQuestion;
import com.flair.server.pipelines.questgen.QuestionGenerationOp;
import com.flair.server.pipelines.questgen.QuestionGenerationPipeline;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

import java.util.Arrays;

public class QuestionGeneratorTest {
	public static void main(String[] args) {
		String query = "rem band";
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
				.launch()
				.yield();

		for (AbstractDocument doc : output.parsedDocs) {
			QuestionGenerationOp.Output qgOutput = QuestionGenerationPipeline.get()
					.generateQuestions()
					.sourceDoc(doc)
					.numQuestions(3)
					.launch()
					.yield();

			ServerLogger.get().info("Document: " + doc.getDescription());
			ServerLogger.get().indent();
			for (GeneratedQuestion gq : qgOutput.generatedQuestions) {
				ServerLogger.get().info("Question: '" + gq.question + "'");
				ServerLogger.get().indent();
				ServerLogger.get().info("Answer: '" + gq.answer + "'");
				ServerLogger.get().exdent();
			}
			ServerLogger.get().exdent();
		}

		System.out.println("Operation Complete");
		System.exit(0);
	}
}
