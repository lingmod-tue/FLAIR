package com.flair.shared.interop;

/*
 * Receives messages from the server
 */
public interface AbstractMessageReceiver {
	public interface MessageHandler {
		public void handle(ServerMessage msg);
	}

	public MessagePipelineType getType();
	public AuthToken getToken();

	public void open(AuthToken receiverToken);            // called before any messages are received, requires a valid handler
	public void receive(ServerMessage[] messages);        // in the order they were sent by the server
	public void close();                                // called when the receiver is no longer needed, removes current handler
	public boolean isOpen();

	public void setHandler(MessageHandler handler);
}
