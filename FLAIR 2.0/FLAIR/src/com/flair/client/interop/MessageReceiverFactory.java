package com.flair.client.interop;

import com.flair.shared.interop.AbstractMessageReceiver;
import com.flair.shared.interop.MessagePipelineType;

/*
 * Factory class for message receivers
 */
public class MessageReceiverFactory
{
	private static MessageReceiverFactory		SINGLETON = new MessageReceiverFactory();
	
	public static MessageReceiverFactory get() {
		return SINGLETON;
	}
	
	private final MessagePipelineType	type;
	
	private MessageReceiverFactory() {
		type = MessagePipelineType.PULL;
	}
	
	public AbstractMessageReceiver create()
	{
		switch (type)
		{
		case PULL:
			return new PullMessageReceiver();
		case PUSH:
			throw new IllegalArgumentException("Receiver type " + type + " not implemented");
		default:
			return null;
		}
	}

}
