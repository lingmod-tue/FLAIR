
package com.flair.server.interop;

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
		HttpSession destroyedSession = hse.getSession();
		ClientSessionManager.get().removeSession(destroyedSession);
	}
}
