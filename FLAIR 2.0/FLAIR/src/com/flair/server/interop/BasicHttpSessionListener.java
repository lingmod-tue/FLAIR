/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.interop;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.flair.server.interop.session.SessionManager;

/**
 * Handles HTTP session creation and deletion(in the servlet context)
 * 
 * @author shadeMe
 */
public class BasicHttpSessionListener implements HttpSessionListener
{
	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		// nothing yet
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) 
	{
		// release corresponding session, it'll be renewed on the next server interaction
		HttpSession destroyedSession = hse.getSession();
		SessionManager.get().removeSession(destroyedSession);
	}
}
