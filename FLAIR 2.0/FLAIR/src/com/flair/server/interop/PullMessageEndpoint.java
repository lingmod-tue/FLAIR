package com.flair.server.interop;

import com.flair.shared.interop.ServerMessage;

/*
 * Provides an interface for retrieving messages from the message queue
 */
public interface PullMessageEndpoint
{
	public int					getMessageCount();				// number of messages in the queue
	public ServerMessage[]		dequeue(int count);				// removes the corresponding num of messages from the head
	public ServerMessage[]		dequeueAll();					// removes all the messages in the queue
}
