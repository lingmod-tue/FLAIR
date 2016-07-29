/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Represents a document source object that encapsulates the contents of a local file
 * @author shadeMe
 */
public class StringDocumentSource extends AbstractDocumentSource
{
    private final String		    sourceString;
//    private final Language		    language;
//    
//    private final String		    filename;
//    private final String		    filePath;
    
    public StringDocumentSource(String inputString, Language lang) {

	super(lang);
	if (inputString.isEmpty())
	    throw new IllegalArgumentException("Empty string source");
	
	sourceString = preprocessText(inputString);
	
    }
    
    @Override
    public String getSourceText() {
	return sourceString;
    }

    @Override
    public String getDescription() {
	return "Source string: " + sourceString;
    }
    
    @Override
    public int compareTo(AbstractDocumentSource t) {
	if (t instanceof StringDocumentSource == false)
	    throw new IllegalArgumentException("Incompatible source type");
	
	StringDocumentSource sds = (StringDocumentSource)t;
	
	// compare source strings
	return sourceString.compareTo(sds.sourceString);
    }
}
