package com.flair.server.questgen.selection;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.List;

/*
 * A wrapper around the bundled WordNet dictionary
 */
class WordNetDictionary implements SynSetDictionary {
	static final class SynSet implements SynSetDictionary.SynSet {
		final Synset source;
		final String pos;

		SynSet(Synset source, String pos) {
			this.source = source;
			this.pos = pos;
		}

		@Override
		public String pos() {
			return pos;
		}
		@Override
		public String description() {
			return source.getGloss();
		}
		@Override
		public boolean contains(String lemma) {
			return source.containsWord(lemma);
		}
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			SynSet synSet = (SynSet) o;
			return source != null ? source.equals(synSet.source) : synSet.source == null;
		}
		@Override
		public int hashCode() {
			return source != null ? source.hashCode() : 0;
		}
	}

	private Dictionary dict;

	WordNetDictionary() {
		try {
			this.dict = Dictionary.getDefaultResourceInstance();
		} catch (Throwable e) {
			this.dict = null;
			ServerLogger.get().error(e, "Couldn't load WordNet dictionary");
		}
	}

	@Override
	public Language language() {
		return Language.ENGLISH;
	}
	@Override
	public List<? extends SynSetDictionary.SynSet> lookup(String lemma, String pos) {
		List<SynSet> out = new ArrayList<>();
		POS partOfSpeech;
		if (pos.startsWith("JJ"))
			partOfSpeech = POS.ADJECTIVE;
		else if (pos.startsWith("RB"))
			partOfSpeech = POS.ADVERB;
		else if (pos.startsWith("NN"))
			partOfSpeech = POS.NOUN;
		else if (pos.startsWith("VB"))
			partOfSpeech = POS.VERB;
		else
			return out;

		try {
			IndexWord indexWord = dict.lookupIndexWord(partOfSpeech, lemma);
			if (indexWord != null) {
				for (long offset : indexWord.getSynsetOffsets()) {
					Synset synset = dict.getSynsetAt(indexWord.getPOS(), offset);
					if (synset != null)
						out.add(new SynSet(synset, pos));
				}
			}
		} catch (Throwable e) {
			ServerLogger.get().error(e, "Couldn't lookup word '" + lemma + "' in WordNet!");
		}

		return out;
	}
}
