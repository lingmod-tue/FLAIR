package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;

public class BracketsGeneratorFactory {

	public static BracketsGenerator getBracketsGenerator(IExerciseSettings settings, NlpManager nlpManager) {
		if(settings instanceof ConfigExerciseSettings) {
			return new BracketsFromConfigGenerator();
		} else {
			String constructionType = ((ExerciseSettings)settings).getConstructions().get(0).getConstruction().toString();
			ArrayList<BracketsProperties> bracketsProperties = ((ExerciseSettings)settings).getBrackets();
			String plainText = ((ExerciseSettings)settings).getPlainText();

			if(constructionType.startsWith("COND")) {     
	            return new ConditionalBracketsFromTextGenerator(nlpManager, bracketsProperties);
	        } else if(constructionType.startsWith("ADJ") || constructionType.startsWith("ADV")) {
	            return new ComparisonBracketsFromTextGenerator(nlpManager, bracketsProperties);
	        } else if(constructionType.startsWith("PASSIVE") || constructionType.startsWith("ACTIVE")) {
	            return new PassiveBracketsFromTextGenerator(nlpManager, bracketsProperties, plainText);
	        } else if(constructionType.startsWith("QUEST") || constructionType.startsWith("STMT") ||
	        		constructionType.startsWith("PAST") || constructionType.startsWith("PRES")) {
	            return new PresentAndPastBracketsFromTextGenerator(nlpManager, bracketsProperties);
	        } else {
	        	throw new UnsupportedOperationException();
	        }
		}
	}
	
}
