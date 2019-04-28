package com.flair.shared.interop.messaging.server;

import com.flair.shared.interop.messaging.Message;

public class SmClientMessageConsumed implements Message.Payload {
	private long clientMessageId = Message.INVALID_ID;
	private boolean success = true;
	private SmError error = null;

	public SmClientMessageConsumed() {}

	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public SmError getError() {
		return error;
	}
	public void setError(SmError error) {
		this.error = error;
	}
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
