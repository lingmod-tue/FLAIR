
package com.flair.server.document;

import com.flair.server.utilities.TextSegment;
import com.flair.shared.grammar.GrammaticalConstruction;

/**
 * Represents an occurrence of a construction in a text
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
