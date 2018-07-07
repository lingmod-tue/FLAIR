package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;

import java.util.HashMap;
import java.util.HashSet;

/*
 * Represents an inverted index of postings that map terms (words) to their
 * occurrence frequencies in a collection of documents
 */
public class InvertedIndex
{
	static final class Frequency
	{
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

	static final class Posting
	{
		final HashMap<AbstractDocument, Frequency>    doc2Freq;

		Posting() {
			doc2Freq = new HashMap<>();
		}

		void increment(AbstractDocument doc)
		{
			Frequency existing = doc2Freq.get(doc);
			if (existing == null)
			{
				Frequency newFreq = new Frequency();
				newFreq.increment();
				doc2Freq.put(doc, newFreq);
			}
			else
				existing.increment();
		}

		int getDocumentFrequency() {
			return doc2Freq.size();
		}

		int getTermFrequency(AbstractDocument doc)
		{
			Frequency existing = doc2Freq.get(doc);
			if (existing == null)
				return 0;
			else
				return existing.get();
		}
	}

	private final HashMap<String, Posting>      term2posting;
	private final HashSet<AbstractDocument>     sourceDocs;

	public InvertedIndex()
	{
		term2posting = new HashMap<>();
		sourceDocs = new HashSet<>();
	}

	void addTerm(String term, AbstractDocument sourceDoc)
	{
		Posting existing = term2posting.get(term);
		if (existing == null)
		{
			existing = new Posting();
			existing.increment(sourceDoc);
			term2posting.put(term, existing);
		}
		else
			existing.increment(sourceDoc);

		sourceDocs.add(sourceDoc);
	}

	int getTermFrequency(String term, AbstractDocument source)
	{
		Posting existing = term2posting.get(term);
		int out = 0;
		if (source == null)
		{
			for (Frequency f : existing.doc2Freq.values())
				out += f.get();
		}
		else
			out = existing.getTermFrequency(source);

		return out;
	}

	int getTermDocumentFrequency(String term)
	{
		Posting existing = term2posting.get(term);
		if (existing != null)
			return existing.getDocumentFrequency();
		else
			return 0;
	}

	double getTermTfIdf(String term, AbstractDocument source, boolean logTf)
	{
		double tf = getTermFrequency(term, source);
		double df = getTermDocumentFrequency(term);

		if (logTf)
			tf = 1 + Math.log(tf);

		return tf * Math.log(sourceDocs.size() / df);
	}
}
