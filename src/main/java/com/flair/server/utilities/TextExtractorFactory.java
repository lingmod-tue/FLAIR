
package com.flair.server.utilities;

/**
 * Factory class for text extractors
 */
public class TextExtractorFactory {
	public static AbstractTextExtractor create(TextExtractorType type) {
		switch (type) {
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
