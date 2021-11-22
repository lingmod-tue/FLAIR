package com.flair.shared.interop.messaging.server;

import java.util.HashMap;

import com.flair.shared.interop.messaging.Message;

public class SmExGenEvent implements Message.Payload {
	public enum EventType {
		JOB_COMPLETE
	}

	private EventType event = EventType.JOB_COMPLETE;
	private byte[] file;
	private String fileName;
	private HashMap<String, String> previews;
	private byte[] xmls;
	private String xmlName;

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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public HashMap<String, String> getPreviews() {
		return previews;
	}
	public void setPreviews(HashMap<String, String> previews) {
		this.previews = previews;
	}
	public byte[] getXmls() {
		return xmls;
	}
	public void setXmls(byte[] xmls) {
		this.xmls = xmls;
	}

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	@Override
	public String name() {
		return "SmExGenEvent";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("event=" + event).append(" | ");
		sb.append("fileSize=" + file.length);
		return sb.toString();
	}
}
