
package com.flair.server.pipelines.exgen;

import java.util.concurrent.TimeUnit;

class Constants {
	static final int DOWNLOAD_TASK_THREADPOOL_SIZE = 50;
	static final int EXERCISE_GENERATION_TASK_THREADPOOL_SIZE = 50;

	// timeouts must be large enough to include waiting time for tasks that have yet to start
	static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	static final int DOWNLOAD_TASK_TIMEOUT = 500;
	static final int EXERCISE_GENERATION_TASK_TIMEOUT = 1000;

}
