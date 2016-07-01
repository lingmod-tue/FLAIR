/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server;

import com.flair.utilities.FLAIRLogger;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Websocket endpoint for the search results ranking client
 * @author shadeMe
 */

@ServerEndpoint(value = "/webranker", configurator = HttpSessionConfigurator.class)
public class WebRankerEndPoint
{
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
	HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
	SessionManager.get().addSession(session, httpSession);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
	SessionManager.get().routeMessage(message, session);
    }
    
    @OnClose
    public void onClose(Session session) {
	SessionManager.get().removeSession(session);
    }
    
    @OnError
    public void onError(Session session, Throwable thr) 
    {
	StringWriter sw = new StringWriter();
	thr.printStackTrace(new PrintWriter(sw));
	FLAIRLogger.get().error("WebRankerEndPoint error for session " + session.getId() + ": " + thr.toString());
	FLAIRLogger.get().error("Stacktrack: " + sw.toString());
    }
}
