package com.flair.client.interop.messaging;

import com.flair.shared.interop.messaging.Message;
import com.flair.shared.interop.messaging.MessageReceivedHandler;

public interface MessagePoller {
	interface Builder {
		Builder interval(int interval);     // in seconds
		Builder timeout(int timeout);       // in seconds
		Builder onTimeout(Runnable handler);
		<P extends Message.Payload> Builder onMessage(Class<P> payloadClass, MessageReceivedHandler<P> handler);

		MessagePoller build();
	}

	void start();
	void stop();
	void dispose();
	boolean isRunning();
	boolean isTimedout();
}
