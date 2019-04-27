package com.flair.server.interop.messaging;

import com.flair.shared.interop.ClientIdentificationToken;
import com.flair.shared.interop.messaging.Message;

public interface ServerMessageChannel {
	ClientIdentificationToken clientId();

	<P extends Message.Payload> void addReceiveHandler(Class<P> payloadClass, MessageReceivedHandler<P> handler);
	void send(Message<? extends Message.Payload> message);
	void clearPendingMessages(boolean keepClientMessageConsumedMessages);
}
