/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.parser;

import com.flair.server.grammar.Language;

/**
 * Represents the source of a document object
 * @author shadeMe
 */
public abstract class AbstractDocumentSource implements Comparable<AbstractDocumentSource>
{
    private final Language	    language;
    
    public AbstractDocumentSource(Language lang) {
	language = lang;
    }
    
    protected final String preprocessText(String input)
    {
	// ensure that all EOL punctuation marks are periods
	StringBuilder textWriter = new StringBuilder();
	String[] sentences = input.split("\n");
	for (String itr : sentences)
	{
//	    if (itr.trim().isEmpty())
//		continue;
	    
	    textWriter.append(itr);
	    if (!(itr.endsWith(".") || 
		  itr.endsWith("!") || 
		  itr.endsWith("?") ||
		  itr.endsWith("\""))) 
	    {
		textWriter.append(".\n");
	    }
	    else
		textWriter.append("\n");
	}
	
	return textWriter.toString();
    }
    
    public final Language getLanguage() {
	return language;
    }
    
    abstract public String	    getSourceText();
    abstract public String	    getDescription();
}
