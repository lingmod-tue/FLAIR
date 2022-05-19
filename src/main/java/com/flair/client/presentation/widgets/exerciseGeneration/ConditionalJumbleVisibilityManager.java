package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class ConditionalJumbleVisibilityManager extends VisibilityManager {

	public ConditionalJumbleVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpCondTypes);
		addConstructionIfOccurs("condUnreal", ExerciseTopic.CONDITIONALS, 1, visibleSettings, taskItem.chkscopeType1, numberExercises);  
		addConstructionIfOccurs("condReal", ExerciseTopic.CONDITIONALS, 1, visibleSettings, taskItem.chkscopeType2, numberExercises);    			
		
		return visibleSettings;
	}
}