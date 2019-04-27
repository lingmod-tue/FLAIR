package com.flair.server.interop.messaging;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdentificationToken;
import com.flair.shared.interop.messaging.Message;
import com.flair.shared.interop.messaging.server.SmClientMessageConsumed;
import com.flair.shared.interop.messaging.server.SmError;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
 * Coordinates the communication channels between the server and connected clients
 */
public class ServerMessagingSwitchboard {
	private static ServerMessagingSwitchboard SINGLETON = null;

	public static ServerMessagingSwitchboard get() {
		if (SINGLETON == null) {
			synchronized (ServerMessagingSwitchboard.class) {
				if (SINGLETON == null)
					SINGLETON = new ServerMessagingSwitchboard();
			}
		}

		return SINGLETON;
	}

	public static void dispose() {
		if (SINGLETON != null) {
			SINGLETON.shutdown();
			SINGLETON = null;
		}
	}

	private synchronized void shutdown() {
		for (ServerMessageChannelImpl channel : clientId2Channel.values())
			channel.dispose();

		clientId2Channel.clear();
		channel2ClientId.clear();
	}

	private class ServerMessageChannelImpl implements ServerMessageChannel {
		private static final long MAX_OUT_OF_ORDER_MESSAGES = 10;

		private final ClientIdentificationToken clientId;
		private final List<Message<?>> inbox;
		private Queue<Message<?>> outbox;
		private final Map<Class<?>, MessageReceivedHandler<?>> messageHandlers;
		private long previousIncomingMessageId;
		private long nextOutgoingMessageId;
		private long numOutOfOrderMessages;
		private boolean channelOpen;

		private void sendMessageHandlingFatalErrorResponse(Throwable ex, String msg) {
			SmError error = new SmError();
			error.setException(ex);
			error.setFatal(true);
			error.setMessage(msg);
			send(new Message<>(error));
		}

		private <P extends Message.Payload> void handleMessage(Message<P> msg) {
			ClientIdentificationToken sender = msg.getClientId();
			long messageId = msg.getMessageId();
			P payload = msg.getPayload();
			MessageReceivedHandler<P> handler = (MessageReceivedHandler<P>) messageHandlers.get(payload.getClass());

			if (handler == null)
				throw new IllegalArgumentException("No handler for message " + payload.name());
			else {
				ServerLogger.get().trace("New message:").indent()
						.trace("Sender:" + sender.getUuid() + " (messageID:" + messageId + ")")
						.trace("Name: " + payload.name())
						.trace("Contents: " + payload.desc()).exdent();

				try {
					handler.read(messageId, payload);
				} catch (Throwable ex) {
					ServerLogger.get().error(ex, "Message handler raised an exception! Exception: " + ex);
					sendMessageHandlingFatalErrorResponse(ex, "Exception raised when handling the following message: " + msg);
				}
				previousIncomingMessageId = messageId;
			}
		}

		private synchronized void onPushFromClient(Message<? extends Message.Payload> message) {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			if (!message.getClientId().equals(clientId)) {
				ServerLogger.get().error("Message channel client ID mismatch!").indent()
						.error("Expected: " + clientId)
						.error("Got: " + message.getClientId().toString())
						.error("Message: " + message.toString())
						.exdent();
				return;
			}

			long receivedMessageId = message.getMessageId();
			if (receivedMessageId == previousIncomingMessageId + 1) {
				// received the next message in the sequence
				// resort pending messages in the inbox and handle them inorder
				if (inbox.isEmpty())
					handleMessage(message);
				else {
					ServerLogger.get().trace("Received older message " + receivedMessageId + " as expected. Pending messages: " + inbox.size());

					numOutOfOrderMessages = 0;
					inbox.add(message);
					inbox.sort(Comparator.comparingLong(Message::getMessageId));

					long nextMessageId = receivedMessageId - 1;
					for (Message<? extends Message.Payload> m : inbox) {
						if (m.getMessageId() != nextMessageId)
							ServerLogger.get().warn("Message " + nextMessageId + " was lost! Previously handled message: " + previousIncomingMessageId);

						handleMessage(m);
						++nextMessageId;
					}

					inbox.clear();
				}
			} else if (receivedMessageId > previousIncomingMessageId) {
				if (numOutOfOrderMessages++ > MAX_OUT_OF_ORDER_MESSAGES) {
					ServerLogger.get().error("Received too many messages out of order!");
					// ### TODO this is fatal, communicate with the client
					return;
				}

				long delta = receivedMessageId - previousIncomingMessageId;
				ServerLogger.get().trace("Received newer message " + receivedMessageId + " out-of-order. Delta: " + delta + " | Awaiting older message...");
				inbox.add(message);
			} else if (receivedMessageId < previousIncomingMessageId) {
				ServerLogger.get().warn("Time-travelling message " + receivedMessageId + " encountered! Previously handled message: " + previousIncomingMessageId)
						.indent().warn("Message: " + message.toString()).exdent();
			} else {
				ServerLogger.get().warn("Duplicate message " + receivedMessageId + " encountered! Previously handled message: " + previousIncomingMessageId)
						.indent().warn("Message: " + message.toString()).exdent();
			}
		}

