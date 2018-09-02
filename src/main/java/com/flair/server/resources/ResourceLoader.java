
package com.flair.server.resources;

import java.io.InputStream;

/**
 * Tiny wrapper class for loading bundled resource files
 */
public final class ResourceLoader {
	public static InputStream get(String fileName) {
		return ResourceLoader.class.getResourceAsStream(fileName);
	}
}
