package com.flair.client.utilities;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

/*
 * Manages animation event queues for widgets, allowing sequential execution of (animation-)deferred actions
 */
public class GlobalWidgetAnimator {
	public interface AnimStartHandler {
		void handle();
	}

	public interface AnimEndHandler {
		void handle();
	}

	// Queues actions that are triggered by animations
	static class AnimationEventQueue {
		final class AnimEvent {
			final MaterialAnimation anim;
			final AnimStartHandler beginHandler;        // executed right before the event begins
			final AnimEndHandler endHandler;            // executed right after the event ends

			AnimEvent(MaterialAnimation a, AnimStartHandler b, AnimEndHandler e) {
				anim = a;
				beginHandler = b;
				endHandler = e;
			}

			void runBegin() {
				if (invokingHandler)
					throw new RuntimeException("Nested anim handler invocation");

				invokingHandler = true;
				if (beginHandler != null)
					beginHandler.handle();
				invokingHandler = false;
			}

			void runEnd() {
				if (invokingHandler)
					throw new RuntimeException("Nested anim handler invocation");

				invokingHandler = true;
				if (endHandler != null)
					endHandler.handle();
				invokingHandler = false;
			}

			void runAnim() {
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

		private final Queue<AnimEvent> queue;
		private final Queue<AnimEvent> buffer;
		private final boolean sequential;
		private boolean animating;
		private boolean invokingHandler;

		AnimationEventQueue(boolean sequential) {
			queue = new ArrayDeque<>();
			buffer = new ArrayDeque<>();
			this.sequential = sequential;
			this.animating = false;
			this.invokingHandler = false;
		}

		void enqueue(MaterialAnimation anim, AnimStartHandler begin, AnimEndHandler end) {
			if (anim.isInfinite())
				throw new RuntimeException("Infinite animation enqueued");

			AnimEvent newAnimEvent = new AnimEvent(anim, begin, end);
			if (animating && sequential) {
				if (invokingHandler) {
					// add directly to the main queue to ensure sequential playback
					queue.add(newAnimEvent);
				} else
					buffer.add(newAnimEvent);
			} else
				queue.add(newAnimEvent);

			runNextEvent();
		}

		void runNextEvent() {
			// skip if already animating
			if (animating)
				return;

			if (queue.isEmpty()) {
				if (!buffer.isEmpty()) {
					queue.addAll(buffer);
					buffer.clear();
				}
			}

			if (queue.isEmpty())
				return;

			AnimEvent first = queue.poll();
			first.runAnim();
		}

		boolean isEmpty() {
			return queue.isEmpty() && buffer.isEmpty();
		}

		boolean isAnimating() {
			return animating;
		}
	}

	private static final GlobalWidgetAnimator INSTANCE = new GlobalWidgetAnimator();

	public static GlobalWidgetAnimator get() {
		return INSTANCE;
	}

	private static final int TIMEOUT_CLEANUP_MS = 60 * 1000;

	private Map<Widget, AnimationEventQueue> animatingWidgets;
	private AnimationEventQueue commonAnimQueue;

	private boolean cleanupNonSequentialQueues() {
		// filter out the entires that have empty queues
		animatingWidgets = animatingWidgets.entrySet().stream()
				.filter(e -> !e.getValue().isEmpty() && !e.getValue().isAnimating())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return true;
	}

	private GlobalWidgetAnimator() {
		animatingWidgets = new HashMap<>();
		commonAnimQueue = new AnimationEventQueue(true);

		Scheduler.get().scheduleFixedPeriod(this::cleanupNonSequentialQueues, TIMEOUT_CLEANUP_MS);
	}

	private AnimationEventQueue getQueue(Widget w) {
		AnimationEventQueue q = animatingWidgets.get(w);
		if (q == null) {
			q = new AnimationEventQueue(false);
			animatingWidgets.put(w, q);
		}

		return q;
	}

	private void animateWithStartStop(AnimationEventQueue q,
	                                  Widget w,
	                                  Transition transition,
	                                  int delay, int duration,
	                                  AnimStartHandler begin,
	                                  AnimEndHandler end) {
		MaterialAnimation anim = new MaterialAnimation(w);
		anim.transition(transition).delay(delay).duration(duration);
		q.enqueue(anim, begin, end);
	}

	public void animateWithStartStop(Widget w,
	                                 Transition transition,
	                                 int delay, int duration,
	                                 AnimStartHandler begin, AnimEndHandler end) {
		animateWithStartStop(getQueue(w), w, transition, delay, duration, begin, end);
	}

	public void animateWithStart(Widget w,
	                             Transition transition,
	                             int delay, int duration,
	                             AnimStartHandler begin) {
		animateWithStartStop(w, transition, delay, duration, begin, null);
	}

	public void animateWithStop(Widget w,
	                            Transition transition,
	                            int delay, int duration,
	                            AnimEndHandler end) {
		animateWithStartStop(w, transition, delay, duration, null, end);
	}

	public void seqAnimateWithStartStop(Widget w,
	                                    Transition transition,
	                                    int delay, int duration,
	                                    AnimStartHandler begin, AnimEndHandler end) {
		animateWithStartStop(commonAnimQueue, w, transition, delay, duration, begin, end);
	}

	public void seqAnimateWithStart(Widget w,
	                                Transition transition,
	                                int delay, int duration,
	                                AnimStartHandler begin) {
		animateWithStartStop(commonAnimQueue, w, transition, delay, duration, begin, null);
	}

	public void seqAnimateWithStop(Widget w,
	                               Transition transition,
	                               int delay, int duration,
	                               AnimEndHandler end) {
		animateWithStartStop(commonAnimQueue, w, transition, delay, duration, null, end);
	}

}
