package com.flair.server.questgen.selection;

import com.flair.server.document.AbstractDocument;
import com.flair.server.utilities.TextSegment;

import java.util.HashSet;
import java.util.Set;

final class PreprocessedSentence {
	// a unique, atomic unit of a sentence
	static final class Token {
		final String word;
		final SynSetDictionary.SynSet concept;

		Token(String word) {
			this.word = word;
			this.concept = null;
		}

		Token(SynSetDictionary.SynSet concept) {
			this.word = null;
			this.concept = concept;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Token token = (Token) o;

			if (word != null ? !word.equals(token.word) : token.word != null) return false;
			return concept != null ? concept == token.concept : token.concept == null;
		}
		@Override
		public int hashCode() {
			int result = word != null ? word.hashCode() : 0;
			result = 31 * result + (concept != null ? concept.hashCode() : 0);
			return result;
		}
	}

	final AbstractDocument source;
	final TextSegment span;
	final int id;               // sentence number as found in the source
	final Set<Token> tokens;    // unique tokens only

	PreprocessedSentence(AbstractDocument s, TextSegment ss, int id) {
		this.source = s;
		this.span = ss;
		this.id = id;
		this.tokens = new HashSet<>();
	}
}
