/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.grammar.Language;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.Properties;

/**
 * Implementation of the AbstractDocumentParser that uses the Stanford CoreNLP (shift-reduce) parser
 * @author shadeMe
 */
class StanfordDocumentParser extends AbstractDocumentParser
{
    private AbstractDocumentSource			docSource;
    private AbstractDocument				outputDoc;
    private BasicStanfordDocumentParserStrategy		parsingStrategy;
    
    private final StanfordCoreNLP			pipeline;
    private final Language				modelLanguage;
    
    public StanfordDocumentParser(AbstractDocumentFactory factory, Language modelLang)
    {
	super(factory);
	
	docSource = null;
	outputDoc = null;
	parsingStrategy = null;
	modelLanguage = modelLang;
	
	Properties pipelineProps = new Properties();
	pipelineProps.put("annotators", "tokenize, ssplit, pos, lemma, parse");
	
	// ### TODO update the parsing strategy to support universal deps
	// ### TODO consider using the neural network depparser
	pipelineProps.put("parse.originalDependencies", "true");
	
	switch (modelLanguage)
	{
	    case ENGLISH:
		pipelineProps.setProperty("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");
		break;
	    default:
		throw new IllegalArgumentException("Invalid model language: " + modelLanguage + "");
	}
	
	pipeline = new StanfordCoreNLP(pipelineProps);
    }
    
    private void resetState()
    {
	docSource = null;
	outputDoc = null;
	parsingStrategy = null;
    }
    
    private boolean isBusy() {
	return docSource != null || outputDoc != null || parsingStrategy != null;
    }
    
    private AbstractDocument initializeState(AbstractDocumentSource source, AbstractParsingStrategy strategy)
    {
	if (isBusy())
	    throw new IllegalStateException("Parser not idle");
	else if (BasicStanfordDocumentParserStrategy.class.isAssignableFrom(strategy.getClass()) == false)
	    throw new IllegalArgumentException(strategy.getClass() + " is not subclass of " + BasicStanfordDocumentParserStrategy.class);
	else if (isLanguageSupported(source.getLanguage()) == false)
	    throw new IllegalArgumentException("Document language " + source.getLanguage() + " not supported (Model language: " + modelLanguage + ")");
	
	docSource = source;
	outputDoc = docFactory.create(source);
	parsingStrategy = (BasicStanfordDocumentParserStrategy)strategy;
	
	return outputDoc;
    }
    
    
    @Override
    public AbstractDocument parse(AbstractDocumentSource source, AbstractParsingStrategy strategy)
    {
	AbstractDocument result = initializeState(source, strategy);
	{
	   parsingStrategy.setPipeline(pipeline);
	   parsingStrategy.apply(outputDoc);
	}
	resetState();
	return result;
    }

    @Override
    public boolean isLanguageSupported(Language lang) {
	return modelLanguage == lang;
    }
}

/**
 * Factory class for the Stanford CoreNLP parser
 * @author shadeMe
 */
class StanfordDocumentParserFactory implements AbstractDocumentParserFactory
{
    private final AbstractDocumentFactory	docFactory;
    private final Language			language;
    
    public StanfordDocumentParserFactory(AbstractDocumentFactory factory, Language lang)
    {
	docFactory = factory;
	language = lang;
    }
    
    @Override
    public AbstractDocumentParser create() {
	return new StanfordDocumentParser(docFactory, language);
    }
}