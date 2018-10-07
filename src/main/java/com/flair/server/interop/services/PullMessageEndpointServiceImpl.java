package com.flair.server.interop.services;

import com.flair.server.interop.MessagePipe;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerAuthenticationToken;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.services.PullMessageEndpointService;

public class PullMessageEndpointServiceImpl extends AbstractRemoteService implements PullMessageEndpointService {
	@Override
	public ServerMessage[] dequeueMessages(AuthToken token) {
		ServerAuthenticationToken authToken = validateToken(token);
		return MessagePipe.get().getQueuedMessages(authToken);
	}

}
