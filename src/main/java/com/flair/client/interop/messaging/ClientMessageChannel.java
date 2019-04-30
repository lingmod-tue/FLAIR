package com.flair.client.interop.messaging;

import com.flair.client.ClientEndPoint;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdToken;
import com.flair.shared.interop.InteropServiceAsync;
import com.flair.shared.interop.messaging.Message;
import com.flair.shared.interop.messaging.MessageReceivedHandler;
import com.flair.shared.interop.messaging.server.SmClientMessageConsumed;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

public class ClientMessageChannel {
	private final class MessagePollerImpl implements MessagePoller {
		private final int interval;
		private final int timeout;
		private final Runnable timeoutHandler;
		private final Map<Class<?>, MessageReceivedHandler<?>> messageHandlers;
		private final Queue<Message<?>> inbox;
		private boolean running;
		private boolean disposed;
		private boolean timedOut;
		private int remainingTicks;
		private int elapsedTicks;

		private MessagePollerImpl(int interval, int timeout,
		                          Runnable timeoutHandler, Map<Class<?>, MessageReceivedHandler<?>> messageHandlers) {
			this.interval = interval;
			this.timeout = timeout;
			this.timeoutHandler = timeoutHandler;
			this.messageHandlers = new HashMap<>(messageHandlers);
			this.inbox = new ArrayDeque<>();
			this.running = false;
			this.timedOut = false;
			this.disposed = false;
			this.remainingTicks = 0;
			this.elapsedTicks = 0;

			activeMessagePollers.add(this);
		}

		private <P extends Message.Payload> void tick(int elapsedTicks, List<Message<? extends Message.Payload>> newMessages) {
			if (disposed)
				throw new RuntimeException("Message poller has been disposed");
			else if (timedOut || !running)
				return;

			this.remainingTicks -= elapsedTicks;
			this.elapsedTicks += elapsedTicks;

			inbox.addAll(newMessages);

			if (this.elapsedTicks >= timeout && inbox.isEmpty()) {
				timedOut = true;
				running = false;

				try {
					timeoutHandler.run();
				} catch (Throwable ex) {
					ClientLogger.get().error(ex, "Message timeout handler raised an exception! Exception: " + ex);
				}
			} else if (this.remainingTicks <= 0) {
				this.remainingTicks = interval;
				this.elapsedTicks = 0;

				for (Message<?> msg : inbox) {
					P payload = (P) msg.getPayload();
					MessageReceivedHandler<P> handler = (MessageReceivedHandler<P>) messageHandlers.get(payload.getClass());
					if (handler != null) {
						try {
							handler.read(payload);
						} catch (Throwable ex) {
							ClientLogger.get().error(ex, "Message handler raised an exception! Exception: " + ex);
						}
					}
				}

				inbox.clear();
			}
		}

		@Override
		public void start() {
			if (disposed)
				throw new RuntimeException("Message poller has been disposed");
			else if (running)
				throw new RuntimeException("Message poller already running");

			running = true;
			timedOut = false;
			remainingTicks = interval;
			elapsedTicks = 0;
			inbox.clear();

			updateTimerState();
		}
		@Override
		public void stop() {
			if (disposed)
				throw new RuntimeException("Message poller has been disposed");
			else if (!running)
				throw new RuntimeException("Message poller is not running");

			running = false;
			timedOut = false;
			remainingTicks = interval;
			elapsedTicks = 0;
			inbox.clear();

			updateTimerState();
		}
		@Override
		public void dispose() {
			if (disposed)
				throw new RuntimeException("Message poller has already been disposed");
			else if (running)
				throw new RuntimeException("Message poller must be stopped before disposal");

			disposed = true;
			activeMessagePollers.remove(this);

			updateTimerState();
		}
		@Override
		public boolean isRunning() {
			return running;
		}
		@Override
		public boolean isTimedout() {
			return timedOut;
		}
	}

	private final class MessagePollerImplBuilder implements MessagePoller.Builder {
		private int interval = 0;
		private int timeout = 0;
		private Runnable timeoutHandler = null;
		private Map<Class<?>, MessageReceivedHandler<?>> messageHandlers = new HashMap<>();

		@Override
		public MessagePoller.Builder interval(int interval) {
			this.interval = interval;
			return this;
		}
		@Override
		public MessagePoller.Builder timeout(int timeout) {
			this.timeout = timeout;
			return this;
		}
		@Override
		public MessagePoller.Builder onTimeout(Runnable handler) {
			this.timeoutHandler = handler;
			return this;
		}
		@Override
		public <P extends Message.Payload> MessagePoller.Builder onMessage(Class<P> payloadClass, MessageReceivedHandler<P> handler) {
			if (payloadClass == SmClientMessageConsumed.class)
				throw new RuntimeException("Cannot handle SmClientMessageConsumed messages");

			if (this.messageHandlers.put(payloadClass, handler) != null)
				throw new RuntimeException("Handler exists for class " + payloadClass.getSimpleName());
			return this;
		}
		@Override
		public MessagePoller build() {
			if (interval <= 0)
				throw new RuntimeException("Invalid value " + interval + " for interval");
			else if (interval <= POLLING_TIMER_INTERVAL)
				throw new RuntimeException("Interval must be > " + POLLING_TIMER_INTERVAL);
			else if (timeout <= 0)
				throw new RuntimeException("Invalid value " + timeout + " for timeout");
			else if (timeoutHandler == null)
				throw new RuntimeException("No timeout handler set");
			else if (messageHandlers.isEmpty())
				throw new RuntimeException("No message handlers registered");

			return new MessagePollerImpl(interval, timeout, timeoutHandler, messageHandlers);
		}
	}

	private static final class ClientMessageCallbackData {
		private final Message<? extends Message.Payload> message;
		private final SuccessHandler successHandler;
		private final FailureHandler failureHandler;

