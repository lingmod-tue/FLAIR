/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.grammar.Language;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents a document source object that encapsulates the contents of a local file
 * @author shadeMe
 */
public class LocalFileDocumentSource implements AbstractDocumentSource
{
    private final String		    sourceString;
    private final Language		    language;
    
    private final String		    filename;
    private final String		    filePath;
    
    public LocalFileDocumentSource(File sourceFile, Language lang)
    {
	if (sourceFile.canRead() == false)
	    throw new IllegalArgumentException("Cannot read from source file at " + sourceFile.getAbsolutePath());
	else if (sourceFile.isFile() == false)
	    throw new IllegalArgumentException("Invalid source file at " + sourceFile.getAbsolutePath());
	
	language = lang;
	filename = sourceFile.getName();
	filePath = sourceFile.getAbsolutePath();
	
	try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) 
	{
	     StringBuilder sb = new StringBuilder();
	     String line = br.readLine();

	     while (line != null)
	     {
		 sb.append(line);
		 sb.append(System.lineSeparator());
		 line = br.readLine();
	     }
	     
	     sourceString = sb.toString();
	     if (sourceString.isEmpty())
		throw new IllegalArgumentException("Empty source file at " + sourceFile.getAbsolutePath());     
	} catch (IOException ex) {
	    throw new IllegalArgumentException("Cannot read from source file at " + sourceFile.getAbsolutePath() + ". Exception: " + ex.getMessage());
	}
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
    public String getDescription() {
	return "Local File: " + filename;
    }
    
    @Override
    public int compareTo(AbstractDocumentSource t) {
	if (t instanceof LocalFileDocumentSource == false)
	    throw new IllegalArgumentException("Incompatible source type");
	
	LocalFileDocumentSource rhs = (LocalFileDocumentSource)t;
	
	// compare source strings
	return sourceString.compareTo(rhs.sourceString);
    }
}
