package com.flair.client.model;

import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.model.interfaces.WebRankerAnalysis;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableDocument;

import java.util.ArrayList;
import java.util.List;

abstract class ProcessData implements WebRankerAnalysis {
	final AbstractWebRankerCore.OperationType type;
	final Language lang;
	final List<RankableDocument> parsedDocs;
	List<String> keywords;
	boolean complete;        // flagged after completion
	boolean invalid;        // set if cancelled or if there weren't any usable results

	ProcessData(AbstractWebRankerCore.OperationType t, Language l) {
		type = t;
		lang = l;
		complete = false;
		parsedDocs = new ArrayList<>();
		keywords = new ArrayList<>();
		invalid = false;
	}

	void setKeywords(List<String> kw) {
		keywords = new ArrayList<>(kw);
	}

	@Override
	public AbstractWebRankerCore.OperationType getType() {
		return type;
	}

	@Override
	public Language getLanguage() {
		return lang;
	}

	@Override
	public List<RankableDocument> getParsedDocs() {
		return parsedDocs;
	}

	@Override
	public boolean inProgress() {
		return complete == false;
	}
}
