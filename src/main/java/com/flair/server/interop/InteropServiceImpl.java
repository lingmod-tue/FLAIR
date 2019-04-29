package com.flair.server.interop;

import com.flair.server.interop.messaging.ServerMessagingSwitchboard;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdToken;
import com.flair.shared.interop.InteropService;
import com.flair.shared.interop.messaging.Message;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class InteropServiceImpl extends RemoteServiceServlet implements InteropService {
	private void validateClientId(ClientIdToken clientId) throws InvalidClientIdentificationTokenException {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		ClientSessionManager.get().validateClientId(clientId, session);
	}

	@Override
	public ClientIdToken SessionInitialize() {
		HttpSession session = getThreadLocalRequest().getSession(true);
		return ClientSessionManager.get().addSession(session);
	}

	@Override
	public void SessionTeardown(ClientIdToken clientId) throws InvalidClientIdentificationTokenException {
		validateClientId(clientId);
		ClientSessionManager.get().removeSession(clientId);
	}

	@Override
	public void MessagingSend(Message<? extends Message.Payload> message) throws InvalidClientIdentificationTokenException {
		validateClientId(message.getClientId());
		ServerMessagingSwitchboard.get().onPushFromClient(message);
	}

	@Override
	public ArrayList<Message<? extends Message.Payload>> MessagingReceive(ClientIdToken clientId) throws InvalidClientIdentificationTokenException {
		validateClientId(clientId);
		return ServerMessagingSwitchboard.get().onPullToClient(clientId);
	}
}