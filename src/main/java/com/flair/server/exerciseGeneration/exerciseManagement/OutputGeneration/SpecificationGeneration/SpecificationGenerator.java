package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;

public interface SpecificationGenerator {
	
	byte[] generateJsonSpecification(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ArrayList<ExerciseData> datas);

}
