package com.flair.server.pipelines.exgen;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseManager;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

import edu.stanford.nlp.util.Pair;

public class ExGenTask implements AsyncTask<ExGenTask.Result> {
	public static ExGenTask factory(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
			OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
		return new ExGenTask(settings, parser, generator, lemmatizer, resourceDownloader);
	}

	private final ContentTypeSettings settings;
	private final CoreNlpParser parser;
	private final SimpleNlgParser generator;
	private final OpenNlpParser lemmatizer;
	private final ResourceDownloader resourceDownloader;

	private ExGenTask(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
			OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
		this.settings = settings;
		this.parser = parser;
		this.generator = generator;
		this.lemmatizer = lemmatizer;
		this.resourceDownloader = resourceDownloader;
	}


	@Override
	public Result run() {		
		Pair<String, byte[]> file = new Pair<>(null, null);
		long startTime = 0;
		boolean error = false;

		ExerciseManager exerciseManager = new ExerciseManager(settings);
		try {
			startTime = System.currentTimeMillis();
			file = ThreadPool.get().invokeAndWait(new FutureTask<>(() -> {
		        return exerciseManager.generateExercises(settings, parser, generator, lemmatizer, resourceDownloader);
			}), Constants.EXERCISE_GENERATION_TASK_TIMEOUT, Constants.TIMEOUT_UNIT);
		} catch (TimeoutException ex) {
			ServerLogger.get().error("Exercise generation task timed-out.");
		} catch(InterruptedException ex) {
			exerciseManager.stopExecution();
			error = true;
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Exercise generation task encountered an error. Exception: " + ex.toString());
			error = true;
		}

		long endTime = System.currentTimeMillis();
		if (!error)
			ServerLogger.get().info("Exercise " + file.first + " generated in " + (endTime - startTime) + " ms");

		return new Result(file.second, file.first);
	}

	static final class Result {
		final byte[] file;
		final String fileName;

		Result(byte[] file, String fileName) {
			this.file = file;
			this.fileName = fileName;
		}
	}
}
