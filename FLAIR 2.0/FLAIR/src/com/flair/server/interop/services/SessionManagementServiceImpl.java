package com.flair.server.interop.services;

import javax.servlet.http.HttpSession;

import com.flair.server.interop.ServerAuthenticationToken;
import com.flair.server.interop.session.SessionManager;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.services.SessionManagementService;

public class SessionManagementServiceImpl extends AbstractRemoteService implements SessionManagementService
{
	@Override
	public AuthToken beginSession() 
	{
		// create and init session data
		HttpSession session = getThreadLocalRequest().getSession(true);
		return SessionManager.get().addSession(session);
	}

	@Override
	public void endSession(AuthToken token) 
	{
		validateToken(token);

		ServerAuthenticationToken authToken = (ServerAuthenticationToken)token;
		SessionManager.get().removeSession(authToken);
	}

}
