package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class RelativesSelectVisibilityManager extends VisibilityManager {

	public RelativesSelectVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		addConstructionIfOccurs("who", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkWho, numberExercises);   			
		addConstructionIfOccurs("which", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkWhich, numberExercises);   			
		addConstructionIfOccurs("that", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkThat, numberExercises);   
		if(visibleSettings.size() > 0) {
			visibleSettings.add(taskItem.grpDistractors);
			visibleSettings.add(taskItem.grpPronouns);
		}
		
		return visibleSettings;
	}
}