		private ClientMessageCallbackData(Message<? extends Message.Payload> message,
		                                  SuccessHandler successHandler, FailureHandler failureHandler) {
			this.message = message;
			this.successHandler = successHandler;
			this.failureHandler = failureHandler;
		}
	}

	private static final int POLLING_TIMER_INTERVAL = 1;      // in seconds


	private final ClientIdToken clientId;
	private final InteropServiceAsync interopService;
	private final Timer timer;
	private final Map<Long, ClientMessageCallbackData> pendingMessageCallbacks;
	private final Set<MessagePollerImpl> activeMessagePollers;
	private long previousIncomingMessageId;
	private long nextOutgoingMessageId;
	private boolean pollingServer;

	public ClientMessageChannel(ClientIdToken clientId) {
		this.clientId = clientId;
		this.interopService = InteropServiceAsync.Util.getInstance();
		this.timer = new Timer() {
			@Override
			public void run() {
				onPollTimerTick();
			}
		};
		this.pendingMessageCallbacks = new HashMap<>();
		this.activeMessagePollers = new HashSet<>();
		this.previousIncomingMessageId = Message.INVALID_ID;
		this.nextOutgoingMessageId = Message.INVALID_ID + 1;
		this.pollingServer = false;
	}

	private void startTimer() {
		if (!timer.isRunning()) {
			timer.scheduleRepeating(POLLING_TIMER_INTERVAL * 1000);
			ClientLogger.get().trace("Message channel poll timer has started");
		}
	}

	private void stopTimer() {
		if (timer.isRunning()) {
			timer.cancel();
			ClientLogger.get().trace("Message channel poll timer has stopped");
		}
	}

	private void updateTimerState() {
		if (pendingMessageCallbacks.isEmpty() && activeMessagePollers.isEmpty())
			stopTimer();
		else {
			long numRunningPollers = activeMessagePollers.stream().filter(MessagePollerImpl::isRunning).count();
			if (numRunningPollers > 0 || !pendingMessageCallbacks.isEmpty())
				startTimer();
			else
				stopTimer();
		}
	}

	private void onPollTimerTick() {
		// prevent the server from being queried a second time whilst a previous RPC call is in progress
		if (pollingServer)
			return;

		pollingServer = true;
		AsyncCallback<ArrayList<Message<? extends Message.Payload>>> callback = new AsyncCallback<ArrayList<Message<? extends Message.Payload>>>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientLogger.get().error(caught, "Message retrieval failed with the following exception: " + caught);
				updateTimerState();

				pollingServer = false;

				if (caught instanceof InvalidClientIdentificationTokenException)
					ClientEndPoint.get().fatalServerError();
			}
			@Override
			public void onSuccess(ArrayList<Message<? extends Message.Payload>> result) {
				processIncomingServerMessages(result);
				updateTimerState();

				pollingServer = false;
			}
		};
		interopService.MessagingReceive(clientId, callback);
	}

	private void processIncomingServerMessages(List<Message<? extends Message.Payload>> messages) {
		if (!messages.isEmpty())
			ClientLogger.get().trace("Pulled " + messages.size() + " messages from the server");

		List<Message<? extends Message.Payload>> filteredMessages = new ArrayList<>();

		// consume special messages from the server
		for (Message<? extends Message.Payload> message : messages) {
			if (message.getMessageId() != previousIncomingMessageId + 1) {
				throw new RuntimeException("Server message was received out-of-order! Expected "
						+ previousIncomingMessageId + 1 + ", received " + message.getMessageId());
			}
			previousIncomingMessageId = message.getMessageId();

			Message.Payload payload = message.getPayload();
			if (payload instanceof SmClientMessageConsumed) {
				SmClientMessageConsumed ackMsg = (SmClientMessageConsumed) payload;
				ClientMessageCallbackData callbackData = pendingMessageCallbacks.get(ackMsg.getClientMessageId());
				if (callbackData == null)
					throw new RuntimeException("Unexpected client message acknowledgement received from server! Message: " + message);

				if (ackMsg.getSuccess())
					callbackData.successHandler.onSuccess();
				else
					callbackData.failureHandler.onFailure(ackMsg.getError().getException(), ackMsg.getError().getMessage());

				pendingMessageCallbacks.remove(ackMsg.getClientMessageId());
			} else {
				ClientLogger.get().info("New Message: " + message);
				filteredMessages.add(message);
			}
		}

		// pass the filtered messages to active pollers
		// performed on a copy as the iterator can get invalidate if a poller was disposed inside a callback
		for (MessagePollerImpl poller : new HashSet<>(activeMessagePollers))
			poller.tick(POLLING_TIMER_INTERVAL, filteredMessages);
	}

	public void send(Message.Payload payload, SuccessHandler successHandler, FailureHandler failureHandler) {
		Message<?> message = new Message<>(payload);
		message.setClientId(clientId);
		message.setMessageId(nextOutgoingMessageId++);
		if (message.getPayload() == null)
			throw new RuntimeException("Message has no payload!");

		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientLogger.get().error(caught, "Message sending failed with the following exception: " + caught);
				ClientLogger.get().error("Message: " + message);

				failureHandler.onFailure(caught, "");

				// ### TODO handle IncompatibleRemoteServiceException && InvocationException more elegantly
			}
			@Override
			public void onSuccess(Void result) {
				// wait for a message from the server acknowledging the consumption of sent message
				pendingMessageCallbacks.put(message.getMessageId(),
						new ClientMessageCallbackData(message, successHandler, failureHandler));
				updateTimerState();
			}
		};
		interopService.MessagingSend(message, callback);
	}

	public MessagePoller.Builder messagePoller() {
		return new MessagePollerImplBuilder();
	}
}
