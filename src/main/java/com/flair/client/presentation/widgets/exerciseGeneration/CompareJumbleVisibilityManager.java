package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class CompareJumbleVisibilityManager extends VisibilityManager {

	public CompareJumbleVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpPos);
		addConstructionIfOccurs("adj", "Compare", 1, visibleSettings, taskItem.chkPosAdj, numberExercises); 
		addConstructionIfOccurs("adv", "Compare", 1, visibleSettings, taskItem.chkPosAdv, numberExercises); 
		visibleSettings.add(taskItem.grpCompForm);
		addConstructionIfOccurs("comp", "Compare", 2, visibleSettings, taskItem.chkFormComparatives, numberExercises); 
		addConstructionIfOccurs("sup", "Compare", 2, visibleSettings, taskItem.chkFormSuperlatives, numberExercises); 
		visibleSettings.add(taskItem.grpForms);
		addConstructionIfOccurs("syn", "Compare", 3, visibleSettings, taskItem.chkFormSynthetic, numberExercises); 
		addConstructionIfOccurs("ana", "Compare", 3, visibleSettings, taskItem.chkFormAnalytic, numberExercises);
		
		return visibleSettings;
	}
}