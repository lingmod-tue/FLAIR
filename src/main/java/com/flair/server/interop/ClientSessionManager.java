
package com.flair.server.interop;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks active client sessions and their attributes
 */
public class ClientSessionManager {
	private static ClientSessionManager SINGLETON = null;

	public static ClientSessionManager get() {
		if (SINGLETON == null) {
			synchronized (ClientSessionManager.class) {
				if (SINGLETON == null)
					SINGLETON = new ClientSessionManager();
			}
		}

		return SINGLETON;
	}

	public static void dispose() {
		if (SINGLETON != null) {
			SINGLETON.releaseSessions();
			SINGLETON = null;
		}
	}

	private static final class SessionMetadata {
		private final ClientIdToken token;
		private HttpSession session;
		private ClientSessionState state;

		SessionMetadata(ClientIdToken t, HttpSession s) {
			token = t;
			session = s;
			state = new ClientSessionState(t);
		}

		void release() {
			state.release();
		}
		ClientIdToken getToken() {
			return token;
		}
		ClientSessionState getState() {
			return state;
		}
		HttpSession getHttpSession() {
			return session;
		}
	}

	private final Map<ClientIdToken, SessionMetadata> activeSessions;

	private ClientSessionManager() {
		activeSessions = new ConcurrentHashMap<>();
	}

	private synchronized void releaseSessions() {
		try {
			for (SessionMetadata itr : activeSessions.values())
				itr.release();

			activeSessions.clear();
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Exception encountered while closing sessions: " + ex.toString());
		}
	}

	private SessionMetadata getSessionMetadata(ClientIdToken tok) {
		return activeSessions.get(tok);
	}

	private SessionMetadata getSessionMetadata(String tokUuid) {
		return getSessionMetadata(new ClientIdToken(tokUuid));
	}

	private void invalidateAndRemove(ClientIdToken tok) {
		SessionMetadata data = getSessionMetadata(tok);
		if (data == null)        // can be null if the session was previously invalidated
			return;

		data.release();
		activeSessions.remove(tok);
		ServerLogger.get().info("Released session. ClientID: " + tok);
	}

	private static ClientIdToken createNewClientIdentificationToken() {
		return new ClientIdToken(UUID.randomUUID().toString());
	}

	public synchronized ClientIdToken addSession(HttpSession httpSession) {
		ClientIdToken newTok = createNewClientIdentificationToken();
		ServerLogger.get().info("Opened new session. ClientID: " + newTok.toString());

		// bind the token to the session
		httpSession.setAttribute(newTok.toString(), newTok);
		activeSessions.put(newTok, new SessionMetadata(newTok, httpSession));

		return newTok;
	}

	public synchronized void removeSession(HttpSession oldSession) {
		// invalidate all associated clients
		Enumeration<String> boundUuids = oldSession.getAttributeNames();
		while (boundUuids.hasMoreElements()) {
			ClientIdToken tok = (ClientIdToken) oldSession.getAttribute(boundUuids.nextElement());
			invalidateAndRemove(tok);
		}
	}

	public synchronized void removeSession(ClientIdToken tok) {
		invalidateAndRemove(tok);
	}

	public synchronized void validateClientId(ClientIdToken tok, HttpSession session)
			throws InvalidClientIdentificationTokenException {
		SessionMetadata data = getSessionMetadata(tok);
		if (data == null) {
			// the http session was released, invalidate this instance of the client
			ServerLogger.get().error("Invalid client session token. ID: " + tok.getUuid());
			throw new InvalidClientIdentificationTokenException();
		} else if (session == null || session.isNew()) {
			// server session is not valid
			ServerLogger.get().error("Invalid server session for token. ID: " + tok.getUuid());
			throw new InvalidClientIdentificationTokenException();
		} else {
			HttpSession oldSession = data.getHttpSession();
			if (oldSession != session) {
				// should never happen!
				ServerLogger.get().error("Server session mismatch for token. ID: " + tok.getUuid());
				throw new InvalidClientIdentificationTokenException();
			}
		}
	}

	private static String getUploadFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename"))
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
		}

		return "";
	}

	synchronized void handleCorpusUpload(HttpServletRequest request) throws ServletException, IOException {
		HttpSession parentHttpSession = request.getSession();
		if (parentHttpSession == null)
			throw new IllegalStateException("Invalid HTTP Session");

		String clientUuid = request.getParameter(ClientIdToken.class.getSimpleName());
		if (clientUuid == null)
			throw new IllegalArgumentException("Request doesn't specify client identifier");

		SessionMetadata data = getSessionMetadata(clientUuid);
		if (data == null)
			throw new IllegalStateException("Invalid client identifier for corpus upload request");

		List<CustomCorpusFile> files = new ArrayList<>();
		for (Part part : request.getParts()) {
			String orgName = getUploadFileName(part);
			if (orgName.isEmpty())
				continue;

			int extIdx = orgName.lastIndexOf(".");
			if (extIdx != -1)
				orgName = orgName.substring(0, extIdx); // strip extension

			files.add(new CustomCorpusFile(part.getInputStream(), orgName));
		}

		data.getState().handleCorpusUpload(files);
	}
}

