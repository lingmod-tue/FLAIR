/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.utilities;

import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import org.xml.sax.InputSource;

/**
 * Boilerpipe implementation of a text extractor
 * 
 * @author shadeMe
 */
class BoilerpipeTextExtractor extends AbstractTextExtractor
{
	public BoilerpipeTextExtractor() {
		super(TextExtractorType.BOILERPIPE);
	}

	@Override
	public Output extractText(Input input) 
	{
		boolean error = false;
		String pageText = "";

		try
		{
			// TODO: check for encoding!!!
			InputSource source = new InputSource();
			source.setEncoding("UTF-8");

			switch (input.sourceType)
			{
			case URL:
				source.setByteStream(openURLStream(input.url, input.lang));
				break;
			case STREAM:
				source.setByteStream(input.stream);
				break;
			}

			pageText = DefaultExtractor.getInstance().getText(source);
		} catch (Throwable ex)
		{
			ServerLogger.get().error("Couldn't fetch text. Exception: " + ex.getMessage());
			error = true;
		}

		// boilerpipe always assumes that the stream is text/html
		return new Output(input, error == false, pageText, true);
	}

	public static String parse(String html, boolean useArticleExtractor) 
	{
		// ### the default extractor seems to mostly return empty strings in this method, why? investigate
		try
		{
			if (useArticleExtractor)
				return ArticleExtractor.getInstance().getText(html);
			else
				return DefaultExtractor.getInstance().getText(html);
		} catch (Throwable ex)
		{
			return "";
		}
	}
}
