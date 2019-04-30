package com.flair.shared.interop;

import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.messaging.Message;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("InteropEndpoint")
public interface InteropService extends RemoteService {
	// invoked before all other server communication, returns the client's token
	ClientIdToken SessionInitialize();

	// invoked when the client ends its session
	void SessionTeardown(ClientIdToken clientId) throws InvalidClientIdentificationTokenException;

	// communication channel used by the client to send messages to the server
	void MessagingSend(Message<? extends Message.Payload> message) throws InvalidClientIdentificationTokenException;

	// communication channel polled by the client to receive messages from the server
	ArrayList<Message<? extends Message.Payload>> MessagingReceive(ClientIdToken clientId) throws InvalidClientIdentificationTokenException;
}
