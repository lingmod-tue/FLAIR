/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Search parameters for an AbstractDocumentKeywordSearcher
 * @author shadeMe
 */
public class KeywordSearcherInput implements Iterable<String>
{
    private final List<String>		    keywords;
    private final boolean		    debugMode;	    // prints debug output

    public KeywordSearcherInput() {
	this.keywords = new ArrayList<>();
	this.debugMode = false;
    }
    
    public KeywordSearcherInput(List<String> keywords) {
	this.keywords = new ArrayList<>(keywords);
	this.debugMode = false;
    }
    
    public KeywordSearcherInput(List<String> keywords, boolean debugMode) {
	this.keywords = new ArrayList<>(keywords);
	this.debugMode = debugMode;
    }
    
    public void addKeyword(String keyword) 
    {
	// force to lowercase
	keyword = keyword.toLowerCase();
	if (keywords.contains(keyword) == false)
	    keywords.add(keyword);
    }
    
    @Override
    public Iterator<String> iterator() {
	return keywords.iterator();
    }
    
    public boolean isDebuggable() {
	return debugMode;
    }
}
