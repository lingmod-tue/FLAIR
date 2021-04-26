package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class RelativesFiBVisibilityManager extends VisibilityManager {

	public RelativesFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.grpPronouns);
		addConstructionIfOccurs("who", "Relatives", 1, visibleSettings, taskItem.chkWho, numberExercises);   			
		addConstructionIfOccurs("which", "Relatives", 1, visibleSettings, taskItem.chkWhich, numberExercises);   			
		addConstructionIfOccurs("that", "Relatives", 1, visibleSettings, taskItem.chkThat, numberExercises);   			
		addConstructionIfOccurs("otherRelPron", "Relatives", 1, visibleSettings, taskItem.chkOtherRelPron, numberExercises);
		
		return visibleSettings;
	}
}