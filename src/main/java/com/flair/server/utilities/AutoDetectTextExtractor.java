package com.flair.server.utilities;

/**
 * Auto-detects the target content type and picks an applicable implementation
 */
class AutoDetectTextExtractor extends AbstractTextExtractor {
	public AutoDetectTextExtractor() {
		super(TextExtractorType.AUTODETECT);
	}

	@Override
	public AbstractTextExtractor.Output extractText(AbstractTextExtractor.Input input) {
		try {
			// Boilerpipe for regular webpages, Tika for everything else
			if (TikaTextExtractor.isContentHTMLPlainText(input.url, input.lang)) {
				//			ServerLogger.get().trace("Plain Text MIME @ '" + input.url + "' - Using BoilerpipeTextExtractor");
				return new BoilerpipeTextExtractor().extractText(input);
			} else {
				//			ServerLogger.get().trace("Non-Text MIME @ '" + input.url + "' - Using TikaTextExtractor");
				return new TikaTextExtractor().extractText(input);
			}
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Couldn't extract text. Exception: " + ex.toString());
			return new AbstractTextExtractor.Output(input, true, "", false);
		}
	}
}
