package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.TextSegment;

import java.util.HashSet;
import java.util.Set;

final class PreprocessedSentence {
	final AbstractDocument source;
	final TextSegment span;
	final int id;               // sentence number as found in the source
	final Set<String> tokens;   // unique tokens only

	PreprocessedSentence(AbstractDocument s, TextSegment ss, int id) {
		this.source = s;
		this.span = ss;
		this.id = id;
		this.tokens = new HashSet<>();
	}
}
