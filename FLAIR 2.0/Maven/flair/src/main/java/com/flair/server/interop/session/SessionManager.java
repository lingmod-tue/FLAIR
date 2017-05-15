/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.interop.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.flair.server.interop.AuthTokenGenerator;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.InvalidAuthTokenException;
import com.flair.shared.interop.ServerAuthenticationToken;

/**
 * Tracks active sessions and its attributes
 * 
 * @author shadeMe
 */
public class SessionManager
{
	private static SessionManager SINGLETON = null;

	public static SessionManager get()
	{
		if (SINGLETON == null)
		{
			synchronized (SessionManager.class)
			{
				if (SINGLETON == null)
					SINGLETON = new SessionManager();
			}
		}

		return SINGLETON;
	}

	public static void dispose()
	{
		if (SINGLETON != null)
		{
			SINGLETON.releaseSessions();
			SINGLETON = null;
		}
	}
	
	private static final class BasicSessionData
	{
		private final ServerAuthenticationToken		token;
		private HttpSession							session;
		private SessionState						state;
		
		public BasicSessionData(ServerAuthenticationToken t, HttpSession s)
		{
			token = t;
			session = s;
			state = new SessionState(t);
		}
		
		public void release() {
			state.release();
		}
		
		public ServerAuthenticationToken getToken() {
			return token;
		}
		
		public SessionState getState() {
			return state;
		}
		
		public HttpSession getHttpSession() {
			return session;
		}
		
		public void setHttpSession(HttpSession val) {
			session = val;
		}
	}

	private final HashMap<ServerAuthenticationToken, BasicSessionData>		activeSessions;
	private final HashMap<HttpSession, ServerAuthenticationToken>			http2Token;

	private SessionManager()
	{
		activeSessions = new HashMap<>();
		http2Token = new HashMap<>();
	}

	private synchronized void releaseSessions()
	{
		try
		{
			for (BasicSessionData itr : activeSessions.values())
				itr.release();
			
			activeSessions.clear();
			http2Token.clear();
		} catch (Throwable ex)
		{
			ServerLogger.get().error(ex, "Exception encountered while closing sessions: " + ex.toString());
		}
	}

	private HttpSession getHttpSession(ServerAuthenticationToken token)
	{
		BasicSessionData data = activeSessions.get(token);
		if (data != null)
			return data.getHttpSession();
		else
			return null;
	}

	private BasicSessionData getSessionData(HttpSession httpSession)
	{
		ServerAuthenticationToken tok = http2Token.get(httpSession);
		if (tok != null)
			return activeSessions.get(tok);
		else
			return null;
	}
	
	private BasicSessionData getSessionData(ServerAuthenticationToken tok) {
		return activeSessions.get(tok);
	}

	private void invalidateAndRemove(ServerAuthenticationToken tok)
	{
		BasicSessionData data = getSessionData(tok);
		data.release();
		http2Token.remove(data.session);
		
		ServerLogger.get().info("Session token " + data.getToken().getUuid() + " released");
	}
	
	public synchronized ServerAuthenticationToken addSession(HttpSession httpSession)
	{
		ServerAuthenticationToken newTok = AuthTokenGenerator.create(), old = http2Token.get(httpSession);
		if (old != null)
		{
			newTok.setStatus(AuthToken.Status.INVALID_SESSION_EXISTS);
			ServerLogger.get().error("Token already exists for session. Existing ID: " + old.getUuid());
		}
		else
		{
			activeSessions.put(newTok, new BasicSessionData(newTok, httpSession));
			http2Token.put(httpSession, newTok);
			ServerLogger.get().info("New session token generated. ID: " + newTok.getUuid());
		}
		
		return newTok;
	}
	

	public synchronized void removeSession(HttpSession oldSession)
	{
		if (http2Token.containsKey(oldSession))
			invalidateAndRemove(http2Token.get(oldSession));
	}
	
	public synchronized void removeSession(ServerAuthenticationToken tok)
	{
		if (activeSessions.containsKey(tok) == false)
			throw new IllegalArgumentException("Session token is not tracked. ID: " + tok.getUuid());
		
		invalidateAndRemove(tok);
	}
	
	public synchronized SessionState getSessionState(ServerAuthenticationToken tok)
	{
		BasicSessionData data = getSessionData(tok);
		if (data != null)
			return data.getState();
		else
			return null;
	}
	
	public synchronized SessionState getSessionState(HttpSession session)
	{
		BasicSessionData data = getSessionData(session);
		if (data != null)
			return data.getState();
		else
			return null;
	}
	
	public synchronized void validateToken(ServerAuthenticationToken tok, HttpSession session)
	{
		BasicSessionData data = getSessionData(tok);
		if (data == null)
		{
			// the previous session was released
			if (getSessionData(session) != null)
			{
				// there's another instance of the client using the same/current session
				// invalidate this instance of the client
				throw new InvalidAuthTokenException();
			}
			
			activeSessions.put(tok, new BasicSessionData(tok, session));
			http2Token.put(session, tok);
			ServerLogger.get().info("Renewed session token. ID: " + tok.getUuid());
		}
		else
		{
			HttpSession oldSession = data.getHttpSession();
			if (oldSession != session)
			{
				data.setHttpSession(session);		// update the cached session
				http2Token.remove(oldSession);
				http2Token.put(session, tok);
			}
		}
	}
	
	private String getUploadFileName(Part part)
	{
		String contentDisp = part.getHeader("content-disposition");
		String[] tokens = contentDisp.split(";");
		for (String token : tokens)
		{
			if (token.trim().startsWith("filename"))
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
		}

		return "";
	}

	public synchronized void handleCorpusUpload(HttpServletRequest request) throws ServletException, IOException
	{
		HttpSession parentHttpSession = request.getSession();
		if (parentHttpSession == null)
			throw new IllegalStateException("Invalid HTTP Session");

		BasicSessionData data = getSessionData(parentHttpSession);
		if (data == null)
			throw new IllegalStateException("HTTP session is not tracked");

		List<CustomCorpusFile> files = new ArrayList<>();
		
		for (Part part : request.getParts())
		{
			String orgName = getUploadFileName(part);
			if (orgName.isEmpty())
				continue;

			int extIdx = orgName.lastIndexOf(".");
			if (extIdx != -1)
				orgName = orgName.substring(0, extIdx); // strip extension

			files.add(new CustomCorpusFile(part.getInputStream(), orgName));
			ServerLogger.get().info("Uploaded custom corpus file " + orgName);
		}
		
		data.getState().handleCorpusUpload(files);
	}
}

final class CustomCorpusFile
{
	private final InputStream	stream;
	private final String		fileName;

	public CustomCorpusFile(InputStream input, String fileName)
	{
		this.stream = input;
		this.fileName = fileName;
	}
	
	public InputStream getStream() {
		return stream;
	}
	
	public String getFilename() {
		return fileName;
	}
}
