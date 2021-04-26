package com.flair.client.presentation.widgets.exerciseGeneration;

public class VisibilityManagerCollection {

	public VisibilityManager getVisibilityManger(String topic, String exerciseType) {
		if(topic.equals("Passive")) {
    		if(exerciseType.equals("FiB")) {
    			return passiveFiBVisibilityManger;
    		} else if(exerciseType.equals("Drag")) {
    			 return passiveDragVisibilityManger;
    		}
    	} else if(topic.equals("Relatives")) {
    		if(exerciseType.equals("FiB")) {
    			return relativesFiBVisibilityManger;
    		} else if(exerciseType.equals("Select")) {
    			return relativesSelectVisibilityManger;
    		} else if(exerciseType.equals("Mark")) {
    			return realtivesMarkVisibilityManger;
    		} else if(exerciseType.equals("Drag")) {
    			return relativesDragVisibilityManger;
    		}
    	} else if(topic.equals("Present")) {
    		if(exerciseType.equals("FiB")) {
    			return presentFiBVisibilityManger;
    		} else if(exerciseType.equals("Select")) {
    			return presentSelectVisibilityManger;
    		} else if(exerciseType.equals("Mark")) {
    			return presentMarkVisibilityManger;  	
    		} 
    	} else if(topic.equals("Past")) {
    		if(exerciseType.equals("FiB")) {
    			return pastFiBVisibilityManger;
    		} else if(exerciseType.equals("Select")) {
    			return pastSelectVisibilityManger;
    		} else if(exerciseType.equals("Mark")) {
    			return pastMarkVisibilityManger;
    		} else if(exerciseType.equals("Drag")) {
    			return pastDragVisibilityManger;
    		}
    	} else if(topic.equals("'if'")) {
    		if(exerciseType.equals("FiB")) {
    			return conditionalFiBVisibilityManger;
    		} else if(exerciseType.equals("Select")) {
    			return conditionalSelectVisibilityManger;
    		} else if(exerciseType.equals("Drag")) {
    			return conditionalDragVisibilityManger;
    		}
    	}else if(topic.equals("Compare")) {
			if(exerciseType.equals("FiB")) {
				return compareFiBVisibilityManger;
    		} else if(exerciseType.equals("Select")) {
    			return compareSelectVisibilityManger;
    		} else if(exerciseType.equals("Mark")) {
    			return compareMarkVisibilityManger;
    		} else if(exerciseType.equals("Drag")) {
    			return compareDragVisibilityManger;
    		}
    	}
		
		return null;
	}
	
	public VisibilityManagerCollection(TaskItem taskItem) {	
		passiveFiBVisibilityManger = new PassiveFiBVisibilityManager(taskItem);
		passiveDragVisibilityManger = new PassiveDragVisibilityManager(taskItem);
		relativesFiBVisibilityManger = new RelativesFiBVisibilityManager(taskItem);
		relativesSelectVisibilityManger = new RelativesSelectVisibilityManager(taskItem);
		realtivesMarkVisibilityManger = new RelativesMarkVisibilityManager(taskItem);
		relativesDragVisibilityManger = new RelativesDragVisibilityManager(taskItem);
		presentFiBVisibilityManger = new PresentFiBVisibilityManager(taskItem);
		presentSelectVisibilityManger = new PresentSelectVisibilityManager(taskItem);
		presentMarkVisibilityManger = new PresentMarkVisibilityManager(taskItem);
		pastFiBVisibilityManger = new PastFiBVisibilityManager(taskItem);
		pastSelectVisibilityManger = new PastSelectVisibilityManager(taskItem);
		pastMarkVisibilityManger = new PastMarkVisibilityManager(taskItem);
		pastDragVisibilityManger = new PastDragVisibilityManager(taskItem);
		conditionalFiBVisibilityManger = new ConditionalFiBVisibilityManager(taskItem);
		conditionalSelectVisibilityManger = new ConditionalSelectVisibilityManager(taskItem);
		conditionalDragVisibilityManger = new ConditionalDragVisibilityManager(taskItem);
		compareFiBVisibilityManger = new CompareFiBVisibilityManager(taskItem);
		compareSelectVisibilityManger = new CompareSelectVisibilityManager(taskItem);
		compareMarkVisibilityManger = new CompareMarkVisibilityManager(taskItem);
		compareDragVisibilityManger = new CompareDragVisibilityManager(taskItem);
	}
    		
	private final PassiveFiBVisibilityManager passiveFiBVisibilityManger;
	private final PassiveDragVisibilityManager passiveDragVisibilityManger;
	private final RelativesFiBVisibilityManager relativesFiBVisibilityManger;
	private final RelativesSelectVisibilityManager relativesSelectVisibilityManger;
	private final RelativesMarkVisibilityManager realtivesMarkVisibilityManger;
	private final RelativesDragVisibilityManager relativesDragVisibilityManger;
	private final PresentFiBVisibilityManager presentFiBVisibilityManger;
	private final PresentSelectVisibilityManager presentSelectVisibilityManger;
	private final PresentMarkVisibilityManager presentMarkVisibilityManger;
	private final PastFiBVisibilityManager pastFiBVisibilityManger;
	private final PastSelectVisibilityManager pastSelectVisibilityManger;
	private final PastMarkVisibilityManager pastMarkVisibilityManger;
	private final PastDragVisibilityManager pastDragVisibilityManger;
	private final ConditionalFiBVisibilityManager conditionalFiBVisibilityManger;
	private final ConditionalSelectVisibilityManager conditionalSelectVisibilityManger;
	private final ConditionalDragVisibilityManager conditionalDragVisibilityManger;
	private final CompareFiBVisibilityManager compareFiBVisibilityManger;
	private final CompareSelectVisibilityManager compareSelectVisibilityManger;
	private final CompareMarkVisibilityManager compareMarkVisibilityManger;
	private final CompareDragVisibilityManager compareDragVisibilityManger;
	
}