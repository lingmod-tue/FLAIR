package com.flair.shared.interop.services;

import com.flair.shared.exceptions.InvalidAuthTokenException;
import com.flair.shared.interop.AuthToken;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/*
 * Interface for session management
 */
@RemoteServiceRelativePath("SessionManagement")
public interface SessionManagementService extends RemoteService {
	AuthToken beginSession();                                           // invoked before all other server communication, returns the client's token
	void endSession(AuthToken token) throws InvalidAuthTokenException;  // invoked when the client ends their session
}
