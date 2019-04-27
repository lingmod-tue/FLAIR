package com.flair.server.interop;

import com.flair.server.interop.messaging.ServerMessagingSwitchboard;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdentificationToken;
import com.flair.shared.interop.FlairClientServerInteropService;
import com.flair.shared.interop.messaging.Message;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class FlairClientServerInteropServiceImpl extends RemoteServiceServlet implements FlairClientServerInteropService {
	private void validateClientId(ClientIdentificationToken clientId) throws InvalidClientIdentificationTokenException {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		ClientSessionManager.get().validateClientId(clientId, session);
	}

	@Override
	public ClientIdentificationToken SessionInitialize() {
		HttpSession session = getThreadLocalRequest().getSession(true);
		return ClientSessionManager.get().addSession(session);
	}

	@Override
	public void SessionTeardown(ClientIdentificationToken clientId) throws InvalidClientIdentificationTokenException {
		validateClientId(clientId);
		ClientSessionManager.get().removeSession(clientId);
	}

	@Override
	public void MessagingSend(Message<? extends Message.Payload> message) throws InvalidClientIdentificationTokenException {
		validateClientId(message.getClientId());
		ServerMessagingSwitchboard.get().onPushFromClient(message);
	}

	@Override
	public ArrayList<Message<? extends Message.Payload>> MessagingReceive(ClientIdentificationToken clientId) throws InvalidClientIdentificationTokenException {
		validateClientId(clientId);
		return ServerMessagingSwitchboard.get().onPullToClient(clientId);
	}
}