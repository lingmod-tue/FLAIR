package com.flair.server.questgen.selection;

import com.flair.shared.grammar.Language;

import java.util.List;

/*
 * A dictionary of synonym sets (e.g: WordNet)
 */
interface SynSetDictionary {
	interface SynSet {
		String pos();
		String description();
		boolean contains(String lemma);
	}

	Language language();
	List<? extends SynSet> lookup(String lemma, String pos);
}
