package com.flair.shared.interop;

/*
 * Sends messages to the client
 */
public interface AbstractMesageSender
{
	public MessagePipelineType			getType();
	
	public void							open(AuthToken receiverToken);			// called before any messages are sent
	public void							send(ServerMessage msg);
	public void							close();								// called when the sender is no longer needed
	public boolean						isOpen();
	public void							clearPendingMessages();					// removes any unread messages in the message queue
}
