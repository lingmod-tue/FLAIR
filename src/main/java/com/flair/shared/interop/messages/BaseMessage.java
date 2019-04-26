package com.flair.shared.interop.messages;

import com.flair.shared.interop.AuthToken;
import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class BaseMessage implements IsSerializable {
	public static final long FIRST_ID = 1;
	public static final long INVALID_ID = FIRST_ID - 1;

	// identifies the client from/to which the messages is sent
	// can be null if the client is communicating with the server for the first time
	private AuthToken clientId = null;

	// a monotonically increasing number used to uniquely identify the message in a sequence (in a particular channel)
	private long messageId = INVALID_ID;

	public BaseMessage() {}

	protected String identifier() {
		if (clientId != null)
			return clientId.toString() + "|" + messageId;
		else
			return "???|" + messageId;
	}

	@Override
	public String toString() {
		return "BaseMessage{" + identifier() + "}";
	}

	public AuthToken getClientId() {
		return clientId;
	}
	public void setClientId(AuthToken clientId) {
		this.clientId = clientId;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
}
