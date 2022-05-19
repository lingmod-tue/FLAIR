package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class RelativesFiBVisibilityManager extends VisibilityManager {

	public RelativesFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpPronouns);
		addConstructionIfOccurs("who", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkWho, numberExercises);   			
		addConstructionIfOccurs("which", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkWhich, numberExercises);   			
		addConstructionIfOccurs("that", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkThat, numberExercises);   			
		addConstructionIfOccurs("otherRelPron", ExerciseTopic.RELATIVES, 1, visibleSettings, taskItem.chkOtherRelPron, numberExercises);
		
		return visibleSettings;
	}
}