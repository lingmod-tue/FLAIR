/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server;

import com.flair.utilities.FLAIRLogger;
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
// ### TODO grab the HttpSession like so - http://stackoverflow.com/questions/21888425/accessing-servletcontext-and-httpsession-in-onmessage-of-a-jsr-356-serverendpo

@ServerEndpoint("/webranker")
public class WebRankerEndPoint
{
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
	SessionManager.get().addSession(session);
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
    public void onError(Session session, Throwable thr) {
	FLAIRLogger.get().error("WebRankerEndPoint error for session " + session.getId() + ": " + thr.toString());
    }
}
