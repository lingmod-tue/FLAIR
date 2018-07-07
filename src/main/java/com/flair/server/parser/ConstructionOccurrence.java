/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import com.flair.shared.grammar.GrammaticalConstruction;

/**
 * Represents an occurrence of a construction in a text
 *
 * @author shadeMe
 */
public class ConstructionOccurrence extends AbstractConstructionData {
	private final TextSegment segment;

	public ConstructionOccurrence(GrammaticalConstruction type, int start, int end) {
		super(type);
		this.segment = new TextSegment(start, end);
	}

	public int getStart() {
		return segment.getStart();
	}

	public int getEnd() {
		return segment.getEnd();
	}
}
