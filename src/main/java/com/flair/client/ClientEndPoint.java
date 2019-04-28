package com.flair.client;

import com.flair.client.interop.messaging.ClientsideInteropBus;
import com.flair.client.localization.GrammaticalConstructionLocalizationProvider;
import com.flair.client.localization.LocalizationStringTable;
import com.flair.client.model.DocumentAnnotator;
import com.flair.client.model.DocumentRanker;
import com.flair.client.model.WebRankerCore;
import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.presentation.MainViewport;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.interop.ClientIdentificationToken;
import com.flair.shared.interop.FlairClientServerInteropServiceAsync;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * Manages basic client-side state and authentication
 */
public class ClientEndPoint {
	private static final ClientEndPoint INSTANCE = new ClientEndPoint();

	public static ClientEndPoint get() {
		return INSTANCE;
	}

	private ClientIdentificationToken clientToken;
	private MainViewport viewport;
	private WebRankerCore webranker;
	private FlairClientServerInteropServiceAsync interopService;
	private ClientsideInteropBus messageChannel;
	private boolean initialized;

	private ClientEndPoint() {
		clientToken = null;
		viewport = null;
		webranker = null;
		interopService = FlairClientServerInteropServiceAsync.Util.getInstance();
		messageChannel = null;
		initialized = false;
	}

	public void init() {
		if (initialized)
			throw new RuntimeException("Client endpoint already initialized");

		// init'ed first to ensure localization providers are ready and available
		LocalizationStringTable.get().init();

		viewport = new MainViewport();
		RootPanel.get().add(viewport);

		viewport.setSplashTitle("");
		viewport.setSplashSubtitle("");
		viewport.showSplash(true);

		// establish handshake with the server
		AsyncCallback<ClientIdentificationToken> handshakeCallback = new AsyncCallback<ClientIdentificationToken>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientLogger.get().error(caught, "Couldn't perform handshake with server");

				viewport.setSplashTitle("Oh dear!");
				viewport.setSplashSubtitle("Something went wrong on our end. Please try again later.");
			}
			@Override
			public void onSuccess(ClientIdentificationToken result) {
				clientToken = result;
				messageChannel = new ClientsideInteropBus(clientToken);

				ClientLogger.get().info("Session token assigned. ID: " + clientToken);
				viewport.showSplash(false);

				Window.addCloseHandler(e -> deinit());

				// finish init
				webranker = new WebRankerCore(messageChannel, new DocumentRanker(), new DocumentAnnotator());
				webranker.init(viewport);
				viewport.showDefaultPane(true);
				GrammaticalConstructionLocalizationProvider.bindToWebRankerCore(webranker);

				initialized = true;
			}
		};
		interopService.SessionInitialize(handshakeCallback);
	}

	public void deinit() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientLogger.get().error(caught, "Couldn't deinitialize server session");
			}
			@Override
			public void onSuccess(Void result) {}
		};
		interopService.SessionTeardown(clientToken, callback);
		ClientLogger.get().info("Client endpoint deinitialized");
	}

	public ClientIdentificationToken getClientIdentificationToken() {
		return clientToken;
	}

	public AbstractWebRankerCore getWebRanker() {
		return webranker;
	}

	public AbstractWebRankerPresenter getViewport() {
		return viewport;
	}

	public ClientsideInteropBus getMessageChannel() {
		return messageChannel;
	}

	public void fatalServerError() {
		viewport.setSplashTitle("Oh dear!");
		viewport.setSplashSubtitle("Your current browser session has expired. Please reload the page or try again later.");
		viewport.showSplash(true);
	}
}
