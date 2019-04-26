package com.flair.shared.interop.messages.client;

import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.messages.BaseMessage;

public class CmQuestionGenStart extends BaseMessage {
	private RankableDocument sourceDoc = null;
	private int numQuestions = 10;
	private boolean randomizeSelection = false;

	public CmQuestionGenStart() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CmQuestionGenStart{" + identifier() + "}[");
		if (sourceDoc != null)
			sb.append("sourceDoc=<").append(sourceDoc.toString()).append(">").append("> | ");
		sb.append("numQuestions=" + numQuestions).append(" | ");
		sb.append("randomizeSelection=" + randomizeSelection);
		return sb.append("]").toString();
	}

	public RankableDocument getSourceDoc() {
		return sourceDoc;
	}
	public void setSourceDoc(RankableDocument sourceDoc) {
		this.sourceDoc = sourceDoc;
	}
	public int getNumQuestions() {
		return numQuestions;
	}
	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}
	public boolean isRandomizeSelection() {
		return randomizeSelection;
	}
	public void setRandomizeSelection(boolean randomizeSelection) {
		this.randomizeSelection = randomizeSelection;
	}
}
