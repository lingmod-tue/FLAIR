package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class ConditionalDragVisibilityManager extends VisibilityManager {

	public ConditionalDragVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpCondTypes);
		addConstructionIfOccurs("condUnreal", "'if'", 1, visibleSettings, taskItem.chkscopeType1, numberExercises);  
		addConstructionIfOccurs("condReal", "'if'", 1, visibleSettings, taskItem.chkscopeType2, numberExercises); 
		if(taskItem.rbtSingleTask.getValue()) {
			visibleSettings.add(taskItem.grpClauses);
		}
		visibleSettings.add(taskItem.grpScope);
		
		return visibleSettings;
	}
}