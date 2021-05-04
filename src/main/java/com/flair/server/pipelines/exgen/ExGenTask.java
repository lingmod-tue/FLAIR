package com.flair.server.pipelines.exgen;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseManager;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.scheduler.AsyncTask;
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
        ExerciseManager exerciseManger = new ExerciseManager();
        byte[] file = exerciseManger.generateExercises(settings, parser, generator);
                
		Result result = new Result(file);
		ServerLogger.get().trace("Created exercise");
		return result;
	}

	static final class Result {
		final byte[] file;

		Result(byte[] file) {
			this.file = file;
		}
	}
}
