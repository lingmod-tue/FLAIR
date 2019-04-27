package com.flair.server.interop.messaging;

import com.flair.shared.exceptions.ServerRuntimeException;
import com.flair.shared.interop.messaging.Message;

public interface MessageReceivedHandler<P extends Message.Payload> {
	void read(long messageId, P content) throws ServerRuntimeException;
}
