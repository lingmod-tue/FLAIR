/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import java.util.List;

import com.flair.server.parser.AbstractDocumentKeywordSearcherFactory;
import com.flair.server.parser.AbstractDocumentSource;
import com.flair.server.parser.AbstractParsingStrategyFactory;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.KeywordSearcherType;
import com.flair.server.parser.MasterParsingFactoryGenerator;
import com.flair.server.parser.ParserType;
import com.flair.shared.grammar.Language;

/**
 * Job scheduler for the web crawling/local parser frameworks
 * 
 * @author shadeMe
 */
public final class MasterJobPipeline
{
	private static MasterJobPipeline SINGLETON = null;

	public static MasterJobPipeline get()
	{
		if (SINGLETON == null)
		{
			synchronized (MasterJobPipeline.class)
			{
				if (SINGLETON == null)
					SINGLETON = new MasterJobPipeline();
			}
		}

		return SINGLETON;
	}

	public static void dispose()
	{
		if (SINGLETON != null)
		{
			SINGLETON.shutdown();
			SINGLETON = null;
		}
	}

	private final WebSearchTask.Executor		webSearchExecutor;
	private final WebCrawlTask.Executor			webCrawlExecutor;
	private final DocumentParseTask.Executor	docParseExecutor;

	private final AbstractParsingStrategyFactory	stanfordEnglishStrategy;
	private final AbstractParsingStrategyFactory	stanfordGermanStrategy;

	private final AbstractDocumentKeywordSearcherFactory naiveSubstringSearcher;

	private DocumentParserPool	stanfordParserEnglishPool;
	private DocumentParserPool	stanfordParserGermanPool;

	private MasterJobPipeline()
	{
		this.webSearchExecutor = WebSearchTask.getExecutor();
		this.webCrawlExecutor = WebCrawlTask.getExecutor();
		this.docParseExecutor = DocumentParseTask.getExecutor();

		this.stanfordEnglishStrategy = MasterParsingFactoryGenerator.createParsingStrategy(ParserType.STANFORD_CORENLP,
				Language.ENGLISH);
		this.stanfordGermanStrategy = MasterParsingFactoryGenerator.createParsingStrategy(ParserType.STANFORD_CORENLP,
				Language.GERMAN);

		this.naiveSubstringSearcher = MasterParsingFactoryGenerator
				.createKeywordSearcher(KeywordSearcherType.NAIVE_SUBSTRING);

		// lazy initilization
		this.stanfordParserGermanPool = null;
		this.stanfordParserEnglishPool = null;
	}

	private void shutdown()
	{
		webSearchExecutor.shutdown(false);
		webCrawlExecutor.shutdown(false);
		docParseExecutor.shutdown(false);
	}

	private AbstractParsingStrategyFactory getStrategyForLanguage(Language lang)
	{
		switch (lang)
		{
		case ENGLISH:
			return stanfordEnglishStrategy;
		case GERMAN:
			return stanfordGermanStrategy;
		default:
			throw new IllegalArgumentException("Language " + lang + " not supported");
		}
	}

	private DocumentParserPool getParserPoolForLanguage(Language lang)
	{
		switch (lang)
		{
		case ENGLISH:
			if (stanfordParserEnglishPool == null)
			{
				stanfordParserEnglishPool = new DocumentParserPool(
						MasterParsingFactoryGenerator.createParser(ParserType.STANFORD_CORENLP, Language.ENGLISH));
			}

			return stanfordParserEnglishPool;
		case GERMAN:
			if (stanfordParserGermanPool == null)
			{
				stanfordParserGermanPool = new DocumentParserPool(
						MasterParsingFactoryGenerator.createParser(ParserType.STANFORD_CORENLP, Language.GERMAN));
			}

			return stanfordParserGermanPool;
		default:
			throw new IllegalArgumentException("Language " + lang + " not supported");
		}
	}

	public SearchCrawlParseOperation doSearchCrawlParse(Language lang,
													String query,
													int numResults,
													KeywordSearcherInput keywords)
	{
		SearchCrawlParseJobInput jobParams = new SearchCrawlParseJobInput(lang,
																query,
																numResults,
																webSearchExecutor,
																webCrawlExecutor,
																docParseExecutor,
																getParserPoolForLanguage(lang),
																getStrategyForLanguage(lang),
																naiveSubstringSearcher,
																keywords);
		SearchCrawlParseOperationImpl newOp = new SearchCrawlParseOperationImpl(jobParams);
		return newOp;
	}

	public CustomParseOperation doDocumentParsing(Language lang,
														List<AbstractDocumentSource> docsSources,
														KeywordSearcherInput keywords)
	{
		ParseJobInput jobParams = new ParseJobInput(lang,
										docsSources,
										docParseExecutor,
										getParserPoolForLanguage(lang),
										getStrategyForLanguage(lang),
										naiveSubstringSearcher,
										keywords);
		CustomParseOperationImpl newOp = new CustomParseOperationImpl(jobParams);
		return newOp;
	}
}