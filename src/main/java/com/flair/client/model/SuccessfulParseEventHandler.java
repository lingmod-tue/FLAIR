package com.flair.client.model;

import com.flair.shared.interop.dtos.RankableDocument;

interface SuccessfulParseEventHandler {
	void handle(ProcessData proc, RankableDocument d);
}
