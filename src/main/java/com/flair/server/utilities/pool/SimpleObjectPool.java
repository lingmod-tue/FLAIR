
package com.flair.server.utilities.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

/**
 * A basic implementation of a blocking object pool
 */
public class SimpleObjectPool<T> {
	public interface Factory<T> {
		T create();
	}

	private final class ResourceUsageData {
		private boolean inUse;

		public ResourceUsageData() {
			this.inUse = false;
		}

		public boolean isFree() {
			return inUse == false;
		}

		public void acquire() {
			if (inUse)
				throw new IllegalStateException("Resource is already in use");

			inUse = true;
		}

		public void release() {
			if (inUse == false)
				throw new IllegalStateException("Resource is not in use");

			inUse = false;
		}
	}

	private final class AcquiredResource implements SimpleObjectPoolResource<T> {
		private final T resource;
		private boolean valid;

		public AcquiredResource(T resource) {
			if (resource == null)
				throw new IllegalArgumentException("Invalid resource object");

			this.resource = resource;
			this.valid = true;
		}

		@Override
		public T get() {
			if (valid == false)
				throw new IllegalStateException("Resource already released");

			return resource;
		}

		@Override
		public void close() throws Exception {
			put(resource);
			valid = false;
		}

	}

	private final Semaphore synchronizer;
	private final Map<T, ResourceUsageData> resourceTable;

	public SimpleObjectPool(int poolSize, Factory<T> factory) {
		this.synchronizer = new Semaphore(poolSize, true);
		this.resourceTable = new HashMap<>();

		for (int i = 0; i < poolSize; i++)
			resourceTable.put(factory.create(), new ResourceUsageData());
	}

	public SimpleObjectPool(int poolSize, T[] resources) {
		this.synchronizer = new Semaphore(poolSize, true);
		this.resourceTable = new HashMap<>();

		if (resources.length != poolSize)
			throw new IllegalArgumentException("Initial resource count not equal to pool size. Expected " + poolSize
					+ ", received " + resources.length);

		for (int i = 0; i < poolSize; i++)
			resourceTable.put(resources[i], new ResourceUsageData());
	}

	private synchronized T lend() {
		for (Entry<T, ResourceUsageData> itr : resourceTable.entrySet()) {
			if (itr.getValue().isFree()) {
				itr.getValue().acquire();
				return itr.getKey();
			}
		}

		return null;
	}

	private synchronized void reclaim(T resource) {
		if (resourceTable.containsKey(resource) == false)
			throw new IllegalArgumentException("Resource does not belong to the pool");

		resourceTable.get(resource).release();
	}

	private void put(T resource) {
		reclaim(resource);
		synchronizer.release();
	}

	public final SimpleObjectPoolResource<T> get() throws InterruptedException {
		synchronizer.acquire();
		return new AcquiredResource(lend());
	}
}
