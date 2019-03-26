package com.flair.client.localization.resources;

import com.google.gwt.resources.client.TextResource;

/*
 * Loads a string table into localization providers
 *
 * String table format: <provider-name>\t<tag>\t<localized-string>
 * Lines beginning with # are ignored
 */
public class StringTableReader {
	public static interface ReadLineHandler {
		public void readLine(String providerName, String tag, String localizedStr);
	}

	public void parse(TextResource stringTable, ReadLineHandler handler) {
		String input = stringTable.getText();
		String[] splits = input.split("\n");
		int lineno = 0;
		for (String line : splits) {
			lineno++;
			line = line.trim();
			if (line.isEmpty())
				continue;
			else if (line.charAt(0) == '#')
				continue;        // comment line

			String[] tabs = line.split("\t");
			if (tabs.length != 3)
				throw new RuntimeException("Invalid entry @ line " + lineno + " in string table '" + stringTable.getName() + "'");

			handler.readLine(tabs[0], tabs[1], tabs[2]);
		}
	}
}
