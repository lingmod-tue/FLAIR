package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PresentMarkVisibilityManager extends VisibilityManager {

	public PresentMarkVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpVerbPerson);  
		addConstructionIfOccurs("3", "Present", 4, visibleSettings, taskItem.chk3Pers, numberExercises);   			
		addConstructionIfOccurs("not3", "Present", 4, visibleSettings, taskItem.chkNot3Pers, numberExercises); 
		
		return visibleSettings;
	}
}