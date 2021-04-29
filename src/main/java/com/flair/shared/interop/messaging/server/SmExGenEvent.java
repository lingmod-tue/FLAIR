package com.flair.shared.interop.messaging.server;

import com.flair.shared.interop.messaging.Message;

public class SmExGenEvent implements Message.Payload {
	public enum EventType {
		JOB_COMPLETE,
		GENERATION_COMPLETE
	}

	private EventType event = EventType.JOB_COMPLETE;
	private byte[] file;

	public SmExGenEvent() {}

	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String name() {
		return "SmQuestionGenEvent";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("event=" + event).append(" | ");
		sb.append("fileSize=" + file.length);
		return sb.toString();
	}
}
