package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.InputStream;
import java.util.ArrayList;

public abstract class JsonFileReader {
	
	public abstract ArrayList<ExerciseConfigData> parse(InputStream jsonContent);
	
}
