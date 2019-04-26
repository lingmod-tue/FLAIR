package com.flair.client.model;

import com.google.gwt.core.client.Duration;
import com.google.gwt.user.client.Timer;

final class WebSearchCooldownTimer {
	private static final int COOLDOWN_MS = 5 * 60 * 1000;
	private static final int MAX_QUERIES = 15;                // no of allowed queries before the cooldown is triggered

	private final Timer cooldownTimer;
	private int elapsedQueries;
	private Duration lastResetTimestamp;

	public WebSearchCooldownTimer() {
		cooldownTimer = new Timer() {
			@Override
			public void run() {
				// reset elapsed queries
				elapsedQueries = 0;
				lastResetTimestamp = new Duration();
			}
		};

		elapsedQueries = 0;
		lastResetTimestamp = new Duration();
	}

	boolean tryBeginOperation() {
		if (elapsedQueries >= MAX_QUERIES)
			return false;

		elapsedQueries++;
		return true;
	}

	void start() {
		cooldownTimer.scheduleRepeating(COOLDOWN_MS);
	}

	void stop() {
		cooldownTimer.cancel();
	}

	long getNextResetTime() {
		long delta = COOLDOWN_MS - lastResetTimestamp.elapsedMillis();
		return delta > 0 ? delta : 0;
	}
}
