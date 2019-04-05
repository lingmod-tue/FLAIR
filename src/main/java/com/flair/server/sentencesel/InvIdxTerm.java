package com.flair.server.sentencesel;

import com.flair.server.utilities.dictionary.SynSetDictionary;

import java.util.Objects;

/*
 * Represents the concept of a term in an inverted index
 */
final class InvIdxTerm {
	final String word;
	final SynSetDictionary.SynSet concept;

	InvIdxTerm(String word) {
		this.word = word;
		this.concept = null;
	}

	InvIdxTerm(SynSetDictionary.SynSet concept) {
		this.word = null;
		this.concept = concept;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InvIdxTerm token = (InvIdxTerm) o;

		if (!Objects.equals(word, token.word)) return false;
		return concept != null ? concept == token.concept : token.concept == null;
	}
	@Override
	public int hashCode() {
		int result = word != null ? word.hashCode() : 0;
		result = 31 * result + (concept != null ? concept.hashCode() : 0);
		return result;
	}
}
