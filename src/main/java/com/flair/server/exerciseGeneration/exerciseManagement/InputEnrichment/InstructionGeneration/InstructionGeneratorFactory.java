package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class InstructionGeneratorFactory {
	
    public static InstructionGenerator getGenerator(String topic, String type) {
    	if(type.equals(ExerciseType.CATEGORIZE)) {
			if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new CategorizeConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new CategorizeRelativeInstructionGenerator();
			}
		} else if(type.equals(ExerciseType.FILL_IN_THE_BLANKS) || type.equals(ExerciseType.SHORT_ANSWER)) {
			if(topic.equals(ExerciseTopic.COMPARISON)) {
				return new FiBComparisonInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new FiBConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PASSIVE)) {
				return new FiBPassiveInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PRESENT)) {
				return new FiBPresentInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PAST)) {
				return new FiBPastInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new FiBRelativeInstructionGenerator();
			}
		} else if(type.equals(ExerciseType.SINGLE_CHOICE)) {
			if(topic.equals(ExerciseTopic.COMPARISON)) {
				return new SCComparisonInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new SCConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PRESENT)) {
				return new SCPresentInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PAST)) {
				return new SCPastInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new SCRelativeInstructionGenerator();
			}
		} else if(type.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
			if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new DDSingleConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PASSIVE)) {
				return new DDSinglePassiveInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PAST)) {
				return new DDSinglePastInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new DDSingleRelativeInstructionGenerator();
			}
		} else if(type.equals(ExerciseType.DRAG_AND_DROP_MULTI)) {
			if(topic.equals(ExerciseTopic.COMPARISON)) {
				return new DDMultiComparisonInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new DDMultiConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PASSIVE)) {
				return new DDMultiPassiveInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PAST)) {
				return new DDMultiPastInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new DDMultiRelativeInstructionGenerator();
			}
		} else if(type.equals(ExerciseType.JUMBLED_SENTENCES)) {
			return new JSInstructionGenerator();
		} else if(type.equals(ExerciseType.MARK_THE_WORDS)) {
			if(topic.equals(ExerciseTopic.COMPARISON)) {
				return new MtWComparisonInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new MtWConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PRESENT)) {
				return new MtWPresentInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PAST)) {
				return new MtWPastInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new MtWRelativeInstructionGenerator();
			}
		} else if(type.equals(ExerciseType.MEMORY)) {
			if(topic.equals(ExerciseTopic.COMPARISON)) {
				return new MemoryComparisonInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.CONDITIONALS)) {
				return new MemoryConditionalInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PRESENT)) {
				return new MemoryPresentInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.PAST)) {
				return new MemoryPastInstructionGenerator();
			} else if(topic.equals(ExerciseTopic.RELATIVES)) {
				return new MemoryRelativeInstructionGenerator();
			}
		} 

    	return null;
    }
    
}
