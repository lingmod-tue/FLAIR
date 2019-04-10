package com.flair.server.pipelines.questgen;

import java.util.concurrent.TimeUnit;

class Constants {
	static final int COREF_MAX_MENTION_DISTANCE = 25;
	static final int COREF_MAX_MENTION_DISTANCE_STRING_MATCH = 100;
	static final int COREF_MAX_REPRESENTATIVE_MENTION_TOKEN_COUNT = 5;
	static final boolean COREF_ONLY_REPLACE_FIRST_MENTION_IN_SENTENCE = true;

	static final int PARSE_TASK_THREADPOOL_SIZE = 50;
	static final int SENTENCESEL_TASK_THREADPOOL_SIZE = 50;
	static final int QUESTGEN_TASK_THREADPOOL_SIZE = 50;

	static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	static final int NERCOREF_PARSE_TASK_TIMEOUT = 300;

	static final int GENERATOR_MAX_GENERATED_TREE_LEAF_COUNT = 1000;

	// the pool of generated questions from which the final question for a given sentence is selected
	static final int QUESTGEN_BESTQPOOL_SIZE = 5;
	// percentage of input sentences from which to generate questions
	// used to select distractors for the originally requested number of questions
	static final double QUESTGEN_OVERGENERATION_PERCENTAGE = 0.75;
	static final int QUESTGEN_NUM_DISTRACTORS = 3;
	// remove consecutive token sequences found in both the question and the answer
	static final boolean QUESTGEN_REMOVE_QUESTION_COMMON_TOKEN_SEQUENCE_FROM_ANSWER = true;
	// consecutive token sequences found in both the question and the answer are removed from the latter
	// if the sequence length is larger than this value
	static final int QUESTGEN_MIN_QUESTION_COMMON_TOKEN_SEQUENCE_LENGTH = 3;

	// ratio of the number of tokens cooccuring in a higher-ranked sentence to the
	// total number of tokens in the given sentence. if this ratio is higher than the
	// one below, the sentence is dropped from the ranked list
	static final double SENTENCESEL_DUPLICATE_COOCCURRENCE_THRESHOLD = 0.60;
}
