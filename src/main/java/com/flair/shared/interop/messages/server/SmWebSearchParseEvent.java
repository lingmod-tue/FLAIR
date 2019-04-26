package com.flair.shared.interop.messages.server;

import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.RankableWebSearchResult;
import com.flair.shared.interop.messages.BaseMessage;

public class SmWebSearchParseEvent extends BaseMessage {
	public enum EventType {
		CRAWL_COMPLETE,
		PARSE_COMPLETE,
		JOB_COMPLETE
	}

	private EventType event = EventType.JOB_COMPLETE;
	private RankableWebSearchResult crawlResult = null;
	private RankableDocument parseResult = null;

	public SmWebSearchParseEvent() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SmWebSearchParseEvent{" + identifier() + "}[");
		sb.append("event=" + event);
		switch (event) {
		case CRAWL_COMPLETE:
			sb.append(" | ").append("searchResult=<").append(crawlResult.getTitle())
					.append("@").append(crawlResult.getDisplayUrl()).append("> | ");
			sb.append("linkingId=").append(crawlResult.getLinkingId());
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
	public RankableWebSearchResult getCrawlResult() {
		return crawlResult;
	}
	public void setCrawlResult(RankableWebSearchResult crawlResult) {
		this.crawlResult = crawlResult;
	}
	public RankableDocument getParseResult() {
		return parseResult;
	}
	public void setParseResult(RankableDocument parseResult) {
		this.parseResult = parseResult;
	}
}
