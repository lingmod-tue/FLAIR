package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class RelativeHintGenerator implements HintGenerator {

	@Override
	public void generateHints(ExerciseData data) {
		data.setSupport("Relative clauses require <br> the use of words such as “that”, <br> “who”, “which”, “whose”, “where” or <br> “whom”. See the information <br> at the bottom.");
		data.setSecondInstructions("Remember: “who” is used for people, “which” for objects and animals, “that” for all of them, “where” for places, and “whose” to indicate possession. Sometimes no relative is also an option. Here are some examples: <br>\n"
				+ " \n"
				+ "Examples: <br>\n"
				+ "<ul>\n"
				+ "<li> WHO: The man who talks to Mary is my father.</li>\n"
				+ "<li>WHICH: The car which parked in front of the supermarket was stolen.</li>\n"
				+ "<li>THAT: The man that talks to Mary is my father. </li> \n"
				+ "<li>THAT: The car that parked in front of the supermarket was stolen. </li>\n"
				+ "<li>WHERE: The restaurant where I saw him is now closed. </li>\n"
				+ "<li>WHOSE: The cars whose doors were open were towed away by the police. </li>\n"
				+ "<li>NONE: The food I really love is tacos. </li>");
	}
}
