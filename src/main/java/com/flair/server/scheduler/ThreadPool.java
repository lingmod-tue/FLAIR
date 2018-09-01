package com.flair.server.scheduler;

import com.flair.server.utilities.ServerLogger;
import org.threadly.concurrent.PriorityScheduler;
import org.threadly.concurrent.SchedulerService;
import org.threadly.concurrent.SubmitterExecutor;
import org.threadly.concurrent.wrapper.limiter.SchedulerServiceLimiter;
import org.threadly.concurrent.wrapper.traceability.ThreadRenamingSchedulerService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadPool {
	private static ThreadPool SINGLETON = null;

	public static ThreadPool get() {
		if (SINGLETON == null) {
			synchronized (ThreadPool.class) {
				if (SINGLETON == null)
					SINGLETON = new ThreadPool();
			}
		}

		return SINGLETON;
	}

	public static void dispose() {
		if (SINGLETON != null) {
			SINGLETON.shutdown();
			SINGLETON = null;
		}
	}

	private final PriorityScheduler primaryThreadPool;
	private final PriorityScheduler timeoutThreadPool;

	private ThreadPool() {
		this.primaryThreadPool = new PriorityScheduler(Constants.PRIMARY_THREAD_POOL_SIZE);
		this.timeoutThreadPool = new PriorityScheduler(Constants.TIMEOUTABLE_THREAD_POOL_SIZE);
	}

	private void shutdown() {
		try {
			primaryThreadPool.shutdown();
			if (!primaryThreadPool.awaitTermination(2 * 60 * 1000))
				primaryThreadPool.shutdownNow();
		} catch (InterruptedException ex) {
			ServerLogger.get().error(ex, "Couldn't shutdown primary thread pool. Exception: " + ex.toString());
		}

		try {
			timeoutThreadPool.shutdown();
			if (!timeoutThreadPool.awaitTermination(2 * 60 * 1000))
				timeoutThreadPool.shutdownNow();
		} catch (InterruptedException ex) {
			ServerLogger.get().error(ex, "Couldn't shutdown timeout thread pool. Exception: " + ex.toString());
		}
	}

	public Builder builder() {
		return new Builder();
	}

	public <R> R invokeAndWait(FutureTask<R> task, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		// a rather wasteful way of implementing timeouts for executing tasks
		// unfortunately, Java currently provides no way to assign a timeout to a task on submission
		timeoutThreadPool.submit(task);
		return task.get(timeout, unit);
	}


	private static final class ExecutorService implements AsyncExecutorService {
		final SubmitterExecutor wrapped;

		private ExecutorService(SubmitterExecutor wrapped) {
			this.wrapped = wrapped;
		}

		@Override
		public void submit(FutureTask<?> task) {
			wrapped.submit(task);
		}
	}

	public final class Builder {
		int poolSize = Constants.BASELINE_CONCURRENT_THREADS;
		String poolName = "Default Thread Pool";

		public Builder poolSize(int size) {
			poolSize = size;
			return this;
		}

		public Builder poolName(String name) {
			poolName = name;
			return this;
		}

		public AsyncExecutorService build() {
			SchedulerService executor = new SchedulerServiceLimiter(primaryThreadPool, poolSize);
			executor = new ThreadRenamingSchedulerService(executor, poolName, true);

			return new ExecutorService(executor);
		}
	}
}
