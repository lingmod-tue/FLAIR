package com.flair.server.sentencesel;

import com.flair.server.document.AbstractDocument;

/*
 * Represents the concept of a document in an inverted index
 */
final class InvIdxDocument {
	final SentenceSelectorPreprocessor.PreprocessedSentence sent;
	final AbstractDocument doc;

	InvIdxDocument(SentenceSelectorPreprocessor.PreprocessedSentence sent) {
		this.sent = sent;
		this.doc = null;
	}
	InvIdxDocument(AbstractDocument doc) {
		this.doc = doc;
		this.sent = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InvIdxDocument that = (InvIdxDocument) o;
		return sent == that.sent && doc == that.doc;
	}
	@Override
	public int hashCode() {
		int result = sent != null ? sent.hashCode() : 0;
		result = 31 * result + (doc != null ? doc.hashCode() : 0);
		return result;
	}
}
