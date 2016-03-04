/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Represents a document source object that encapsulates a string
 * @author shadeMe
 */
public class SimpleDocumentSource implements AbstractDocumentSource
{
    private final String		   sourceString;
    private final Language		   language;
    
    public SimpleDocumentSource(String parent, Language lang)
    {
	if (parent.isEmpty())
	    throw new IllegalArgumentException("Empty string source");
	
	sourceString = parent;
	language = lang;
    }
    
    @Override
    public String getSourceText() {
	return sourceString;
    }

    @Override
    public Language getLanguage() {
	return language;
    }

    @Override
    public String getDescription() 
    {
	if (sourceString.length() < 10)
	    return "Simple String: " + sourceString.substring(0);
	else
	    return "Simple String: " + sourceString.substring(0, 10) + "...";
    }
    
    @Override
    public int compareTo(AbstractDocumentSource t) {
	if (t instanceof SimpleDocumentSource == false)
	    throw new IllegalArgumentException("Incompatible source type");
	
	SimpleDocumentSource rhs = (SimpleDocumentSource)t;
	
	// compare source strings
	return sourceString.compareTo(rhs.sourceString);
    }
}
