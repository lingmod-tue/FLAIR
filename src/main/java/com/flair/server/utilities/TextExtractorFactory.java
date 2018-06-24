/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.utilities;

/**
 * Factory class for text extractors
 * @author shadeMe
 */
public class TextExtractorFactory
{
    public static AbstractTextExtractor create(TextExtractorType type)
    {
	switch (type)
	{
	    case AUTODETECT:
		return new AutoDetectTextExtractor();
	    case BOILERPIPE:
		return new BoilerpipeTextExtractor();
	    case TIKA:
		return new TikaTextExtractor();
	}
	
	return null;
    }
}
