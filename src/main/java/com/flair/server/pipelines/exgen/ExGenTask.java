package com.flair.server.pipelines.exgen;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseManager;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.utilities.ServerLogger;

public class ExGenTask implements AsyncTask<ExGenTask.Result> {
	public static ExGenTask factory(ContentTypeSettings settings) {
		return new ExGenTask(settings);
	}

	private final ContentTypeSettings settings;

	private ExGenTask(ContentTypeSettings settings) {
		this.settings = settings;
	}


	@Override
	public Result run() {		
        ExerciseManager exerciseManger = new ExerciseManager();
        byte[] file = exerciseManger.generateExercises(settings);
                
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
