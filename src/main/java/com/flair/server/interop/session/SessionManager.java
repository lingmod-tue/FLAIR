/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.interop.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
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
	}

	private final HashMap<ServerAuthenticationToken, BasicSessionData>		activeSessions;

	private SessionManager()
	{
		activeSessions = new HashMap<>();
	}

	private synchronized void releaseSessions()
	{
		try
		{
			for (BasicSessionData itr : activeSessions.values())
				itr.release();
			
			activeSessions.clear();
		} catch (Throwable ex)
		{
			ServerLogger.get().error(ex, "Exception encountered while closing sessions: " + ex.toString());
		}
	}

	private BasicSessionData getSessionData(ServerAuthenticationToken tok) {
		return activeSessions.get(tok);
	}
	
	private BasicSessionData getSessionData(String tokUuid)
	{
		for (ServerAuthenticationToken itr : activeSessions.keySet())
		{
			if (tokUuid.equals(itr.getUuid()))
				return activeSessions.get(itr);
		}
		
		return null;
	}

	private void invalidateAndRemove(ServerAuthenticationToken tok)
	{
		BasicSessionData data = getSessionData(tok);
		if (data == null)		// can be null if the session was previously invalidated
			return;
		
		data.release();
		activeSessions.remove(tok);
		ServerLogger.get().info("Session token " + data.getToken().getUuid() + " released");
	}
	
	public synchronized ServerAuthenticationToken addSession(HttpSession httpSession)
	{
		ServerAuthenticationToken newTok = AuthTokenGenerator.create();
		ServerLogger.get().info("New session token generated. ID: " + newTok.getUuid());
		
		// bind the token to the session
		httpSession.setAttribute(newTok.toString(), newTok);
		activeSessions.put(newTok, new BasicSessionData(newTok, httpSession));
		
		return newTok;
	}
	

	public synchronized void removeSession(HttpSession oldSession)
	{
		// invalidete all associated clients
		Enumeration<String> boundUuids = oldSession.getAttributeNames();
		while (boundUuids.hasMoreElements())
		{
			ServerAuthenticationToken tok = (ServerAuthenticationToken)oldSession.getAttribute(boundUuids.nextElement());
			invalidateAndRemove(tok);
		}
	}
	
	public synchronized void removeSession(ServerAuthenticationToken tok) {
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
	
	public synchronized void validateToken(ServerAuthenticationToken tok, HttpSession session)
	{
		BasicSessionData data = getSessionData(tok);
		if (data == null)
		{
			// the http session was released, invalidate this instance of the client
			ServerLogger.get().error("Invalid client session token. ID: " + tok.getUuid());
			throw new InvalidAuthTokenException();
		}
		else if (session == null || session.isNew())
		{
			// server session is not valid
			ServerLogger.get().error("Invalid server session for token. ID: " + tok.getUuid());
			throw new InvalidAuthTokenException();
		}
		else
		{
			HttpSession oldSession = data.getHttpSession();
			if (oldSession != session)
			{
				// should never happen!
				ServerLogger.get().error("Server session mismatch for token. ID: " + tok.getUuid());
				throw new InvalidAuthTokenException();
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

		String clientUuid = request.getParameter(AuthToken.class.getSimpleName());
		if (clientUuid == null)
			throw new IllegalArgumentException("Request doesn't specify client identifier");
			
		BasicSessionData data = getSessionData(clientUuid);
		if (data == null)
			throw new IllegalStateException("Invalid client identifier for corpus upload request");

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
