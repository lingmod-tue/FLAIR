package com.flair.client.interop;

import com.flair.client.ClientEndPoint;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.interop.AbstractMessageReceiver;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.InvalidAuthTokenException;
import com.flair.shared.interop.MessagePipelineType;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.services.PullMessageEndpointServiceAsync;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/*
 * Periodically polls the server for messages
 */
class PullMessageReceiver implements AbstractMessageReceiver
{
	private final class PollTimer extends Timer
	{
		final class Callback implements AsyncCallback<ServerMessage[]>
		{
			@Override
			public void onFailure(Throwable caught)
			{
				ClientLogger.get().error(caught, "Couldn't fetch server messages");
				callbackInProgess = false;
				
				if (caught instanceof InvalidAuthTokenException)
				{
					ClientEndPoint.get().fatalServerError();
					close();
				}
			}

			@Override
			public void onSuccess(ServerMessage[] result)
			{
				receive(result);
				callbackInProgess = false;
			}
		}

		private boolean		callbackInProgess;
		
		PollTimer() {
			callbackInProgess = false;
		}
		
		@Override
		public void run()
		{
			if (open == false)
				return;
			
			if (token == null)
				throw new RuntimeException("Invalid session token for PollTimer");
			
			// fetch all queued messages from the server
			// and pass them to the handler sequentially
			if (callbackInProgess == false)
				service.dequeueMessages(token, new Callback());
		}
	}
	
	private static final int						TIMER_INTERVAL_MS = 3 * 1000;
	
	private AuthToken								token;
	private final PollTimer							timer;
	private final PullMessageEndpointServiceAsync	service;
	private MessageHandler							handler;
	private boolean									open;
	
	public PullMessageReceiver()
	{
		super();
		this.token = null;
		this.timer = new PollTimer();
		this.service = PullMessageEndpointServiceAsync.Util.getInstance();
		this.handler = null;
		this.open = false;
	}


	@Override
	public MessagePipelineType getType() {
		return MessagePipelineType.PULL;
	}


	@Override
	public AuthToken getToken() {
		return token;
	}


	@Override
	public void open(AuthToken receiverToken)
	{
		if (open)
			throw new RuntimeException("Receiver already open");
		
		// schedule timer
		token = receiverToken;
		timer.scheduleRepeating(TIMER_INTERVAL_MS);
		open = true;
	}


	@Override
	public void receive(ServerMessage[] messages)
	{
		if (handler != null)
		{
			if (messages.length != 0)
				ClientLogger.get().info("Pulled " + messages.length + " messages from the server");
			ClientLogger.get().indent();
			for (ServerMessage itr : messages)
			{
				ClientLogger.get().info(itr.toString());
				handler.handle(itr);
			}
			ClientLogger.get().exdent();
		}
	}


	@Override
	public void close()
	{
		if (open == false)
			throw new RuntimeException("Receiver not open");
		
		timer.cancel();
		token = null;
		open = false;
	}

	@Override
	public void setHandler(MessageHandler handler)
	{
		if (open)
			throw new RuntimeException("Attempting to change handler when the pipeline is open");
		
		this.handler = handler;
	}


	@Override
	public boolean isOpen() {
		return open;
	}
}
