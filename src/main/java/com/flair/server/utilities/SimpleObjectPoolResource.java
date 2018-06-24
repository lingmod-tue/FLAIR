/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.utilities;

/**
 * Represents a resource stored in a SimpleObjectPool that has been acquired for use
 * @author shadeMe
 */
public interface SimpleObjectPoolResource<T extends Object> extends AutoCloseable
{
    public T		get();
}
