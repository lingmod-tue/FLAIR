package com.flair.server.interop.services;

import com.flair.server.interop.MessagePipe;
import com.flair.shared.interop.*;
import com.flair.shared.interop.services.PullMessageEndpointService;

public class PullMessageEndpointServiceImpl extends AbstractRemoteService implements PullMessageEndpointService {
	@Override
	public ServerMessage[] dequeueMessages(AuthToken token) throws InvalidAuthTokenException, ServerRuntimeException {
		ServerAuthenticationToken authToken = validateToken(token);
		return MessagePipe.get().getQueuedMessages(authToken);
	}

}
