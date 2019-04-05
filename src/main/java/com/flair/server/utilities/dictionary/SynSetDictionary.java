package com.flair.server.utilities.dictionary;

import com.flair.shared.grammar.Language;

import java.util.List;

/*
 * A dictionary of synonym sets (e.g: WordNet)
 */
public interface SynSetDictionary {
	interface SynSet {
		String pos();
		String description();
		boolean contains(String lemma);
	}

	Language language();
	List<? extends SynSet> lookup(String lemma, String pos);
	String lemma(String word, String pos);
}
