package com.flair.shared.interop.messages.client;

import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.messages.BaseMessage;

public class CmQuestionGenEagerParse extends BaseMessage {
	private RankableDocument sourceDoc = null;

	public CmQuestionGenEagerParse() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CmQuestionGenEagerParse{" + identifier() + "}[");
		if (sourceDoc != null)
			sb.append("sourceDoc=<").append(sourceDoc.toString()).append(">");
		return sb.append("]").toString();
	}

	public RankableDocument getSourceDoc() {
		return sourceDoc;
	}
	public void setSourceDoc(RankableDocument sourceDoc) {
		this.sourceDoc = sourceDoc;
	}
}
