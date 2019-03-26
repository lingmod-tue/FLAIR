package com.flair.server.pipelines.questgen;

class Constants {
	static final int SENTENCESEL_TASK_THREADPOOL_SIZE = 50;
	static final int QUESTGEN_TASK_THREADPOOL_SIZE = 50;

	// sentences that have fewer unique tokens than this will be discarded
	static final int SELECTOR_MIN_UNIQUE_TOKEN_COUNT = 5;
	static final int GENERATOR_MAX_GENERATED_TREE_LEAF_COUNT = 1000;

	// how many of the generated questions should be considered when selecting the final question
	static final int QUESTGEN_BESTQPOOL_SIZE = 3;

	// used to determine the minimum number of questions to generate
	static final int QUESTGEN_NUM_DISTRACTOR = 3;
}
