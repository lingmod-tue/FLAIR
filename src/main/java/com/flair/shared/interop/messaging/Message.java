package com.flair.shared.interop.messaging;

import com.flair.shared.interop.ClientIdentificationToken;
import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Encapsulates a message used to communicate between the client and the server.
 */
public class Message<P extends Message.Payload> implements IsSerializable {
	/*
	 * Represents a message's contents.
	 */
	public interface Payload {
		String name();
		String desc();
	}

	public static final long INVALID_ID = 0;

	// identification of the client that sent/is to receive the message
	private ClientIdentificationToken clientId = null;

	// a monotonically increasing number used to uniquely identify the message in a particular channel
	private long messageId = INVALID_ID;

	// the payload of the message
	private P payload = null;

	public Message() {}
	public Message(P payload) {
		this.payload = payload;
	}

	private String identifier() {
		if (clientId != null)
			return clientId.toString() + "|" + messageId;
		else
			return "???|" + messageId;
	}

	@Override
	public String toString() {
		return payload.name() + "{" + identifier() + "}[" + payload.desc() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message<?> message = (Message<?>) o;

		if (messageId != message.messageId) return false;
		return clientId.equals(message.clientId);

	}
	@Override
	public int hashCode() {
		int result = clientId.hashCode();
		result = 31 * result + (int) (messageId ^ (messageId >>> 32));
		return result;
	}
	public ClientIdentificationToken getClientId() {
		return clientId;
	}
	public void setClientId(ClientIdentificationToken clientId) {
		this.clientId = clientId;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public P getPayload() {
		return payload;
	}
	public void setPayload(P payload) {
		this.payload = payload;
	}

}
