package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;

public class ConstructionsExtractorFactory {

	public static ConstructionsExtractor getConstructionsExtractor(IExerciseSettings settings,
			NlpManager nlpManager){
		if(settings instanceof ConfigExerciseSettings) {
			return new ConstructionsFromConfigExtractor();
		} else {
			if(((ExerciseSettings)settings).getConstructions().size() == 0) {
				throw new IllegalArgumentException("The configuration contains no constructions.");
			}
			String constructionType = ((ExerciseSettings)settings).getConstructions().get(0).getConstruction().toString();

			if(constructionType.startsWith("COND")) {     
	            return new ConditionalConstructionsFromTextExtractor(nlpManager);
	        } else if(constructionType.startsWith("ADJ") || constructionType.startsWith("ADV")) {
	            return new ComparisonConstructionsFromTextExtractor(nlpManager);
	        } else if(constructionType.startsWith("PASSIVE") || constructionType.startsWith("ACTIVE")) {
	            return new PassiveConstructionsFromTextExtractor(nlpManager);
	        } else if(constructionType.startsWith("QUEST") || constructionType.startsWith("STMT")) {
	            return new PresentConstructionsFromTextExtractor(nlpManager, ((ExerciseSettings)settings).getConstructions());
	        } else if(constructionType.startsWith("PAST") || constructionType.startsWith("PRES")) {
	            return new PastConstructionsFromTextExtractor(nlpManager);
	        } else {
	        	throw new UnsupportedOperationException("The construction type '" + constructionType + "' is not supported.");
	        }
		} 
	}
	
}
