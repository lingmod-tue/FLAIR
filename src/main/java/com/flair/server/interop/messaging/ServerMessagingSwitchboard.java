package com.flair.server.interop.messaging;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdToken;
import com.flair.shared.interop.messaging.Message;
import com.flair.shared.interop.messaging.MessageReceivedHandler;
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

		private final ClientIdToken clientId;
		private final List<Message<?>> inbox;
		private Queue<Message<?>> outbox;
		private final Map<Class<?>, MessageReceivedHandler<?>> messageHandlers;
		private long previousIncomingMessageId;
		private long nextOutgoingMessageId;
		private long numOutOfOrderMessages;
		private boolean channelOpen;

		private <P extends Message.Payload> void handleMessage(Message<P> msg) {
			ClientIdToken sender = msg.getClientId();
			long messageId = msg.getMessageId();
			P payload = msg.getPayload();
			MessageReceivedHandler<P> handler = (MessageReceivedHandler<P>) messageHandlers.get(payload.getClass());

			if (handler == null)
				throw new IllegalArgumentException("No handler for message " + payload.name());

			ServerLogger.get().trace("New message:").indent()
					.trace("Sender:" + sender.getUuid() + " (messageID:" + messageId + ")")
					.trace("Name: " + payload.name())
					.trace("Contents: " + payload.desc()).exdent();


			// queue the success message ahead of time in case the handler sends its own messages
			// this will preserve the expected order of the messages
			SmClientMessageConsumed ackClentMsg = new SmClientMessageConsumed();
			ackClentMsg.setClientMessageId(messageId);
			ackClentMsg.setSuccess(true);
			send(ackClentMsg);

			try {
				handler.read(payload);
			} catch (Throwable ex) {
				// update the success message to reflect failure
				SmError error = new SmError();
				error.setException(ex);
				error.setFatal(false);
				error.setMessage("Message handler raised an exception! Exception: " + ex);

				ackClentMsg.setSuccess(false);
				ackClentMsg.setError(error);
				ServerLogger.get().error(ex, error.getMessage());
			}
			previousIncomingMessageId = messageId;
		}

		private void processPendingInboxMessages() {
			inbox.sort(Comparator.comparingLong(Message::getMessageId));

			long nextMessageId = inbox.get(0).getMessageId();
			for (Message<? extends Message.Payload> m : inbox) {
				if (m.getMessageId() != nextMessageId)
					ServerLogger.get().warn("Message " + nextMessageId + " was lost! Previously handled message: " + previousIncomingMessageId);

				handleMessage(m);
				++nextMessageId;
			}

			inbox.clear();
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
					processPendingInboxMessages();
				}
			} else if (receivedMessageId > previousIncomingMessageId) {
				// This can happen under following circumstances (amongst others):
				//      > a client message was not delivered to the server due to network issues, ending up getting lost
				//      > message order was disturbed due to server load balancing
				if (numOutOfOrderMessages++ <= MAX_OUT_OF_ORDER_MESSAGES) {
					long delta = receivedMessageId - previousIncomingMessageId;
					ServerLogger.get().trace("Received newer message " + receivedMessageId + " out-of-order. Delta: " + delta + " | Awaiting older message...");
					inbox.add(message);
				} else {
					ServerLogger.get().error("Received too many messages out of order! Resetting to the oldest pending message in inbox...")
							.error("Message " + previousIncomingMessageId + " is probably lost");
					numOutOfOrderMessages = 0;
					inbox.add(message);
					processPendingInboxMessages();
				}
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

		ServerMessageChannelImpl(ClientIdToken clientId) {
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
		public ClientIdToken clientId() {
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
		public synchronized void send(Message.Payload payload) {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			Message<?> message = new Message<>(payload);
			message.setClientId(clientId);
			message.setMessageId(nextOutgoingMessageId++);
			if (message.getPayload() == null)
				throw new IllegalArgumentException("Message for client " + clientId + " has no payload!");

			outbox.add(message);
		}

		@Override
		public synchronized void clearPendingMessages() {
			if (!channelOpen)
				throw new IllegalStateException("Broken message channel for client " + clientId);

			// keep acknowledgement messages
			outbox = outbox.stream().filter(e -> e.getPayload() instanceof SmClientMessageConsumed)
						.sorted(Comparator.comparingLong(Message::getMessageId)).collect(Collectors.toCollection(ArrayDeque::new));
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			
			ServerMessageChannelImpl that = (ServerMessageChannelImpl) o;
			
			return clientId.equals(that.clientId) && inbox.equals(that.inbox) &&
					messageHandlers.equals(that.messageHandlers) && outbox.equals(that.outbox) &&
					previousIncomingMessageId == that.previousIncomingMessageId &&
					nextOutgoingMessageId == that.nextOutgoingMessageId &&
					numOutOfOrderMessages == that.numOutOfOrderMessages &&
					channelOpen == that.channelOpen;
		}
		
		@Override
		public int hashCode() {
			int hash = 7;
			hash = 31 * hash + clientId.hashCode();
			return hash;
		}
	}

	private final Map<ClientIdToken, ServerMessageChannelImpl> clientId2Channel;
	private final Map<ServerMessageChannelImpl, ClientIdToken> channel2ClientId;

	private ServerMessagingSwitchboard() {
		clientId2Channel = new ConcurrentHashMap<>();
		channel2ClientId = new ConcurrentHashMap<>();
	}

	public synchronized ServerMessageChannel openChannel(ClientIdToken clientId) {
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

		ClientIdToken mapped = channel2ClientId.get(channelImpl);
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

	public ArrayList<Message<? extends Message.Payload>> onPullToClient(ClientIdToken clientId) {
		ServerMessageChannelImpl messageChannel = clientId2Channel.get(clientId);
		if (messageChannel == null) {
			ServerLogger.get().error("Received message from unknown client " + clientId);
			throw new InvalidClientIdentificationTokenException();
		}

		return messageChannel.onPullToClient(-1);
	}
}
