package com.flair.client;

import com.flair.client.interop.FuncCallback;
import com.flair.client.interop.MessageReceiverFactory;
import com.flair.client.localization.LocalizationStringTable;
import com.flair.client.model.DocumentAnnotator;
import com.flair.client.model.DocumentRanker;
import com.flair.client.model.WebRankerCore;
import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.presentation.MainViewport;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.interop.AbstractMessageReceiver;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.services.SessionManagementServiceAsync;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * Manages basic client-side state and authentication
 */
public class ClientEndPoint
{
	private static final ClientEndPoint			INSTANCE = new ClientEndPoint();
	
	public static ClientEndPoint get() {
		return INSTANCE;
	}

	private AuthToken						clientToken;
	private MainViewport					viewport;
	private WebRankerCore					webranker;
	private SessionManagementServiceAsync	sessionService;
	private AbstractMessageReceiver			messagePipeline;
	
	private boolean							initialized;
	
	private ClientEndPoint()
	{
		clientToken = null;
		viewport = null;
		webranker = null;
		sessionService = null;
		messagePipeline = MessageReceiverFactory.get().create();
		
		initialized = false;
	}
	
	private void initiateServerHandshake()
	{
		viewport.setSplashTitle("");
		viewport.setSplashSubtitle("");
		viewport.showSplash(true);
		
		sessionService.beginSession(
			FuncCallback.get(r -> {
				clientToken = r;
				switch (clientToken.getStatus())
				{
				case INVALID_SERVER_ERROR:
					ClientLogger.get().error("Internal server error");
					viewport.setSplashTitle("Oh dear!");
					viewport.setSplashSubtitle("Something went wrong on our end. Please try again later.");
					break;
				case VALID:
					ClientLogger.get().info("Session token assigned. ID: " + clientToken);
					viewport.showSplash(false);
					
					Window.addCloseHandler(e -> {
						deinit();
					});
					
					// finish init
					webranker = new WebRankerCore(new DocumentRanker(),
												new DocumentAnnotator(),
												messagePipeline);
					webranker.init(clientToken, viewport);
					viewport.showDefaultPane(true);
					
					initialized = true;
					break;
				}
		}, c -> {
			ClientLogger.get().error(c, "Couldn't perform handshake with server");

			viewport.setSplashTitle("Oh dear!");
			viewport.setSplashSubtitle("Something went wrong on our end. Please try again later.");
		}));
	}
	
	public void init()
	{
		if (initialized)
			throw new RuntimeException("Client endpoint already initialized");
		
		// init'ed first to ensure localization providers are ready and available
		LocalizationStringTable.get().init();
		
		viewport = new MainViewport();
		sessionService = SessionManagementServiceAsync.Util.getInstance();
		
		RootPanel.get().add(viewport);
		initiateServerHandshake();
	}
	
	public void deinit()
	{
		sessionService.endSession(clientToken, FuncCallback.get(v -> {}, c -> {
			ClientLogger.get().error(c, "Couldn't deinitialize server session");
		}));
		
		if (messagePipeline.isOpen())
			messagePipeline.close();
		
		ClientLogger.get().info("Client endpoint deinitialized");
	}
	
	public AuthToken getClientToken() {
		return clientToken;
	}
	
	public AbstractWebRankerCore getWebRanker() {
		return webranker;
	}

	public AbstractWebRankerPresenter getViewport() {
		return viewport;
	}
	
	public AbstractMessageReceiver getMessagePipeline() {
		return messagePipeline;
	}

	public void fatalServerError()
	{
		viewport.setSplashTitle("Oh dear!");
		viewport.setSplashSubtitle("Your current browser session has expired. Please reload the page or try again later.");
		viewport.showSplash(true);
	}
}
