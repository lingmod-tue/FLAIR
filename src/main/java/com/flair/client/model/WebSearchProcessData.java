package com.flair.client.model;

import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.RankableWebSearchResult;

import java.util.ArrayList;
import java.util.List;

final class WebSearchProcessData extends ProcessData {
	final String query;
	final int numResults;
	final List<RankableWebSearchResult> searchResults;

	WebSearchProcessData(Language l, String q, int r) {
		super(AbstractWebRankerCore.OperationType.WEB_SEARCH, l);
		query = q;
		numResults = r;
		searchResults = new ArrayList<>();
	}


	@Override
	public String getName() {
		return "'" + query + "'";
	}
}
