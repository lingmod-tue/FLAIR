package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.BracketsProperties;

public class ConditionalHintGenerator implements HintGenerator {

	@Override
	public void generateHints(ExerciseData data) {
		if(data.getBracketsProperties().contains(BracketsProperties.CONDITIONAL_TYPE)) {
			data.setHintLink("Tip zu Conditionals Type 2|/media/lif/WebContent/LiF7.html");
		} else {
			data.setHintLink("Tip zu Conditionals Type 2|/media/lif/WebContent/LiF7.html;Tip zu Conditionals Type 1|/media/lif/WebContent/LiF6R.html");
		}
	}
}
