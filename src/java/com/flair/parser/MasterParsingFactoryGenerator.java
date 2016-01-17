/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Omnibus factory generator for component classes
 * @author shadeMe
 */
public final class MasterParsingFactoryGenerator
{
    private MasterParsingFactoryGenerator() {}
    
    public static AbstractDocumentParserFactory createParser(ParserType type, Language lang)
    {
	switch (type)
	{
	    case STANFORD_CORENLP:
		return new StanfordDocumentParserFactory(new DocumentFactory(), lang);
	}
	
	return null;
    }
    
    public static AbstractParsingStrategyFactory createParsingStrategy(ParserType type, Language lang)
    {
	switch (type)
	{
	    case STANFORD_CORENLP:
		return new StanfordDocumentParserStrategyFactory(lang);
	}
	
	return null;
    }
}
