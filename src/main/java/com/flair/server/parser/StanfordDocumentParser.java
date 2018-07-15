/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import com.flair.server.grammar.StopwordsList;
import com.flair.server.parser.corenlp.StopwordAnnotator;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * Implementation of the AbstractDocumentParser that uses the Stanford CoreNLP (shift-reduce) parser
 *
 * @author shadeMe
 */
class StanfordDocumentParser extends AbstractDocumentParser {
	private static final String ENGLISH_SR_PARSER_MODEL = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";
	private static final String GERMAN_SR_PARSER_MODEL = "edu/stanford/nlp/models/srparser/germanSR.ser.gz";
	private static final String GERMAN_POS_MODEL = "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger";

	private AbstractDocumentSource docSource;
	private AbstractDocument outputDoc;
	private BasicStanfordDocumentParserStrategy parsingStrategy;

	private final StanfordCoreNLP pipeline;
	private final Language modelLanguage;

	StanfordDocumentParser(AbstractDocumentFactory factory, Language modelLang) {
		super(factory);

		docSource = null;
		outputDoc = null;
		parsingStrategy = null;
		modelLanguage = modelLang;

		Properties pipelineProps = new Properties();
		switch (modelLanguage) {
		case ENGLISH:
			// ### TODO update the parsing strategy to support universal deps
			pipelineProps.put("annotators", "tokenize, ssplit, pos, lemma, stopword, parse");
			pipelineProps.put("parse.originalDependencies", "true");
			pipelineProps.setProperty("parse.model", ENGLISH_SR_PARSER_MODEL);
			pipelineProps.setProperty("ner.applyNumericClassifiers", "false");
			pipelineProps.setProperty("ner.useSUTime", "false");
			pipelineProps.setProperty("ner.applyFineGrained", "false");

			break;
		case GERMAN:
			pipelineProps.put("annotators", "tokenize, ssplit, pos, stopword, parse");
			pipelineProps.put("tokenize.language", "de");
			pipelineProps.setProperty("parse.model", GERMAN_SR_PARSER_MODEL);
			pipelineProps.setProperty("pos.model", GERMAN_POS_MODEL);
			break;
		default:
			throw new IllegalArgumentException("Invalid model language: " + modelLanguage + "");
		}

		pipelineProps.put("tokenize.options", "tokenizePerLine");
		pipelineProps.put("ssplit.newlineIsSentenceBreak", "two");

		StringBuilder stopwords = new StringBuilder();
		StopwordsList.get(modelLanguage).forEach(e -> stopwords.append(e).append(","));
		pipelineProps.setProperty("customAnnotatorClass.stopword", "com.flair.server.parser.corenlp.StopwordAnnotator");
		pipelineProps.setProperty(StopwordAnnotator.STOPWORDS_LIST, stopwords.toString());
		pipelineProps.setProperty(StopwordAnnotator.IGNORE_STOPWORD_CASE, "true");

		pipeline = new StanfordCoreNLP(pipelineProps);
	}

	private void resetState() {
		docSource = null;
		outputDoc = null;
		parsingStrategy = null;
	}

	private boolean isBusy() {
		return docSource != null || outputDoc != null || parsingStrategy != null;
	}

	private AbstractDocument initializeState(AbstractDocumentSource source, AbstractParsingStrategy strategy) {
		if (isBusy()) {
			// this could be the case if the previous task timed-out
			ServerLogger.get().warn("Parser did complete its previous task. Resetting...");
		} else if (strategy instanceof BasicStanfordDocumentParserStrategy == false) {
			throw new IllegalArgumentException(
					strategy.getClass() + " is not subclass of " + BasicStanfordDocumentParserStrategy.class);
		} else if (isLanguageSupported(source.getLanguage()) == false) {
			throw new IllegalArgumentException("Document language " + source.getLanguage()
					+ " not supported (Model language: " + modelLanguage + ")");
		}

		docSource = source;
		outputDoc = docFactory.create(source);
		parsingStrategy = (BasicStanfordDocumentParserStrategy) strategy;

		return outputDoc;
	}

	@Override
	public AbstractDocument parse(AbstractDocumentSource source, AbstractParsingStrategy strategy) {
		AbstractDocument result = null;
		try {
			result = initializeState(source, strategy);
			parsingStrategy.setPipeline(pipeline);
			parsingStrategy.apply(outputDoc);
		} catch (Throwable e) {
			throw e;
		} finally {
			resetState();
		}

		return result;
	}

	@Override
	public boolean isLanguageSupported(Language lang) {
		return modelLanguage == lang;
	}
}

/**
 * Factory class for the Stanford CoreNLP parser
 *
 * @author shadeMe
 */
class StanfordDocumentParserFactory implements AbstractDocumentParserFactory {
	private final AbstractDocumentFactory docFactory;
	private final Language language;

	StanfordDocumentParserFactory(AbstractDocumentFactory factory, Language lang) {
		docFactory = factory;
		language = lang;
	}

	@Override
	public AbstractDocumentParser create() {
		return new StanfordDocumentParser(docFactory, language);
	}
}