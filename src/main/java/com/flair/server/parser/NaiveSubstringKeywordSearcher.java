/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server.parser;

import com.flair.server.utilities.ServerLogger;

/**
 * Keyword searcher that uses a basic substring search
 * @author shadeMe
 */
public class NaiveSubstringKeywordSearcher implements AbstractDocumentKeywordSearcher
{
    @Override
    public KeywordSearcherOutput search(AbstractDocument source, KeywordSearcherInput input)
    {
	if (input == null)
	    throw new IllegalArgumentException("No keyword search input");
	
	KeywordSearcherOutput output = new KeywordSearcherOutput(input);
	// force to lowercase
	String sourceText = source.getText().toLowerCase();
	
	long startTime = System.currentTimeMillis();
	for (String keyword : input)
	{
	    int startIdx = sourceText.indexOf(keyword, 0);
	    while (startIdx != -1)
	    {
		int endIdx = startIdx + keyword.length();

		// check if it's at a word boundary
		boolean validStartBoundary = false, validEndBoundary = false;
		if (startIdx - 1 < 0 ||
		    sourceText.charAt(startIdx - 1) == '.' ||
		    sourceText.charAt(startIdx - 1) == '-' ||
		    sourceText.charAt(startIdx - 1) == '\n' ||
		    Character.isWhitespace(sourceText.charAt(startIdx - 1)))
		{
		    validStartBoundary = true;
		}

		if (endIdx >= sourceText.length() ||
		    sourceText.charAt(endIdx) == '.' ||
		    sourceText.charAt(endIdx) == '-' ||
		    sourceText.charAt(endIdx) == '\n' ||
		    Character.isWhitespace(sourceText.charAt(endIdx)))
		{
		    validEndBoundary = true;
		}

		if (validStartBoundary && validEndBoundary)
		    output.addHit(keyword, startIdx, endIdx);
		
		startIdx = sourceText.indexOf(keyword, endIdx);
	    }
	    
	}	    
	
	long endTime = System.currentTimeMillis();
	if (input.isDebuggable())
	{
	    ServerLogger.get().trace("Naive keyword search for document " + source.getDescription() + " completed in " + (endTime - startTime) + " ms: Total hits=" + output.getTotalHitCount());
	}
	
	return output;
    }
}


/**
 * Factory class for the above class
 * @author shadeMe
 */
class NaiveSubstringKeywordSearcherFactory implements AbstractDocumentKeywordSearcherFactory
{
    @Override
    public AbstractDocumentKeywordSearcher create() {
	return new NaiveSubstringKeywordSearcher();
    }
}
