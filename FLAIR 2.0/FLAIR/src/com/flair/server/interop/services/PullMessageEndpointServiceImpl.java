package com.flair.server.interop.services;

import com.flair.server.interop.MessagePipeline;
import com.flair.server.interop.ServerAuthenticationToken;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.services.PullMessageEndpointService;

public class PullMessageEndpointServiceImpl extends AbstractRemoteService implements PullMessageEndpointService
{
	@Override
	public ServerMessage[] dequeueMessages(AuthToken token) 
	{
		validateToken(token);
		
		ServerAuthenticationToken authToken = (ServerAuthenticationToken)token;
		return MessagePipeline.get().getQueuedMessages(authToken);
	}

}
