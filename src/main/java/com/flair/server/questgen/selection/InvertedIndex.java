package com.flair.server.questgen.selection;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.HashMap;
import java.util.HashSet;

/*
 * Represents an inverted index of postings that map terms (words) to their
 * occurrence frequencies in a collection of documents
 */
class InvertedIndex<T, D> {
	static final class Frequency {
		int frequency;

		Frequency() {
			this.frequency = 0;
		}

		void increment() {
			frequency += 1;
		}

		int get() {
			return frequency;
		}
	}

	static final class Posting<D> {
		final HashMap<D, Frequency> doc2Freq;

		Posting() {
			doc2Freq = new HashMap<>();
		}

		void increment(D doc) {
			Frequency existing = doc2Freq.get(doc);
			if (existing == null) {
				Frequency newFreq = new Frequency();
				newFreq.increment();
				doc2Freq.put(doc, newFreq);
			} else
				existing.increment();
		}

		int getDocumentFrequency() {
			return doc2Freq.size();
		}

		int getTermFrequency(D doc) {
			Frequency existing = doc2Freq.get(doc);
			if (existing == null)
				return 0;
			else
				return existing.get();
		}
	}

	private static final int TERM_ID_BEGIN = 1;     // zero is reserved by the Trove4J map implementations

	private final TObjectIntMap<T> term2id;
	private final TIntObjectMap<Posting<D>> id2posting;
	private final HashSet<D> sourceDocs;
	private int nextId;

	InvertedIndex() {
		term2id = new TObjectIntHashMap<>();
		id2posting = new TIntObjectHashMap<>();
		sourceDocs = new HashSet<>();
		nextId = TERM_ID_BEGIN;

		if (term2id.getNoEntryValue() == TERM_ID_BEGIN || id2posting.getNoEntryKey() == TERM_ID_BEGIN)
			throw new IllegalStateException("Invalid no entry key/value sentinel!");
	}

	private Posting<D> getPosting(T term) {
		int termId = term2id.get(term);
		if (termId == term2id.getNoEntryValue())
			return null;
		else
			return id2posting.get(termId);
	}

	boolean hasTerm(T term) {
		return term2id.get(term) != term2id.getNoEntryValue();
	}

	int getTermId(T term) {
		int id = term2id.get(term);
		if (id == term2id.getNoEntryValue())
			throw new IllegalArgumentException("Term '" + term + "' not found in index");
		return id;
	}


	void addTerm(T term, D sourceDoc) {
		Posting<D> existing = getPosting(term);
		if (existing == null) {
			existing = new Posting<>();
			existing.increment(sourceDoc);

			term2id.put(term, nextId);
			id2posting.put(nextId, existing);
			nextId += 1;
		} else
			existing.increment(sourceDoc);

		sourceDocs.add(sourceDoc);
	}

	int getTermFrequency(T term, D source) {
		Posting<D> existing = getPosting(term);
		int out = 0;
		if (existing != null) {
			if (source == null) {
				for (Frequency f : existing.doc2Freq.values())
					out += f.get();
			} else
				out = existing.getTermFrequency(source);
		}

		return out;
	}

	int getTermDocumentFrequency(T term) {
		Posting existing = getPosting(term);
		if (existing != null)
			return existing.getDocumentFrequency();
		else
			return 0;
	}

	double getTermTfIdf(T term, D source, boolean logTf) {
		double tf = getTermFrequency(term, source);
		double df = getTermDocumentFrequency(term);

		if (logTf)
			tf = 1 + Math.log(tf);

		return tf * Math.log(sourceDocs.size() / df);
	}

	int size() {
		return nextId;
	}
}
