package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class RelativeHintGenerator implements HintGenerator {
	
	private static final String SECOND_INSTRUCTION_START = "Remember: “who” is used for people, “which” for objects and animals, “where” for places, and “whose” to indicate possession. Sometimes no relative is also an option. <br><br>"
			+ "Examples: <br><ul>";
	private static final String WHO_EXAMPLE = "<li> WHO: The man who talks to Mary is my father.</li>";
	private static final String WHICH_EXAMPLE = "<li>WHICH: The car which parked in front of the supermarket was stolen.</li>";
	private static final String WHERE_EXAMPLE = "<li>WHERE: The restaurant where I saw him is now closed. </li>";
	private static final String WHOSE_EXAMPLE = "<li>WHOSE: The cars whose doors were open were towed away by the police. </li>";
	private static final String NONE_EXAMPLE = "<li>NONE: The food I really love is tacos. </li>";
	private static final String SECOND_INSTRUCTION_END = "</ul>";
	
	@Override
	public void generateHints(ExerciseData data) {
		//data.setSupport("Relative clauses require <br> the use of words such as “that”, <br> “who”, “which”, “whose”, “where” or <br> “whom”. See the information <br> at the bottom.");
		
		String secondInstruction = SECOND_INSTRUCTION_START;
		if(data.getSubtopic().equals("subject who/which")) {
			secondInstruction += WHO_EXAMPLE + WHICH_EXAMPLE;
		} else if(data.getSubtopic().equals("object which/whom")) {
			secondInstruction += WHO_EXAMPLE + WHICH_EXAMPLE + WHERE_EXAMPLE + WHOSE_EXAMPLE + NONE_EXAMPLE;
		} else if(data.getSubtopic().equals("where")) {
			secondInstruction += WHO_EXAMPLE + WHICH_EXAMPLE + WHERE_EXAMPLE + WHOSE_EXAMPLE;
		} else if(data.getSubtopic().equals("whose")) {
			secondInstruction += WHO_EXAMPLE + WHICH_EXAMPLE + WHOSE_EXAMPLE;
		} else if(data.getSubtopic().equals("contact clauses")) {
			secondInstruction += WHO_EXAMPLE + WHICH_EXAMPLE + WHERE_EXAMPLE + WHOSE_EXAMPLE + NONE_EXAMPLE;
		} else {
			System.out.println("Subtopic " + data.getSubtopic() + " not supported.");
			return;
		}
		
		secondInstruction += SECOND_INSTRUCTION_END;
		data.setSecondInstructions(secondInstruction);
	}
}
