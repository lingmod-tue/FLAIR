package com.flair.server.scheduler;

import java.util.concurrent.FutureTask;

public interface AsyncExecutorService {
	void submit(FutureTask<?> task);
}
