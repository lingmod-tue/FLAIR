package com.flair.client.model;

import com.flair.shared.interop.RankableDocument;

interface SuccessfulParseEventHandler {
	void handle(ProcessData proc, RankableDocument d);
}
