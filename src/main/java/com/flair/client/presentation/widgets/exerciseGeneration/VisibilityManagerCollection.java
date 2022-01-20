package com.flair.client.presentation.widgets.exerciseGeneration;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class VisibilityManagerCollection {

	public VisibilityManager getVisibilityManger(String topic, String exerciseType) {
		if(topic.equals(ExerciseTopic.PASSIVE)) {
    		if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
    			return passiveFiBVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP)) {
    			 return passiveDragVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
	   			 return passiveJumbleVisibilityManger;
	   		} 
    	} else if(topic.equals(ExerciseTopic.RELATIVES)) {
    		if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
    			return relativesFiBVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
    			return relativesSelectVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.MARK_THE_WORDS)) {
    			return realtivesMarkVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP)) {
    			return relativesDragVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
	   			 return relativesJumbleVisibilityManger;
	   		} 
    	} else if(topic.equals(ExerciseTopic.PRESENT)) {
    		if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
    			return presentFiBVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
    			return presentSelectVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.MARK_THE_WORDS)) {
    			return presentMarkVisibilityManger;  	
    		} else if(exerciseType.equals(ExerciseType.MEMORY)) {
    			return presentMemoryVisibilityManger;  	
    		}  else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
	   			 return presentJumbleVisibilityManger;
	   		} 
    	} else if(topic.equals(ExerciseTopic.PAST)) {
    		if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
    			return pastFiBVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
    			return pastSelectVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.MARK_THE_WORDS)) {
    			return pastMarkVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP)) {
    			return pastDragVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.MEMORY)) {
    			return pastMemoryVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
	   			 return pastJumbleVisibilityManger;
	   		} 
    	} else if(topic.equals(ExerciseTopic.CONDITIONALS)) {
    		if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
    			return conditionalFiBVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
    			return conditionalSelectVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP)) {
    			return conditionalDragVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
	   			 return conditionalJumbleVisibilityManger;
	   		} 
    	}else if(topic.equals(ExerciseTopic.COMPARISON)) {
			if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
				return compareFiBVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
    			return compareSelectVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.MARK_THE_WORDS)) {
    			return compareMarkVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP)) {
    			return compareDragVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.MEMORY)) {
    			return compareMemoryVisibilityManger;
    		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
	   			 return compareJumbleVisibilityManger;
	   		} 
    	}
		
		return null;
	}
	
	public VisibilityManagerCollection(TaskItem taskItem) {	
		passiveFiBVisibilityManger = new PassiveFiBVisibilityManager(taskItem);
		passiveDragVisibilityManger = new PassiveDragVisibilityManager(taskItem);
		passiveJumbleVisibilityManger = new PassiveJumbleVisibilityManager(taskItem);
		relativesFiBVisibilityManger = new RelativesFiBVisibilityManager(taskItem);
		relativesSelectVisibilityManger = new RelativesSelectVisibilityManager(taskItem);
		realtivesMarkVisibilityManger = new RelativesMarkVisibilityManager(taskItem);
		relativesDragVisibilityManger = new RelativesDragVisibilityManager(taskItem);
		relativesJumbleVisibilityManger = new RelativesJumbleVisibilityManager(taskItem);
		presentFiBVisibilityManger = new PresentFiBVisibilityManager(taskItem);
		presentSelectVisibilityManger = new PresentSelectVisibilityManager(taskItem);
		presentMarkVisibilityManger = new PresentMarkVisibilityManager(taskItem);
		presentMemoryVisibilityManger = new PresentMemoryVisibilityManager(taskItem);
		presentJumbleVisibilityManger = new PresentJumbleVisibilityManager(taskItem);
		pastFiBVisibilityManger = new PastFiBVisibilityManager(taskItem);
		pastSelectVisibilityManger = new PastSelectVisibilityManager(taskItem);
		pastMarkVisibilityManger = new PastMarkVisibilityManager(taskItem);
		pastDragVisibilityManger = new PastDragVisibilityManager(taskItem);
		pastMemoryVisibilityManger = new PastMemoryVisibilityManager(taskItem);
		pastJumbleVisibilityManger = new PastJumbleVisibilityManager(taskItem);
		conditionalFiBVisibilityManger = new ConditionalFiBVisibilityManager(taskItem);
		conditionalSelectVisibilityManger = new ConditionalSelectVisibilityManager(taskItem);
		conditionalDragVisibilityManger = new ConditionalDragVisibilityManager(taskItem);
		conditionalJumbleVisibilityManger = new ConditionalJumbleVisibilityManager(taskItem);
		compareFiBVisibilityManger = new CompareFiBVisibilityManager(taskItem);
		compareSelectVisibilityManger = new CompareSelectVisibilityManager(taskItem);
		compareMarkVisibilityManger = new CompareMarkVisibilityManager(taskItem);
		compareDragVisibilityManger = new CompareDragVisibilityManager(taskItem);
		compareMemoryVisibilityManger = new CompareMemoryVisibilityManager(taskItem);
		compareJumbleVisibilityManger = new CompareJumbleVisibilityManager(taskItem);
	}
    		
	private final PassiveFiBVisibilityManager passiveFiBVisibilityManger;
	private final PassiveDragVisibilityManager passiveDragVisibilityManger;
	private final PassiveJumbleVisibilityManager passiveJumbleVisibilityManger;
	private final RelativesFiBVisibilityManager relativesFiBVisibilityManger;
	private final RelativesSelectVisibilityManager relativesSelectVisibilityManger;
	private final RelativesMarkVisibilityManager realtivesMarkVisibilityManger;
	private final RelativesDragVisibilityManager relativesDragVisibilityManger;
	private final RelativesJumbleVisibilityManager relativesJumbleVisibilityManger;
	private final PresentFiBVisibilityManager presentFiBVisibilityManger;
	private final PresentSelectVisibilityManager presentSelectVisibilityManger;
	private final PresentMarkVisibilityManager presentMarkVisibilityManger;
	private final PresentMemoryVisibilityManager presentMemoryVisibilityManger;
	private final PresentJumbleVisibilityManager presentJumbleVisibilityManger;
	private final PastFiBVisibilityManager pastFiBVisibilityManger;
	private final PastSelectVisibilityManager pastSelectVisibilityManger;
	private final PastMarkVisibilityManager pastMarkVisibilityManger;
	private final PastDragVisibilityManager pastDragVisibilityManger;
	private final PastMemoryVisibilityManager pastMemoryVisibilityManger;
	private final PastJumbleVisibilityManager pastJumbleVisibilityManger;
	private final ConditionalFiBVisibilityManager conditionalFiBVisibilityManger;
	private final ConditionalSelectVisibilityManager conditionalSelectVisibilityManger;
	private final ConditionalDragVisibilityManager conditionalDragVisibilityManger;
	private final ConditionalJumbleVisibilityManager conditionalJumbleVisibilityManger;
	private final CompareFiBVisibilityManager compareFiBVisibilityManger;
	private final CompareSelectVisibilityManager compareSelectVisibilityManger;
	private final CompareMarkVisibilityManager compareMarkVisibilityManger;
	private final CompareDragVisibilityManager compareDragVisibilityManger;
	private final CompareMemoryVisibilityManager compareMemoryVisibilityManger;
	private final CompareJumbleVisibilityManager compareJumbleVisibilityManger;

}