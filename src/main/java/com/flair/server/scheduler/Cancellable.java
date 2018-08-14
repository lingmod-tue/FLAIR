package com.flair.server.scheduler;

public interface Cancellable {
	boolean isCancelled();
	void cancel();
}
