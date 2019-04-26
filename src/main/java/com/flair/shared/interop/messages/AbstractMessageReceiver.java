package com.flair.shared.interop.messages;

import com.flair.shared.interop.AuthToken;

/*
 * Receives messages from the server
 */
public interface AbstractMessageReceiver {
	interface MessageHandler {
		void handle(ServerMessage msg);
	}

	MessagePipelineType getType();
	AuthToken getToken();

	void open(AuthToken receiverToken);             // called before any messages are received, requires a valid handler
	void receive(ServerMessage[] messages);         // in the order they were sent by the server
	void close();                                   // called when the receiver is no longer needed, removes current handler
	boolean isOpen();

	void setHandler(MessageHandler handler);
}
