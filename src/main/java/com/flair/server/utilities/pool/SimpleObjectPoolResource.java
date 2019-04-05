
package com.flair.server.utilities.pool;

/**
 * Represents a resource stored in a SimpleObjectPool that has been acquired for use
 */
public interface SimpleObjectPoolResource<T extends Object> extends AutoCloseable {
	public T get();
}
