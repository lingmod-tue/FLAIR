package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.List;

class DocumentSentenceSelectorParams {
	public Language lang;
	public boolean stemWords;
	public boolean ignoreStopwords;
	public List<AbstractDocument> docs;

	DocumentSentenceSelectorParams(Language l) {
		lang = l;
		stemWords = ignoreStopwords = false;
		docs = new ArrayList<>();
	}
}
