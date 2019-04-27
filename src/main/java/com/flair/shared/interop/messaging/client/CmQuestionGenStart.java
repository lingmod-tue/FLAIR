package com.flair.shared.interop.messaging.client;

import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.messaging.Message;

public class CmQuestionGenStart implements Message.Payload {
	private RankableDocument sourceDoc = null;
	private int numQuestions = 10;
	private boolean randomizeSelection = false;

	public CmQuestionGenStart() {}

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
	public boolean getRandomizeSelection() {
		return randomizeSelection;
	}
	public void setRandomizeSelection(boolean randomizeSelection) {
		this.randomizeSelection = randomizeSelection;
	}
	@Override
	public String name() {
		return "CmQuestionGenStart";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		if (sourceDoc != null)
			sb.append("sourceDoc=<").append(sourceDoc.toString()).append(">").append("> | ");
		sb.append("numQuestions=").append(numQuestions).append(" | ");
		sb.append("randomizeSelection=").append(randomizeSelection);
		return sb.toString();
	}
}
