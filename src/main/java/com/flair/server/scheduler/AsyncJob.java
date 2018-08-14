package com.flair.server.scheduler;

import com.flair.server.utilities.ServerLogger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.FutureTask;

public final class AsyncJob implements Cancellable {
	public interface NoMoreTasks {
		void handle(AsyncJob job);
	}

	private final class ExecutingTask<R> {
		final FutureTask<R> wrapper;
		final AsyncTask<R> wrapped;
		final AsyncContinuation<R> continuation;
		boolean executing;

		synchronized boolean isExecuting() {
			return executing;
		}

		synchronized void setExecuting(boolean state) {
			if (executing == state)
				throw new IllegalStateException("Illegal task execution state");
			executing = state;
		}

		synchronized void cancel() {
			wrapper.cancel(false);
		}

		ExecutingTask(AsyncTask<R> wrapped, AsyncContinuation<R> continuation) {
			this.wrapped = wrapped;
			this.continuation = continuation;

			AsyncJob.this.registerTask(this);
			this.wrapper = new FutureTask<R>(() -> {
				R output = null;
				try {
					setExecuting(true);
					if (!AsyncJob.this.isCancelled()) {
						output = this.wrapped.run();
						if (!AsyncJob.this.isCancelled())
							this.continuation.then(AsyncJob.this, output);
					}
				} catch (Throwable ex) {
					ServerLogger.get().error(ex, "Uncaught exception in executing task: " + ex.toString());
				} finally {
					AsyncJob.this.deregisterTask(this);
					setExecuting(false);
				}

				return output;
			});
		}
	}

	private final List<ExecutingTask<?>> registeredTasks;
	private boolean cancelled;
	private final NoMoreTasks noMoreTasksHandler;

	private AsyncJob(NoMoreTasks noMoreTasksHandler) {
		this.registeredTasks = new ArrayList<>();
		this.cancelled = false;
		this.noMoreTasksHandler = noMoreTasksHandler;
	}

	public synchronized boolean isExecuting() {
		return !cancelled && !registeredTasks.isEmpty();
	}

	private synchronized <R> ExecutingTask<R> createTaskWrapper(AsyncTask<R> task, AsyncContinuation<R> continuation) {
		return new ExecutingTask<>(task, continuation);
	}

	private synchronized void registerTask(ExecutingTask<?> task) {
		if (cancelled)
			return;

		if (!registeredTasks.add(task))
			throw new RuntimeException("Couldn't register task " + task.toString());
	}

	private synchronized void deregisterTask(ExecutingTask<?> task) {
		if (!registeredTasks.remove(task))
			throw new RuntimeException("Couldn't deregister task " + task.toString());

		if (registeredTasks.isEmpty())
			noMoreTasksHandler.handle(this);
	}

	private synchronized ExecutingTask<?> poll() {
		if (registeredTasks.isEmpty())
			return null;
		else
			return registeredTasks.get(0);
	}

	public synchronized void cancel() {
		if (cancelled || registeredTasks.isEmpty())
			return;

		cancelled = true;
		List<ExecutingTask<?>> nonexec = new ArrayList<>();
		for (ExecutingTask<?> itr : registeredTasks) {
			itr.cancel();
			if (!itr.isExecuting())
				nonexec.add(itr);
		}

		// just remove those tasks that were never executed
		registeredTasks.removeAll(nonexec);
		noMoreTasksHandler.handle(this);
	}

	public synchronized boolean isCancelled() {
		return cancelled;
	}

	public void await() {
		while (isExecuting()) {
			ExecutingTask<?> first = poll();
			if (first != null) {
				try {
					first.wrapper.get();
				} catch (Throwable ex) {
					ServerLogger.get().error(ex, "Job encounted an exception while waiting. Exception: " + ex.toString());
				}
			}
		}
	}

	public static final class Scheduler {
		public final class Context<R> {
			final AsyncTask<R> task;
			AsyncExecutorService executor = null;
			AsyncContinuation<R> continuation = null;

			private Context(AsyncTask<R> task) {
				this.task = task;
			}

			private void execute() {
				ExecutingTask<R> wrappedTask = parent.createTaskWrapper(task, continuation);
				executor.submit(wrappedTask.wrapper);
			}

			public Context<R> with(AsyncExecutorService executor) {
				this.executor = executor;
				return this;
			}

			public Context<R> then(AsyncContinuation<R> continuation) {
				this.continuation = continuation;
				return this;
			}

			public Scheduler queue() {
				if (executor == null)
					throw new IllegalStateException("Invalid task executor service");

				if (continuation == null)
					continuation = (j, r) -> {};

				queuedTasks.add(this);
				return Scheduler.this;
			}
		}

		final AsyncJob parent;
		final Queue<Context<?>> queuedTasks = new ArrayDeque<>();
		boolean consumed = false;

		private Scheduler(AsyncJob parent) {
			this.parent = parent;
		}

		private Scheduler(AsyncJob.NoMoreTasks noMoreTasks) {
			this.parent = new AsyncJob(noMoreTasks);
		}

		public <R> Context<R> newTask(AsyncTask<R> task) {
			if (consumed)
				throw new IllegalStateException("Scheduler already consumed");

			return new Context<>(task);
		}

		public AsyncJob fire() {
			if (consumed)
				throw new IllegalStateException("Scheduler already consumed");
			else if (queuedTasks.isEmpty())
				throw new IllegalStateException("No new tasks scheduled");
			else if (parent.isCancelled())
				throw new IllegalStateException("Job was already cancelled");

			consumed = true;
			for (Context<?> queuedTask = queuedTasks.poll(); queuedTask != null; queuedTask = queuedTasks.poll())
				queuedTask.execute();

			return parent;
		}

		public boolean hasTasks() {
			return !queuedTasks.isEmpty();
		}


		public static Scheduler newJob(AsyncJob.NoMoreTasks noMoreTasks) {
			return new Scheduler(noMoreTasks);
		}

		public static Scheduler existingJob(AsyncJob job) {
			return new Scheduler(job);
		}
	}
}
