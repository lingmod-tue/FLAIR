package com.flair.client.presentation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

/*
 * Manages animation event queues for widgets, allowing sequential execution of (animation-)deferred actions
 */
public class GlobalWidgetAnimator
{
	private static final GlobalWidgetAnimator		INSTANCE = new GlobalWidgetAnimator();
	
	public static GlobalWidgetAnimator get() {
		return INSTANCE;
	}
	
	private static final int						TIMEOUT_CLEANUP_MS = 60 * 1000;
	
	private Map<Widget, AnimationEventQueue>		animatingWidgets;
	private final Timer								cleanupTimer;
	
	private GlobalWidgetAnimator()
	{
		animatingWidgets = new HashMap<>();
		cleanupTimer = new Timer() {
			@Override
			public void run()
			{
				// filter out the entires that have empty queues
				animatingWidgets = animatingWidgets.entrySet().stream()
								.filter(e -> e.getValue().isEmpty() == false && e.getValue().isAnimating() == false)
								.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
			}
		};
		
		cleanupTimer.scheduleRepeating(TIMEOUT_CLEANUP_MS);
	}
	
	private AnimationEventQueue getQueue(Widget w)
	{
		AnimationEventQueue q = animatingWidgets.get(w);
		if (q == null)
		{
			q = new AnimationEventQueue();
			animatingWidgets.put(w, q);
		}
		
		return q;
	}

	public void animateWithStartStop(Widget w,
						Transition transition,
						int delay, int duration,
						AnimationEventQueue.AnimStartHandler begin, AnimationEventQueue.AnimEndHandler end)
	{
		AnimationEventQueue q = getQueue(w);
		MaterialAnimation anim = new MaterialAnimation(w);
		anim.transition(transition).delay(delay).duration(duration);
		q.enqueue(anim, begin, end);
	}
	
	public void animateWithStart(Widget w,
						Transition transition,
						int delay, int duration,
						AnimationEventQueue.AnimStartHandler begin)
	{
		animateWithStartStop(w, transition, delay, duration, begin, null);
	}
	
	public void animateWithStop(Widget w,
						Transition transition,
						int delay, int duration,
						AnimationEventQueue.AnimEndHandler end)
	{
		animateWithStartStop(w, transition, delay, duration, null, end);
	}
}
