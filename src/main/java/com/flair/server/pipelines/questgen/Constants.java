package com.flair.server.pipelines.questgen;

import java.util.concurrent.TimeUnit;

class Constants {
	static final int COREF_MAX_MENTION_DISTANCE = 20;
	static final int COREF_MAX_MENTION_DISTANCE_STRING_MATCH = 50;
	static final int COREF_MAX_REPRESENTATIVE_MENTION_TOKEN_COUNT = 5;
	static final boolean COREF_ONLY_REPLACE_FIRST_MENTION_IN_SENTENCE = true;

	static final int PARSE_TASK_THREADPOOL_SIZE = 50;
	static final int SENTENCESEL_TASK_THREADPOOL_SIZE = 50;
	static final int QUESTGEN_TASK_THREADPOOL_SIZE = 50;

	static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	static final int NERCOREF_PARSE_TASK_TIMEOUT = 300;

	static final int GENERATOR_MAX_GENERATED_TREE_LEAF_COUNT = 1000;

	// how many of the generated questions should be considered when selecting the final question
	static final int QUESTGEN_BESTQPOOL_SIZE = 5;

	// used to determine the minimum number of questions to generate
	static final int QUESTGEN_NUM_DISTRACTOR = 3;

	static final double SENTENCESEL_DUPLICATE_COOCCURRENCE_THRESHOLD = 0.60;
}
