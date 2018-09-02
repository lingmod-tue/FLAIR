
package com.flair.server.document;

import com.flair.shared.grammar.Language;

import java.util.Arrays;

/**
 * Represents the source of a document object
 */
public abstract class AbstractDocumentSource implements Comparable<AbstractDocumentSource> {
	protected interface PreprocessingHandler {
		String preprocess(String input);
	}

	private final Language language;

	public AbstractDocumentSource(Language lang) {
		language = lang;
	}

	protected final String preprocessText(String input, PreprocessingHandler customHandler) {
		// fix up common formatting issues
		input = input.trim().replaceAll("\\n{3,}", "\n\n")
				.replaceAll("\\u0020|\\u00A0", " ");

		StringBuilder out = new StringBuilder();
		Arrays.stream(input.split("\n")).forEach(e -> {
			e = e.trim();
			if (e.isEmpty())
				out.append("\n");
			else if (e.split(" ").length >= 3) {
				// skip sentences with just one or two tokens
				out.append(e);
				if (!(e.endsWith(".") || e.endsWith("!") || e.endsWith("?") ||
						e.endsWith("\"") || e.endsWith(":") || e.endsWith("'"))) {
					out.append(".\n");
				} else
					out.append("\n");
			}
		});

		String recomposed = out.toString();
		if (customHandler != null)
			recomposed = customHandler.preprocess(recomposed);

		recomposed = recomposed.replaceAll("\\.\\n\\.\\n", ".\n")
				.replaceAll("\\. \\.\\n", "")
				.replaceAll("\\n \\.\\n", "\n")
				.replaceAll("\\n\\.\\n", "\n")
				.replaceAll(" {2,}", " ")
				.replaceAll("\\.{2}", ", ")
				.replaceAll(" , ", ", ")
				.replaceAll(" \\. ", ". ")
				.replaceAll("\\n{3,}", "\n\n");
		return recomposed;
	}

	protected final String preprocessText(String input) {
		return preprocessText(input, null);
	}

	public final Language getLanguage() {
		return language;
	}

	abstract public String getSourceText();

	abstract public String getDescription();
}
