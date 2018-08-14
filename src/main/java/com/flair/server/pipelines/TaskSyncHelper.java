package com.flair.server.pipelines;

import com.flair.server.scheduler.AsyncJob;

import java.util.HashMap;
import java.util.Map;

public class TaskSyncHelper {
	public interface SyncHandler<R> {
		void handle(AsyncJob job, R taskResult);
	}

	private final Map<Class<?>, SyncHandler<?>> taskLinker = new HashMap<>();

	public <R> void addHandler(Class<R> taskResultClass, SyncHandler<R> handler) {
		if (taskLinker.put(taskResultClass, handler) != null)
			throw new IllegalArgumentException("Handler exists for class " + taskResultClass.getSimpleName());
	}

	public <R> void syncTask(AsyncJob jerb, R taskResult) {
		SyncHandler<R> handler = (SyncHandler<R>) taskLinker.get(taskResult.getClass());

		if (handler == null)
			throw new IllegalArgumentException("No handler for class " + taskResult.getClass().getSimpleName());
		else
			handler.handle(jerb, taskResult);
	}
}
