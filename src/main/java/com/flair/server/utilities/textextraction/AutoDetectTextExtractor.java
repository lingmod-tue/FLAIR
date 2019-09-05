package com.flair.server.utilities.textextraction;

import com.flair.server.utilities.ServerLogger;

/**
 * Auto-detects the target content type and picks an applicable implementation
 */
class AutoDetectTextExtractor extends AbstractTextExtractor {
	public AutoDetectTextExtractor() {
		super(TextExtractorFactory.Type.AUTODETECT);
	}

	@Override
	public AbstractTextExtractor.Output extractText(AbstractTextExtractor.Input input) {
		try {
			// Boilerpipe for regular webpages, Tika for everything else
			if (TikaTextExtractor.isContentHTMLPlainText(input.url))
				return new BoilerpipeTextExtractor().extractText(input);
			else
				return new TikaTextExtractor().extractText(input);
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Couldn't extract text. Exception: " + ex.toString());
			return new AbstractTextExtractor.Output(input, true, "", false);
		}
	}
}
