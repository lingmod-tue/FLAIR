package edu.cmu.ark;

import java.io.InputStream;

/**
 * Tiny wrapper class for loading bundled resource files
 */
public final class ResourceLoader {
	public static InputStream inputStream(String fileName) {
		return ResourceLoader.class.getResourceAsStream(fileName);
	}
	public static String path(String fileName) {
		return ResourceLoader.class.getResource(fileName).getFile();
	}
}
