package com.flair.server.pipelines.exgen;

import java.util.ArrayList;

import com.flair.server.scheduler.AsyncTask;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public class ExGenTask implements AsyncTask<ExGenTask.Result> {
	public static ExGenTask factory(ArrayList<ExerciseSettings> settings) {
		return new ExGenTask(settings);
	}

	private final ArrayList<ExerciseSettings> settings;

	private ExGenTask(ArrayList<ExerciseSettings> settings) {
		this.settings = settings;
	}


	@Override
	public Result run() {		
		//TODO: start logic here!
		System.out.println("Here we should generate some files");
		Result result = new Result(new byte[] {});
		ServerLogger.get().trace("Created exercises");
		return result;
	}

	static final class Result {
		final byte[] file;

		Result(byte[] file) {
			this.file = file;
		}
	}
}
