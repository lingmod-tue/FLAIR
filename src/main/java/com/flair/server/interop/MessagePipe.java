package com.flair.server.interop;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.interop.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/*
 * Endpoint for message handling requests
 */
public class MessagePipe {
	private static MessagePipe SINGLETON = null;

	public static MessagePipe get() {
		if (SINGLETON == null) {
			synchronized (MessagePipe.class) {
				if (SINGLETON == null)
					SINGLETON = new MessagePipe();
			}
		}

		return SINGLETON;
	}

	private final MessagePipelineType type;
	private final Map<ServerAuthenticationToken, PullMessageSender> token2Pull;
	private final Map<PullMessageSender, ServerAuthenticationToken> pull2Token;

	/*
	 * Pull implementation of a message sender
	 */
	class PullMessageSender implements AbstractMesageSender, PullMessageQueue {
		private final Queue<ServerMessage> messageQueue;
		private boolean registered;

		public PullMessageSender() {
			messageQueue = new ArrayDeque<>();
			registered = false;
		}

		private synchronized void doEnqueue(ServerMessage msg) {
			messageQueue.add(msg);
		}

		private synchronized ServerMessage[] doDequeue(int count) {
			if (count <= 0)
				count = messageQueue.size();
			else if (count > messageQueue.size())
				count = messageQueue.size();

			ServerMessage[] out = new ServerMessage[count];
			for (int i = 0; i < count; i++)
				out[i] = messageQueue.poll();

			return out;
		}

		private synchronized int doSize() {
			return messageQueue.size();
		}

		private synchronized void doOpen(ServerAuthenticationToken token) {
			registerPullMessageSender(token, this);
			registered = true;
		}

		private synchronized void doClose() {
			if (messageQueue.isEmpty() == false) {
				ServerLogger.get().warn("PullMessageSender has pending messages at the time of shutdown. Message count: "
						+ messageQueue.size());
			}

			messageQueue.clear();

			deregisterPullMessageSender(this);
		}

		private synchronized void doClear() {
			messageQueue.clear();
		}

		private synchronized boolean doIsOpen() {
			return registered;
		}

		@Override
		public MessagePipelineType getType() {
			return MessagePipelineType.PULL;
		}

		@Override
		public void send(ServerMessage msg) {
			if (registered == false)
				throw new IllegalStateException("Sender not open");

			doEnqueue(msg);
		}

		@Override
		public int getMessageCount() {
			return doSize();
		}

		@Override
		public ServerMessage[] dequeue(int count) {
			return doDequeue(count);
		}

		@Override
		public ServerMessage[] dequeueAll() {
			return doDequeue(-1);
		}

		@Override
		public void open(AuthToken receiverToken) {
			doOpen((ServerAuthenticationToken) receiverToken);
		}

		@Override
		public void close() {
			doClose();
		}

		@Override
		public boolean isOpen() {
			return doIsOpen();
		}

		@Override
		public void clearPendingMessages() {
			doClear();
		}
	}


	private MessagePipe() {
		type = MessagePipelineType.PULL;
		token2Pull = new HashMap<>();
		pull2Token = new HashMap<>();
	}

	private synchronized void registerPullMessageSender(ServerAuthenticationToken token, PullMessageSender sender) {
		if (token2Pull.containsKey(token))
			throw new IllegalArgumentException("Multiple senders for session token " + token.getUuid());

		token2Pull.put(token, sender);
		pull2Token.put(sender, token);
	}

	private synchronized void deregisterPullMessageSender(PullMessageSender sender) {
		if (pull2Token.containsKey(sender) == false)
			throw new IllegalArgumentException("Sender not registered");

		ServerAuthenticationToken token = pull2Token.get(sender);

		token2Pull.remove(sender);
		pull2Token.remove(token);
	}

	public synchronized AbstractMesageSender createSender() {
		switch (type) {
		case PULL:
			return new PullMessageSender();
		case PUSH:
			throw new IllegalArgumentException("Sender type " + type + " not implemented");
		default:
			return null;
		}
	}

	public synchronized ServerMessage[] getQueuedMessages(ServerAuthenticationToken token) {
		PullMessageSender sender = token2Pull.get(token);
		if (sender == null)
			throw new IllegalArgumentException("Session token has no message sender. ID: " + token.getUuid());

		// return all the queued messages
		return sender.dequeueAll();
	}
}
