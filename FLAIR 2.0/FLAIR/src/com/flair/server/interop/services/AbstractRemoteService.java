package com.flair.server.interop.services;

import javax.servlet.http.HttpServletRequest;

import com.flair.server.interop.ServerAuthenticationToken;
import com.flair.server.interop.session.SessionManager;
import com.flair.shared.interop.AuthToken;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/*
 * Implements basic validation helper methods
 */
public abstract class AbstractRemoteService extends RemoteServiceServlet
{
	protected void validateToken(AuthToken token)
	{
		HttpServletRequest request = this.getThreadLocalRequest();
		ServerAuthenticationToken authTok = (ServerAuthenticationToken)token;
		
		SessionManager.get().validateToken(authTok, request.getSession(true));
	}
}
