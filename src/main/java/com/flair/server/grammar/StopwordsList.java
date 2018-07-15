package com.flair.server.grammar;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

/*
 * Stopwords for different languages
 */
public class StopwordsList {
	private static final EnumMap<Language, List<String>> STOPWORD_MAP = new EnumMap<>(Language.class);

	private static List<String> loadWordListFromDisk(Language lang, InputStream input) {
		List<String> out = new ArrayList<>();
		try (BufferedReader buf = new BufferedReader(new InputStreamReader(input))) {
			String line;
			while ((line = buf.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty() && !out.contains(line))
					out.add(line);
			}
		} catch (IOException e) {
			ServerLogger.get().error(e, "Couldn't read stopwords list for " + lang);
		}

		out = Collections.unmodifiableList(out);
		return out;
	}

	static {
		STOPWORD_MAP.put(Language.ENGLISH, loadWordListFromDisk(Language.ENGLISH, StopwordsList.class.getResourceAsStream("stopwords-en.txt")));
	}

	public static List<String> get(Language lang) {
		List<String> out = STOPWORD_MAP.get(lang);
		if (out == null)
			out = new ArrayList<>();

		return out;
	}
}
