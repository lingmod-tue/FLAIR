package com.flair.shared.utilities;

import java.util.ArrayList;
import java.util.List;

/*
 * Implements a basic event source entity with a backing store for multiple event handlers/sinks
 */
public final class GenericEventSource<E>
{
	public interface EventHandler<E> {
		public void handle(E event);
	}
	private final List<EventHandler<E>>				handlers;
	
	public GenericEventSource() {
		handlers = new ArrayList<>();
	}

	public void addHandler(EventHandler<E> handler) {
		handlers.add(handler);
	}
	
	public void raiseEvent(E event)
	{
		for (EventHandler<E> itr : handlers)
			itr.handle(event);
	}
}
