/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import com.flair.shared.grammar.GrammaticalConstruction;

/**
 * Basic interface for construction specific data
 *
 * @author shadeMe
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
