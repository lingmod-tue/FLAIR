package com.flair.shared.interop.services;

import com.flair.shared.exceptions.InvalidAuthTokenException;
import com.flair.shared.exceptions.ServerRuntimeException;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.messages.ServerMessage;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/*
 * Interface queried by the client to retrieve messages from the server
 */
@RemoteServiceRelativePath("PullMessage")
public interface PullMessageEndpointService extends RemoteService {
	ServerMessage[] dequeueMessages(AuthToken token) throws InvalidAuthTokenException, ServerRuntimeException;
}
