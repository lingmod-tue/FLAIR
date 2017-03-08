/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server.utilities;

/**
 * Auto-detects the target content type and picks an applicable implementation
 * @author shadeMe
 */
class AutoDetectTextExtractor extends AbstractTextExtractor
{
    public AutoDetectTextExtractor() {
	super(TextExtractorType.AUTODETECT);
    }

    @Override
    public AbstractTextExtractor.Output extractText(AbstractTextExtractor.Input input)
    {
	try
        {
	   // Boilerpipe for regular webpages, Tika for everything else
	   if (TikaTextExtractor.isContentHTMLPlainText(input.url, input.lang))
	   {
	       FLAIRLogger.get().trace("Plain Text MIME @ '" + input.url + "' - Using BoilerpipeTextExtractor");
	       return new BoilerpipeTextExtractor().extractText(input);
	   }
	   else
	   {
	       FLAIRLogger.get().trace("Non-Text MIME @ '" + input.url + "' - Using TikaTextExtractor");
	       return new TikaTextExtractor().extractText(input);
	   }
        } 
	catch (Throwable ex)
	{
            FLAIRLogger.get().error("Couldn't fetch text. Exception: " + ex.getMessage());
	    return new AbstractTextExtractor.Output(input, true, "", false);
        }
    }    
}
