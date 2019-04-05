package com.flair.server.sentencesel;

class Constants {
	// sentences that have fewer unique tokens than this will be discarded
	static final int SELECTOR_MIN_UNIQUE_TOKEN_COUNT = 5;
	// sentences that have more tokens than this will be discarded
	static final int SELECTOR_MAX_TOKEN_COUNT = 25;
}
