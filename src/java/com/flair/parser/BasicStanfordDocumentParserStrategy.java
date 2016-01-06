/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.Language;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;


/**
 * Base strategy for the StanfordDocumentParser class
 * @author shadeMe
 */
abstract class BasicStanfordDocumentParserStrategy implements AbstractParsingStrategy
{
    protected StanfordCoreNLP		    pipeline;
    
    public BasicStanfordDocumentParserStrategy()
    {
	pipeline = null;
    }
    
    public void setPipeline(StanfordCoreNLP pipeline)
    {
	assert pipeline != null;
	this.pipeline = pipeline;
    }
}

class StanfordDocumentParserStrategyFactory implements AbstractParsingStrategyFactory
{
    private final Language		lang;

    public StanfordDocumentParserStrategyFactory(Language lang) {
	this.lang = lang;
    }
    
    @Override
    public AbstractParsingStrategy create()
    {
	switch (lang)
	{
	    case ENGLISH:
		return new StanfordDocumentParserEnglishStrategy();
	}
	
	return null;
    }
}