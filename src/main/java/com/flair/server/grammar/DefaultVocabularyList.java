
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

/**
 * Wrapper class for the default keywords for each language
 */
public class DefaultVocabularyList {
	private static final EnumMap<Language, List<String>> VOCAB_MAP = new EnumMap<>(Language.class);

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
			ServerLogger.get().error(e, "Couldn't read default vocabulary list for " + lang);
		}

		out = Collections.unmodifiableList(out);
		return out;
	}

	static {
		VOCAB_MAP.put(Language.ENGLISH, loadWordListFromDisk(Language.ENGLISH, DefaultVocabularyList.class.getResourceAsStream("awl-english.txt")));
	}

	public static List<String> get(Language lang) {
		List<String> out = VOCAB_MAP.get(lang);
		if (out == null)
			out = new ArrayList<>();

		return out;
	}
}
