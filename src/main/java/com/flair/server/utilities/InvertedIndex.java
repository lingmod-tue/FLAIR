package com.flair.server.utilities;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Represents an inverted index of postings that map terms (words) to their
 * occurrence frequencies in a collection of documents
 */
public class InvertedIndex<T, D> {
	private static final class Frequency {
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

	private static final class Posting<D> {
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

		int documentFrequency() {
			return doc2Freq.size();
		}

		int termFrequency(D doc) {
			Frequency existing = doc2Freq.get(doc);
			if (existing == null)
				return 0;
			else
				return existing.get();
		}

		double inverseDocumentFrequency(int numTotalDocs) {
			return Math.log(numTotalDocs / documentFrequency());
		}
	}

	private static final class DocumentData<T> {
		final List<T> terms;

		DocumentData() {
			terms = new ArrayList<>();
		}

		void addTerm(T term) {
			terms.add(term);
		}

		int numTerms() {
			return terms.size();
		}
	}

	private static final int TERM_ID_BEGIN = 1;     // zero is reserved by the Trove4J map implementations

	private final TObjectIntMap<T> term2id;
	private final TIntObjectMap<Posting<D>> id2posting;
	private final HashMap<D, DocumentData<T>> sourceDocs;
	private int nextId;
	private double avgDocLength;
	private double avgIdf;

	public InvertedIndex() {
		term2id = new TObjectIntHashMap<>();
		id2posting = new TIntObjectHashMap<>();
		sourceDocs = new HashMap<>();
		nextId = TERM_ID_BEGIN;
		avgDocLength = -1;
		avgIdf = Double.MAX_VALUE;

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

	public boolean hasTerm(T term) {
		return term2id.get(term) != term2id.getNoEntryValue();
	}

	public int termId(T term) {
		int id = term2id.get(term);
		if (id == term2id.getNoEntryValue())
			throw new IllegalArgumentException("Term '" + term + "' not found in index");
		return id;
	}

	public void addTerm(T term, D sourceDoc) {
		Posting<D> existing = getPosting(term);
		if (existing == null) {
			existing = new Posting<>();
			existing.increment(sourceDoc);

			term2id.put(term, nextId);
			id2posting.put(nextId, existing);
			nextId += 1;
		} else
			existing.increment(sourceDoc);

		DocumentData<T> docData = sourceDocs.computeIfAbsent(sourceDoc, k -> new DocumentData<>());
		docData.addTerm(term);

		// flag for recalculation
		avgDocLength = -1;
		avgIdf = Double.MAX_VALUE;
	}

	public int termFrequency(T term, D source) {
		Posting<D> existing = getPosting(term);
		int out = 0;
		if (existing != null) {
			if (source == null) {
				for (Frequency f : existing.doc2Freq.values())
					out += f.get();
			} else
				out = existing.termFrequency(source);
		}

		return out;
	}

	public int termDocumentFrequency(T term) {
		Posting existing = getPosting(term);
		if (existing != null)
			return existing.documentFrequency();
		else
			return 0;
	}

	public double termTfIdf(T term, D source, boolean logTf) {
		double tf = termFrequency(term, source);
		double df = termDocumentFrequency(term);

		if (logTf)
			tf = 1 + Math.log(tf);

		return tf * Math.log(sourceDocs.size() / df);
	}

	public int numTerms() {
		return nextId;
	}
	public int numDocuments() {
		return sourceDocs.size();
	}
	public double avgDocLength(boolean recalculate) {
		if (avgDocLength < 0 || recalculate)
			avgDocLength = sourceDocs.values().stream().mapToInt(DocumentData::numTerms).average().orElse(0);

		return avgDocLength;
	}
	public double avgIdf(boolean recalculate) {
		if (avgIdf == Double.MAX_VALUE || recalculate) {
			int numTotalDocs = numDocuments();
			avgIdf = id2posting.valueCollection().stream().mapToDouble(e -> e.inverseDocumentFrequency(numTotalDocs)).average().orElse(0);
		}

		return avgIdf;
	}
}
