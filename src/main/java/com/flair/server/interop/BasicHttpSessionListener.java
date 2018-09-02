
package com.flair.server.interop;

import com.flair.server.interop.session.SessionManager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Handles HTTP session creation and deletion(in the servlet context)
 */
public class BasicHttpSessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		// nothing yet
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		// release corresponding session, it'll be renewed on the next server interaction
		HttpSession destroyedSession = hse.getSession();
		SessionManager.get().removeSession(destroyedSession);
	}
}
