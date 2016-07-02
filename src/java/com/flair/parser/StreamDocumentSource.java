/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.parser;

import com.flair.grammar.Language;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Represents a document source that encapsulates an arbitrary InputStream
 * @author shadeMe
 */
public class StreamDocumentSource extends AbstractDocumentSource
{
    private final String		    sourceString;
    private final String		    name;
    
    public StreamDocumentSource(InputStream source, String name, Language lang)
    {
	super(lang);
	this.name = name;
	
	try (BufferedReader br = new BufferedReader(new InputStreamReader(source))) 
	{
	     StringBuilder sb = new StringBuilder();
	     String line = br.readLine();

	     while (line != null)
	     {
		 sb.append(line);
		 sb.append(System.lineSeparator());
		 line = br.readLine();
	     }
	     
	     sourceString = preprocessText(sb.toString());
	} catch (IOException ex) {
	    throw new IllegalArgumentException("Cannot read from source stream. Exception: " + ex.getMessage());
	}
    }
    
    @Override
    public String getSourceText() {
	return sourceString;
    }

    @Override
    public String getDescription() {
	return "Stream: " + name;
    }
    
    @Override
    public int compareTo(AbstractDocumentSource t) {
	if (t instanceof StreamDocumentSource == false)
	    throw new IllegalArgumentException("Incompatible source type");
	
	StreamDocumentSource rhs = (StreamDocumentSource)t;
	
	// compare source strings
	return sourceString.compareTo(rhs.sourceString);
    }
    
    public String getName() {
	return name;
    }
}
