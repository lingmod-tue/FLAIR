package com.flair.client;

import java.util.UUID;

import com.flair.client.localization.LocalizationEngine;
import com.flair.client.views.MainViewport;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * Client-side state manager
 */
public class ClientEndPoint 
{
	private static final ClientEndPoint			INSTANCE = new ClientEndPoint();
	
	public static ClientEndPoint get() {
		return INSTANCE;
	}

	private String					clientId;			// UUID used for server-client interop
	private MainViewport			viewport;
	private WebRankerCore			webranker;
	private LocalizationEngine		localeCore;		
	private boolean					initialized;
	
	private ClientEndPoint() 
	{
		clientId = "";
		viewport = null;
		webranker = null;
		localeCore = new LocalizationEngine();
		
		initialized = false;
	}
	
	public void init()
	{
		if (initialized)
			throw new RuntimeException("Client endpoint already initialized");
		
		initialized = true;
		
		// ### TODO get the client id from the server
		viewport = new MainViewport();
		webranker = new WebRankerCore();
		
		RootPanel.get().add(viewport);
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public WebRankerCore getWebRanker() {
		return webranker;
	}
	
	public LocalizationEngine getLocalization() {
		return localeCore;
	}
}
