package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class ConditionalSelectVisibilityManager extends VisibilityManager {

	public ConditionalSelectVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpDistractors);
		visibleSettings.add(taskItem.chkDistractorsWrongConditional);
		visibleSettings.add(taskItem.chkDistractorsWrongClause);
		visibleSettings.add(taskItem.grpCondTypes);
		addConstructionIfOccurs("condUnreal", ExerciseTopic.CONDITIONALS, 1, visibleSettings, taskItem.chkscopeType1, numberExercises);  
		addConstructionIfOccurs("condReal", ExerciseTopic.CONDITIONALS, 1, visibleSettings, taskItem.chkscopeType2, numberExercises); 
		visibleSettings.add(taskItem.grpClauses);
		
		return visibleSettings;
	}
}