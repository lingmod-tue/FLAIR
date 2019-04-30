package com.flair.shared.interop.messaging;

public interface MessageReceivedHandler<P extends Message.Payload> {
	void read(P content);
}
