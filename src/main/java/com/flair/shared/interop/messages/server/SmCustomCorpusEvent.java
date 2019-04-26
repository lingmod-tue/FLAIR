package com.flair.shared.interop.messages.server;

import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.UploadedDocument;
import com.flair.shared.interop.messages.BaseMessage;

import java.util.ArrayList;

public class SmCustomCorpusEvent extends BaseMessage {
	public enum EventType {
		UPLOAD_COMPLETE,
		PARSE_COMPLETE,
		JOB_COMPLETE
	}

	private EventType event = EventType.JOB_COMPLETE;
	private ArrayList<UploadedDocument> uploadResult = new ArrayList<>();
	private RankableDocument parseResult = null;

	public SmCustomCorpusEvent() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SmCustomCorpusEvent{" + identifier() + "}[");
		sb.append("event=" + event);
		switch (event) {
		case UPLOAD_COMPLETE:
			sb.append(" | ").append("uploadedFiles=").append(uploadResult.size());
			break;
		case PARSE_COMPLETE:
			sb.append(" | ").append("parsedDoc=<").append(parseResult.getTitle()).append("@")
					.append(parseResult.getDisplayUrl()).append("> | ");
			sb.append("linkingId=").append(parseResult.getLinkingId());
			break;
		case JOB_COMPLETE:
			break;
		}
		return sb.append("]").toString();
	}

	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}
	public ArrayList<UploadedDocument> getUploadResult() {
		return uploadResult;
	}
	public void setUploadResult(ArrayList<UploadedDocument> uploadResult) {
		this.uploadResult = uploadResult;
	}
	public RankableDocument getParseResult() {
		return parseResult;
	}
	public void setParseResult(RankableDocument parseResult) {
		this.parseResult = parseResult;
	}
}
