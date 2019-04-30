package com.flair.shared.interop.messaging.client;

import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.messaging.Message;

public class CmQuestionGenEagerParse implements Message.Payload {
	private RankableDocument sourceDoc = null;

	public CmQuestionGenEagerParse() {}

	public RankableDocument getSourceDoc() {
		return sourceDoc;
	}
	public void setSourceDoc(RankableDocument sourceDoc) {
		this.sourceDoc = sourceDoc;
	}
	@Override
	public String name() {
		return "CmQuestionGenEagerParse";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		if (sourceDoc != null)
			sb.append("sourceDoc=<").append(sourceDoc.toString()).append(">");
		return sb.toString();
	}
}
