/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.parser;

import com.flair.server.grammar.Language;

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
	    case GERMAN:
		return new StanfordDocumentParserGermanStrategy();
	    default:
		throw new IllegalArgumentException("Language unsupported: " + lang);
	}
    }
}