		private synchronized ArrayList<Message<? extends Message.Payload>> onPullToClient(int numMessages) {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			if (numMessages <= 0)
				numMessages = outbox.size();
			else if (numMessages > outbox.size())
				numMessages = outbox.size();

			ArrayList<Message<? extends Message.Payload>> out = new ArrayList<>();
			for (int i = 0; i < numMessages; i++)
				out.add(outbox.poll());

			return out;
		}

		private synchronized void dispose() {
			if (!channelOpen)
				throw new IllegalStateException("Message channel for client " + clientId + " has already been disposed");

			if (!inbox.isEmpty())
				ServerLogger.get().warn("Message channel for client " + clientId + " has " + inbox.size() + " pending messages in the inbox");
			if (!outbox.isEmpty())
				ServerLogger.get().warn("Message channel for client " + clientId + " has " + outbox.size() + " pending messages in the outbox");

			channelOpen = false;
			messageHandlers.clear();
			inbox.clear();
			outbox.clear();
		}

		ServerMessageChannelImpl(ClientIdentificationToken clientId) {
			this.clientId = clientId;
			this.inbox = new ArrayList<>();
			this.outbox = new ArrayDeque<>();
			this.messageHandlers = new HashMap<>();
			this.previousIncomingMessageId = Message.INVALID_ID;
			this.nextOutgoingMessageId = Message.INVALID_ID + 1;
			this.numOutOfOrderMessages = 0;
			this.channelOpen = true;
		}

		@Override
		public ClientIdentificationToken clientId() {
			return clientId;
		}

		@Override
		public synchronized <P extends Message.Payload> void addReceiveHandler(Class<P> payloadClass,
		                                                                       MessageReceivedHandler<P> handler) {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			if (messageHandlers.put(payloadClass, handler) != null)
				throw new IllegalArgumentException("Message handler exists for class " + payloadClass.getSimpleName());
		}

		@Override
		public synchronized void send(Message<? extends Message.Payload> message) {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			message.setClientId(clientId);
			message.setMessageId(nextOutgoingMessageId++);
			if (message.getPayload() == null)
				throw new IllegalArgumentException("Message for client " + clientId + " has no payload!");

			outbox.add(message);
		}

		@Override
		public synchronized void clearPendingMessages(boolean keepClientMessageConsumedMessages) {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			if (!keepClientMessageConsumedMessages)
				outbox.clear();
			else {
				outbox = outbox.stream().filter(e -> !(e.getPayload() instanceof SmClientMessageConsumed))
						.sorted(Comparator.comparingLong(Message::getMessageId)).collect(Collectors.toCollection(ArrayDeque::new));
			}
		}
	}

	private final Map<ClientIdentificationToken, ServerMessageChannelImpl> clientId2Channel;
	private final Map<ServerMessageChannelImpl, ClientIdentificationToken> channel2ClientId;

	private ServerMessagingSwitchboard() {
		clientId2Channel = new ConcurrentHashMap<>();
		channel2ClientId = new ConcurrentHashMap<>();
	}

	public synchronized ServerMessageChannel openChannel(ClientIdentificationToken clientId) {
		if (clientId2Channel.containsKey(clientId))
			throw new IllegalArgumentException("Multiple message channels for client " + clientId);

		ServerMessageChannelImpl newChannel = new ServerMessageChannelImpl(clientId);
		clientId2Channel.put(clientId, newChannel);
		channel2ClientId.put(newChannel, clientId);

		return newChannel;
	}

	public synchronized void closeChannel(ServerMessageChannel channel) {
		ServerMessageChannelImpl channelImpl = (ServerMessageChannelImpl) channel;
		if (channelImpl == null)
			throw new IllegalArgumentException("Unknown message channel");

		ClientIdentificationToken mapped = channel2ClientId.get(channelImpl);
		if (mapped == null)
			throw new IllegalArgumentException("Unknown message channel for client " + channel.clientId().toString());
		else if (!mapped.equals(channel.clientId()))
			throw new IllegalStateException("Unexpected clientId for message channel");

		channelImpl.dispose();
		clientId2Channel.remove(mapped);
		channel2ClientId.remove(channel);
	}


	public void onPushFromClient(Message<? extends Message.Payload> message) {
		ServerMessageChannelImpl messageChannel = clientId2Channel.get(message.getClientId());
		if (messageChannel == null) {
			ServerLogger.get().error("Received message from unknown client!").indent().error("Message: " + message.toString()).exdent();
			throw new InvalidClientIdentificationTokenException();
		}

		messageChannel.onPushFromClient(message);
	}

	public ArrayList<Message<? extends Message.Payload>> onPullToClient(ClientIdentificationToken clientId) {
		ServerMessageChannelImpl messageChannel = clientId2Channel.get(clientId);
		if (messageChannel == null) {
			ServerLogger.get().error("Received message from unknown client " + clientId);
			throw new InvalidClientIdentificationTokenException();
		}

		return messageChannel.onPullToClient(-1);
	}
}
