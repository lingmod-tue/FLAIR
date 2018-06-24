/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.resources;

import java.io.InputStream;

/**
 * Tiny wrapper class for loading bundled resource files
 * 
 * @author shadeMe
 */
public final class ResourceLoader
{
	public static InputStream get(String fileName) {
		return ResourceLoader.class.getResourceAsStream("/com/flair/server/resources/" + fileName);
	}
}
