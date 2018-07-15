/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import com.flair.shared.grammar.GrammaticalConstruction;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents basic properties of a construction for a specific document
 *
 * @author shadeMe
 */
public class DocumentConstructionData extends AbstractConstructionData {
	private final AbstractDocument parentDocument;
	private final ArrayList<ConstructionOccurrence> occurrences;

	private boolean hasOccurence(int start, int end) {
		for (ConstructionOccurrence itr : occurrences) {
			if (itr.getStart() == start && itr.getEnd() == end)
				return true;
		}

		return false;
	}

	DocumentConstructionData(GrammaticalConstruction type, AbstractDocument parent) {
		super(type);

		assert parent != null;
		parentDocument = parent;
		occurrences = new ArrayList<>();
	}

	public boolean hasConstruction() {
		return getFrequency() != 0;
	}

	public int getFrequency() {
		return occurrences.size();
	}

	public double getWeightedFrequency() {
		return 1.0 + Math.log(getFrequency());
	}

	public double getRelativeFrequency() {
		return (double) getFrequency() / (double) parentDocument.getLength();
	}

	public double getRelativeWeightedFrequency() {
		return getWeightedFrequency() / parentDocument.getGramL2Norm();
	}

	public boolean addOccurrence(int start, int end) {
		// especially in case of dependencies (gov/dep): make sure start is smaller than end
		if (start > end) {
			int tmp = end;
			end = start;
			start = tmp;
		}

		// "instance"(example) might be different: root(ROOT, appeared) VS. nsubj(appeared,she)
		// but the indices & construction name will match => don't duplicate!
		if (hasOccurence(start, end) == true)
			return false;

		ConstructionOccurrence newOcc = new ConstructionOccurrence(getParentConstruction(), start, end);

		occurrences.add(newOcc);
		return true;
	}

	public List<ConstructionOccurrence> getOccurrences() {
		return occurrences;
	}
}

class DocumentConstructionDataFactory extends AbstractConstructionDataFactory {
	private final Document parent;

	public DocumentConstructionDataFactory(Document parent) {
		this.parent = parent;
	}

	@Override
	public AbstractConstructionData create(GrammaticalConstruction type) {
		return new DocumentConstructionData(type, parent);
	}
}