package com.flair.shared.interop;

import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.messaging.Message;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("ServerIO")
public interface FlairClientServerInteropService extends RemoteService {
	// invoked before all other server communication, returns the client's token
	ClientIdentificationToken SessionInitialize();

	// invoked when the client ends its session
	void SessionTeardown(ClientIdentificationToken clientId) throws InvalidClientIdentificationTokenException;

	// communication channel used by the client to send messages to the server
	void MessagingSend(Message<? extends Message.Payload> message) throws InvalidClientIdentificationTokenException;

	// communication channel polled by the client to receive messages from the server
	ArrayList<Message<? extends Message.Payload>> MessagingReceive(ClientIdentificationToken clientId) throws InvalidClientIdentificationTokenException;
}
