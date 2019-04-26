package com.flair.shared.interop.messages;

import com.flair.shared.interop.AuthToken;

/*
 * Sends messages to the client
 */
public interface AbstractMesageSender {
	MessagePipelineType getType();

	void open(AuthToken receiverToken);            // called before any messages are sent
	void send(ServerMessage msg);
	void close();                                // called when the sender is no longer needed
	boolean isOpen();
	void clearPendingMessages();                    // removes any unread messages in the messages queue
}
