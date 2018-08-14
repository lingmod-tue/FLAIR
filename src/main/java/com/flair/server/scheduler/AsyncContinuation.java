package com.flair.server.scheduler;

public interface AsyncContinuation<R> {
	void then(AsyncJob job, R result);
}
