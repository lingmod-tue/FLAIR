/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.Properties;

/**
 * Implementation of the AbstractDocumentParser that uses the Stanford CoreNLP parser
 * @author shadeMe
 */
class StanfordDocumentParser extends AbstractDocumentParser
{
    private AbstractDocumentSource			docSource;
    private AbstractDocument				outputDoc;
    private BasicStanfordDocumentParserStrategy		parsingStrategy;
    
    private final StanfordCoreNLP			pipeline;
    
    public StanfordDocumentParser(AbstractDocumentFactory factory, Properties pipelineProps)
    {
	super(factory);
	
	docSource = null;
	outputDoc = null;
	parsingStrategy = null;
	
	pipeline = new StanfordCoreNLP(pipelineProps);
    }
    
    public StanfordDocumentParser(AbstractDocumentFactory factory)
    {
	super(factory);
	
	docSource = null;
	outputDoc = null;
	parsingStrategy = null;
	
	Properties pipelineProps = new Properties();
	pipelineProps.put("annotators", "tokenize, ssplit, pos, lemma, parse");
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
	assert isBusy() == false;
	assert BasicStanfordDocumentParserStrategy.class.isAssignableFrom(strategy.getClass()) == true;
	
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
}

/**
 * Factory class for the Stanford CoreNLP parser
 * @author shadeMe
 */
class StanfordDocumentParserFactory implements AbstractDocumentParserFactory
{
    private final AbstractDocumentFactory	docFactory;
    
    public StanfordDocumentParserFactory(AbstractDocumentFactory factory)
    {
	docFactory = factory;
    }
    
    @Override
    public AbstractDocumentParser create() {
	return new StanfordDocumentParser(docFactory);
    }
}