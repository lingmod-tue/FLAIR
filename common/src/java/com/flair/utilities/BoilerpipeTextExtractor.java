/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.utilities;

import com.flair.grammar.Language;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.InputSource;

/**
 * Boilerpipe implementation of a text extractor
 * @author shadeMe
 */
class BoilerpipeTextExtractor extends AbstractTextExtractor
{
    public BoilerpipeTextExtractor() {
	super(TextExtractorType.BOILERPIPE);
    }

    @Override
    public Output extractText(String url, Language lang)
    {
	boolean error = false;
	String pageText = "";
	
	try
        {
	    try (InputStream stream = openURLStream(url, lang))
	    {
		// TODO: check for encoding!!!
		InputSource source = new InputSource();
		source.setEncoding("UTF-8");
		source.setByteStream(stream);

		pageText = DefaultExtractor.INSTANCE.getText(source);
	    }
        } 
	catch (IOException | BoilerpipeProcessingException ex)
	{
            FLAIRLogger.get().error("Couldn't fetch page text. Exception: " + ex.getMessage());
	    error = true;
        }
	
	return new Output(error == false, url, pageText);
    }   
}
