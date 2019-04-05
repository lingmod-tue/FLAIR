
package com.flair.server.utilities.textextraction;

/**
 * Factory class for text extractors
 */
public class TextExtractorFactory {
	public enum Type {
		AUTODETECT,
		BOILERPIPE,
		TIKA
	}

	public static AbstractTextExtractor create(Type type) {
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
