package com.flair.server;

import com.flair.server.document.AbstractDocument;
import com.flair.server.document.AbstractDocumentSource;
import com.flair.server.document.SearchResultDocumentSource;
import com.flair.server.document.StringDocumentSource;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.gramparsing.ParseOp;
import com.flair.server.pipelines.gramparsing.SearchCrawlParseOp;
import com.flair.server.pipelines.questgen.GeneratedQuestion;
import com.flair.server.pipelines.questgen.QuestionGenerationOp;
import com.flair.server.pipelines.questgen.QuestionGenerationPipeline;
import com.flair.server.scheduler.AsyncJob;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.textextraction.AbstractTextExtractor;
import com.flair.server.utilities.textextraction.TextExtractorFactory;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionGeneratorTest {
	static final List<QuestionGenerationOp.Output> outputs = new ArrayList<>();

	private static void printResults(QuestionGenerationOp.Output qgOutput) {
		AbstractDocument doc = qgOutput.sourceDoc;

		ServerLogger.get().info("\n\nDocument: " + doc.getDescription());
		if (doc.getDocumentSource() instanceof SearchResultDocumentSource)
			ServerLogger.get().info("URL: " + ((SearchResultDocumentSource) doc.getDocumentSource()).getSearchResult().getURL());

		ServerLogger.get().indent();
		for (GeneratedQuestion gq : qgOutput.generatedQuestions) {
			if (!gq.sourceSentenceOriginal.isEmpty())
				ServerLogger.get().info("Sentence (org): '" + gq.sourceSentenceOriginal + "'");

			ServerLogger.get()
					.info("Sentence: '" + gq.sourceSentence + "'")
					.indent()
					.info("Question: '" + gq.question + "'")
					.info("Answer: '" + gq.answer + "'")
					//		.info("Answer Tree: '" + gq.answerTree.toString() + "'")
					.exdent();
		}
		ServerLogger.get().exdent();
	}

	private static void webSearch() {
		String query = "site:dw.com germany afd";
		int numResults = 4;
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
				.launch()
				.yield();

		long start = System.currentTimeMillis();
		List<PipelineOp<?, QuestionGenerationOp.Output>> jobs = new ArrayList<>();
		for (AbstractDocument doc : output.parsedDocs) {
			jobs.add(QuestionGenerationPipeline.get()
					.generateQuestions()
					.sourceDoc(doc)
					.numQuestions(10)
					.onComplete(outputs::add)
					.build()
					.launch());
		}

		for (PipelineOp<?, ?> jerb : jobs)
			jerb.await();

		long end = System.currentTimeMillis();

		for (QuestionGenerationOp.Output itr : outputs)
			printResults(itr);

		ServerLogger.get().info("QG Operation Complete in " + (end - start) / 1000. + " seconds");
	}

	private static void specificURL(String url) {
		List<AbstractDocumentSource> parsed = new ArrayList<>();

		AsyncJob jerb = AsyncJob.Scheduler.newJob(j -> {})
				.newTask(() -> TextExtractorFactory.create(TextExtractorFactory.Type.AUTODETECT)
						.extractText(new AbstractTextExtractor.Input(url))
						.extractedText
						.replaceAll("\\[[0-9]+\\]|\\[[a-z]+\\]", "")
						.replaceAll("\\[ [a-zA-Z]* \\]", "")
						.replaceAll("\\^", ""))
				.with(ThreadPool.get().builder().build())
				.then((j, s) -> parsed.add(new StringDocumentSource(s, Language.ENGLISH)))
				.queue()
				.fire();

		jerb.await();

		ParseOp.Output parseOutput = GramParsingPipeline.get()
				.documentParse()
				.lang(Language.ENGLISH)
				.docSource(parsed)
				.build()
				.launch()
				.yield();

		long start = System.currentTimeMillis();
		QuestionGenerationPipeline.get()
				.generateQuestions()
				.sourceDoc(parseOutput.parsedDocs.get(0))
				.numQuestions(3)
				.onComplete(outputs::add)
				.build()
				.launch()
				.await();
		long end = System.currentTimeMillis();

		for (QuestionGenerationOp.Output itr : outputs)
			printResults(itr);

		ServerLogger.get().info("QG Operation Complete in " + (end - start) / 1000. + " seconds");
	}

	public static void main(String[] args) {
		specificURL("https://en.wikipedia.org/wiki/Wallaby");
		System.exit(0);
	}
}
