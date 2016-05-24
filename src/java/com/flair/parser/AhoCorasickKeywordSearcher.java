/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.parser;

import java.util.Iterator;
import java.util.Set;
import org.arabidopsis.ahocorasick.AhoCorasick;
import org.arabidopsis.ahocorasick.SearchResult;

/**
 * Keyword searcher that implements the Aho-Corasick string search algorithm
 * @author shadeMe
 */
class AhoCorasickKeywordSearcher implements AbstractDocumentKeywordSearcher
{
    @Override
    public KeywordSearcherOutput search(AbstractDocument source, KeywordSearcherInput input)
    {
	KeywordSearcherOutput output = new KeywordSearcherOutput(input);
	AhoCorasick tree = new AhoCorasick();
	
	for (String itr : input)
	    tree.add(itr.getBytes(), itr);
	
	// force to lowercase
	String sourceText = source.getText().toLowerCase();
	tree.prepare();
	for (Iterator iter = tree.search(sourceText.getBytes()); iter.hasNext();)
	{
	    SearchResult result = (SearchResult)iter.next();
	    Set resultSet = result.getOutputs();
	    
	    // the result set can contain multiple items if some keywords are substrings of others (e.g: contain and containment)
	    // in such cases, we only care about the "larger" keyword, so we just increment its hit counter
	    String keyword = null;
	    for (Object itr : resultSet)
	    {
		String castItr = (String)itr;
		if (keyword == null || castItr.length() > keyword.length())
		    keyword = castItr;
	    }
	    
	    if (keyword == null)
		throw new IllegalStateException("Empty result set");
	    
	    int endIdx = result.getLastIndex();
	    int startIdx = endIdx - keyword.length();

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
	}
	
	return output;
    }
}


/**
 * Factory class for the above class
 * @author shadeMe
 */
class AhoCorasickKeywordSearcherFactory implements AbstractDocumentKeywordSearcherFactory
{
    @Override
    public AbstractDocumentKeywordSearcher create() {
	return new AhoCorasickKeywordSearcher();
    }
}
