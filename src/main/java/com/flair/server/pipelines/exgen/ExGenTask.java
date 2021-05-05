package com.flair.server.pipelines.exgen;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseManager;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

public class ExGenTask implements AsyncTask<ExGenTask.Result> {
	public static ExGenTask factory(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator) {
		return new ExGenTask(settings, parser, generator);
	}

	private final ContentTypeSettings settings;
	private final CoreNlpParser parser;
	private final SimpleNlgParser generator;

	private ExGenTask(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator) {
		this.settings = settings;
		this.parser = parser;
		this.generator = generator;
	}


	@Override
	public Result run() {		
		byte[] file;
		long startTime = 0;
		boolean error = false;

		try {
			startTime = System.currentTimeMillis();
			file = ThreadPool.get().invokeAndWait(new FutureTask<>(() -> {
				ExerciseManager exerciseManger = new ExerciseManager();
		        return exerciseManger.generateExercises(settings, parser, generator);
			}), 1000, TimeUnit.SECONDS);
		} catch (TimeoutException ex) {
			ServerLogger.get().error("Exercise generation task timed-out.");
			file = null;
			error = true;
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Exercise generation task encountered an error. Exception: " + ex.toString());
			file = null;
			error = true;
		}

		long endTime = System.currentTimeMillis();
		if (!error)
			ServerLogger.get().info("Exercise generated in " + (endTime - startTime) + " ms");

		return new Result(file);
	}

	static final class Result {
		final byte[] file;

		Result(byte[] file) {
			this.file = file;
		}
	}
}
