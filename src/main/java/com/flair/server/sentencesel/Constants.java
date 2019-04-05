package com.flair.server.sentencesel;

class Constants {
	// sentences that have fewer unique terms than this will be discarded
	static final int SELECTOR_MIN_UNIQUE_TOKEN_COUNT = 5;
	// sentences that have more terms than this will be discarded
	static final int SELECTOR_MAX_TOKEN_COUNT = 25;

	static final double SIMILARITY_BM25_PARAM_EPSILON = 0.25;
	static final double SIMILARITY_BM25_PARAM_K = 1.2;
	static final double SIMILARITY_BM25_PARAM_B = 0.75;
}
