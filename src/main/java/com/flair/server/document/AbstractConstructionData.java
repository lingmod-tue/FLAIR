
package com.flair.server.document;

import com.flair.shared.grammar.GrammaticalConstruction;

/**
 * Basic interface for construction specific data
 */
abstract class AbstractConstructionData {
	private final GrammaticalConstruction parentConstruction;

	public AbstractConstructionData(GrammaticalConstruction parent) {
		parentConstruction = parent;
	}

	public GrammaticalConstruction getParentConstruction() {
		return parentConstruction;
	}

	public boolean equals(AbstractConstructionData rhs) {
		return parentConstruction == rhs.parentConstruction;
	}
}

abstract class AbstractConstructionDataFactory {
	public abstract AbstractConstructionData create(GrammaticalConstruction type);
}
