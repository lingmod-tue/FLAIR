/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.document;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of related documents represeting a corpus
 *
 * @author shadeMe
 */
public class DocumentCollection implements Iterable<AbstractDocument> {
	private final Language language;        // only documents of this language are accepted
	private final List<AbstractDocument> dataStore;
	private final ConstructionDataCollection constructionData;

	public DocumentCollection(Language lang) {
		language = lang;
		dataStore = new ArrayList<>();
		constructionData = new ConstructionDataCollection(lang, new DocumentCollectionConstructionDataFactory(this));
	}

	private void refreshConstructionData() {
		for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(language)) {
			DocumentCollectionConstructionData data = (DocumentCollectionConstructionData) constructionData
					.getData(itr);
			int occurrences = 0, docFreq = 0;

			for (AbstractDocument doc : dataStore) {
				DocumentConstructionData docData = doc.getConstructionData(itr);
				if (docData.hasConstruction()) {
					occurrences += docData.getFrequency();
					docFreq++;
				}
			}

			data.calculateData(dataStore.size(), occurrences, docFreq);
		}
	}

	public DocumentCollectionConstructionData getConstructionData(GrammaticalConstruction construction) {
		return (DocumentCollectionConstructionData) constructionData.getData(construction);
	}

	public synchronized int size() {
		return dataStore.size();
	}

	public synchronized void add(AbstractDocument doc, boolean recalcConstructionData) {
		if (doc.getLanguage() != language)
			throw new IllegalArgumentException("Invalid language for collection. Expected " + language + ", received " + doc.getLanguage());

		dataStore.add(doc);
		if (recalcConstructionData)
			refreshConstructionData();
	}

	@Override
	public Iterator<AbstractDocument> iterator() {
		return dataStore.iterator();
	}

	public synchronized void sort() {
		Collections.sort(dataStore);
	}

	public synchronized AbstractDocument get(int i) {
		if (i >= dataStore.size() || i < 0)
			throw new IllegalArgumentException("Index must be 0 < " + i + " < " + dataStore.size());

		return dataStore.get(i);
	}
}