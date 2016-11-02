/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.utilities;

import java.io.InputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

/**
 * Apache Tika implementation of a text extractor
 * @author shadeMe
 */
class TikaTextExtractor extends AbstractTextExtractor
{
    public TikaTextExtractor() {
	super(TextExtractorType.TIKA);
    }

    @Override
    public Output extractText(String url) 
    {
	boolean error = false;
	String pageText = "";
	
	try
        {
            try (InputStream stream = openURLStream(url))
	    {				
		ContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		new AutoDetectParser().parse(stream, handler, metadata, new ParseContext());
		pageText = handler.toString();
	    }
        } 
	catch (Exception ex)
	{
            FLAIRLogger.get().error("Couldn't fetch page text. Exception: " + ex.getMessage());
	    error = true;
        }	
	
	return new Output(error == false, url, pageText);
    }    
}
