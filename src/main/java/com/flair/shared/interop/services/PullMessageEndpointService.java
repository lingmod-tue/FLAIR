package com.flair.shared.interop.services;

import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerMessage;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/*
 * Interface queried by the client to retrieve messages from the server
 */
@RemoteServiceRelativePath("PullMessage")
public interface PullMessageEndpointService extends RemoteService {
	public ServerMessage[] dequeueMessages(AuthToken token);
}
