package com.flair.server.scheduler;

public class Constants {
	public static final int PRIMARY_THREAD_POOL_SIZE = 150;
	public static final int TIMEOUTABLE_THREAD_POOL_SIZE = 50;

	public static final int BASELINE_CONCURRENT_THREADS = Runtime.getRuntime().availableProcessors() + 1;

}
