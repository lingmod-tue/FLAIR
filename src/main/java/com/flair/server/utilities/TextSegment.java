
package com.flair.server.utilities;

/**
 * Represents a segment in a text
 */
public class TextSegment {
	private final int startIdx;
	private final int endIdx;

	public TextSegment(int startIdx, int endIdx) {
		this.startIdx = startIdx;
		this.endIdx = endIdx;
	}

	public int getStart() {
		return startIdx;
	}

	public int getEnd() {
		return endIdx;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TextSegment that = (TextSegment) o;

		if (startIdx != that.startIdx) return false;
		return endIdx == that.endIdx;
	}

	@Override
	public int hashCode() {
		int result = startIdx;
		result = 31 * result + endIdx;
		return result;
	}
}
