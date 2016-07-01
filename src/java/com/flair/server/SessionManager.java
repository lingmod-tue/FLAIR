/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server;

import com.flair.utilities.FLAIRLogger;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Tracks active sessions and its attributes
 * @author shadeMe
 */
public class SessionManager
{
    private static SessionManager	    SINGLETON = null;
    
    public static SessionManager get()
    {
	if (SINGLETON == null)
	{
	    synchronized(SessionManager.class)
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
        
    private final HashMap<Session, SessionState>	    activeSessions;
    private final HashMap<HttpSession, Session>		    httpToWebSocket;
    private final HashMap<Session, HttpSession>		    webSocketToHttp;
    
    private SessionManager()
    {
	activeSessions = new HashMap<>();
	httpToWebSocket = new HashMap<>();
	webSocketToHttp = new HashMap<>();
    }

    private synchronized void releaseSessions()
    {
	for (Entry<Session, SessionState> itr : activeSessions.entrySet())
	{
	    itr.getValue().release();
	}
	
	activeSessions.clear();
	httpToWebSocket.clear();
	webSocketToHttp.clear();
    }
    
    public synchronized void addSession(Session newSession, HttpSession httpSession)
    {
	if (activeSessions.containsKey(newSession))
	    throw new IllegalArgumentException("Session already added");
	else if (newSession.isOpen() == false)
	    throw new IllegalArgumentException("Session is not open");
	
	activeSessions.put(newSession, new SessionState(newSession));
	httpToWebSocket.put(httpSession, newSession);
	webSocketToHttp.put(newSession, httpSession);
	
	FLAIRLogger.get().info("New WebSocket session opened. ID: "+ newSession.getId());
    }
    
    public synchronized void removeSession(Session oldSession)
    {
	if (activeSessions.containsKey(oldSession) == false)
	    throw new IllegalArgumentException("Session is not tracked");
	
	SessionState state = activeSessions.get(oldSession);
	state.release();
	activeSessions.remove(oldSession);
	
	HttpSession oldHttp = webSocketToHttp.get(oldSession);
	httpToWebSocket.remove(oldHttp);
	webSocketToHttp.remove(oldSession);
	
	FLAIRLogger.get().info("WebSocket session " + oldSession.getId() + " closed");
    }
    
    public synchronized void routeMessage(String message, Session session)
    {
	if (activeSessions.containsKey(session) == false)
	    throw new IllegalArgumentException("Session is not tracked");
	
	activeSessions.get(session).handleMessage(message);
    }
}
