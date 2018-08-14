package com.flair.server.pipelines;

import com.flair.server.scheduler.Cancellable;

public interface PipelineOp extends Cancellable {
	interface EventHandler<E> {
		void handle(E event);
	}

	boolean isExecuting();
	void await();
	String name();
}
