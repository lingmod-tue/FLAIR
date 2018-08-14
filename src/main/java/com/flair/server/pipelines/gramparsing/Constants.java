/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.pipelines.gramparsing;

import java.util.concurrent.TimeUnit;

/**
 * Task manager specific constants
 *
 * @author shadeMe
 */
class Constants {
	static final int WEB_SEARCH_TASK_THREADPOOL_SIZE = 50;
	static final int WEB_CRAWL_TASK_THREADPOOL_SIZE = 50;
	static final int PARSE_DOC_TASK_THREADPOOL_SIZE = com.flair.server.scheduler.Constants.BASELINE_CONCURRENT_THREADS;

	static final String ENGLISH_SR_PARSER_MODEL = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";
	static final String GERMAN_SR_PARSER_MODEL = "edu/stanford/nlp/models/srparser/germanSR.ser.gz";
	static final String GERMAN_POS_MODEL = "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger";

	// timeouts must be large enough to include waiting time for tasks that have yet to start
	static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	static final int WEB_CRAWL_TASK_TIMEOUT = 30;
	static final int PARSE_DOC_TASK_TIMEOUT = 300;

	// results with fewer tokens than this are discarded
	static final int SEARCH_RESULT_MINIMUM_TOKEN_COUNT = 100;
	static final int MAX_WEB_CRAWLS_PER_OP = 100;
}
