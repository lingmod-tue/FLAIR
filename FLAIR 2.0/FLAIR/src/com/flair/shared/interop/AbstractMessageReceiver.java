package com.flair.shared.interop;

/*
 * Receives a message from the server
 */
public interface AbstractMessageReceiver
{
	public MessagePipelineType			getType();
	public AuthToken					getToken();
	
	public void							onMessage(ServerMessage[] messages);		// in the order they were sent by the server
}
