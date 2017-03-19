package com.flair.shared.interop.services;

import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerMessage;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/*
 * Interface queried by the client to retrieve messages from the server
 */
@RemoteServiceRelativePath("SessionManagement")
public interface SessionManagementService extends RemoteService
{
	public AuthToken		beginSession();					// invoked before all other server communications, returns the client's token
	public void				endSession(AuthToken token);	// invoked when the client ends their session
}
