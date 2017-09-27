package com.flair.client.utilities;

import java.util.ArrayDeque;
import java.util.Queue;

import gwt.material.design.client.ui.animate.MaterialAnimation;

/*
 * Queues actions that are triggered by animations
 */
public class AnimationEventQueue
{
	public interface AnimStartHandler {
		public void handle();
	}
	
	public interface AnimEndHandler {
		public void handle();
	}
	
	
	final class Event
	{
		final MaterialAnimation		anim;
		final AnimStartHandler		beginHandler;		// executed right before the event begins
		final AnimEndHandler		endHandler;			// executed right after the event ends
		
		Event(MaterialAnimation a, AnimStartHandler b, AnimEndHandler e)
		{
			anim = a;
			beginHandler = b;
			endHandler = e;
		}
		
		void runBegin()
		{
			if (beginHandler != null)
				beginHandler.handle();
		}
		
		void runEnd()
		{
			if (endHandler != null)
				endHandler.handle();
		}
		
		void runAnim()
		{
			if (animating)
				throw new RuntimeException("Previous animation not complete");

			animating = true;
			runBegin();
			anim.animate(() -> {
				runEnd();
				animating = false;
				runNextEvent();
			});
		}
	}
	
	private final Queue<Event>		events;
	private boolean					animating;
	
	public AnimationEventQueue()
	{
		events = new ArrayDeque<>();
		animating = false;
	}
	
	private void runNextEvent()
	{
		// skip if already animating
		if (animating)
			return;
		else if (events.isEmpty())
			return;
		
		Event first = events.poll();
		first.runAnim();
	}

	public void enqueue(MaterialAnimation anim, AnimStartHandler begin, AnimEndHandler end)
	{
		if (anim.isInfinite())
			throw new RuntimeException("Infinite animation enqueued");
		
		Event newEvent = new Event(anim, begin, end);
		events.add(newEvent);
		runNextEvent();
	}
	
	public boolean isEmpty() {
		return events.isEmpty();
	}
	
	public boolean isAnimating() {
		return animating;
	}
}
