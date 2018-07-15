package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.parser.TextSegment;
import com.flair.server.utilities.ServerLogger;

import java.util.ArrayList;
import java.util.List;

/*
 * Performs various preprocessing tasks for sentence selectors
 */
final class Preprocessor {
	static List<PreprocessedSentence> preprocess(AbstractDocument doc, DocumentSentenceSelectorParams params) {
		List<PreprocessedSentence> out = new ArrayList<>();

		if (!doc.isParsed())
			throw new IllegalArgumentException("Couldn't preprocess unparsed document");

		try {
			int i = 1;
			for (ParserAnnotations.Sentence sent : doc.getParserAnnotations().sentences()) {
				PreprocessedSentence preprocSent = new PreprocessedSentence(doc, new TextSegment(sent.start(), sent.end()), i);
				for (ParserAnnotations.Token token : sent.tokens()) {
					String lemma = token.lemma().replaceAll("\\p{P}", "").trim().toLowerCase();
					String word = token.word().replaceAll("\\p{P}", "").trim().toLowerCase();

					if (word.isEmpty())
						continue;

					if (params.ignoreStopwords && token.isStopword())
						continue;

					if (params.stemWords && !lemma.isEmpty())
						preprocSent.tokens.add(lemma);
					else
						preprocSent.tokens.add(word);
				}

				if (!preprocSent.tokens.isEmpty())
					out.add(preprocSent);

				++i;
			}
		} catch (Throwable e) {
			ServerLogger.get().error(e, "Error while preprocessing document " + doc.toString());
		}

		return out;
	}
}
