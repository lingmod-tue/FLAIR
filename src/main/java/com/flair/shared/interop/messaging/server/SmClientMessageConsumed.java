package com.flair.shared.interop.messaging.server;

import com.flair.shared.interop.messaging.Message;

public class SmClientMessageConsumed implements Message.Payload {
	private long clientMessageId = Message.INVALID_ID;

	public SmClientMessageConsumed() {}

	public long getClientMessageId() {
		return clientMessageId;
	}
	public void setClientMessageId(long clientMessageId) {
		this.clientMessageId = clientMessageId;
	}
	@Override
	public String name() {
		return "SmClientMessageConsumed";
	}
	@Override
	public String desc() {
		return "";
	}
}
