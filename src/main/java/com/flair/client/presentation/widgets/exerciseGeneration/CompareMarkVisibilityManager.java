package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class CompareMarkVisibilityManager extends VisibilityManager {

	public CompareMarkVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpPos);
		addConstructionIfOccurs("adj", ExerciseTopic.COMPARISON, 1, visibleSettings, taskItem.chkPosAdj, numberExercises); 
		addConstructionIfOccurs("adv", ExerciseTopic.COMPARISON, 1, visibleSettings, taskItem.chkPosAdv, numberExercises); 
		visibleSettings.add(taskItem.grpCompForm);
		addConstructionIfOccurs("comp", ExerciseTopic.COMPARISON, 2, visibleSettings, taskItem.chkFormComparatives, numberExercises); 
		addConstructionIfOccurs("sup", ExerciseTopic.COMPARISON, 2, visibleSettings, taskItem.chkFormSuperlatives, numberExercises); 
		visibleSettings.add(taskItem.grpForms);
		addConstructionIfOccurs("syn", ExerciseTopic.COMPARISON, 3, visibleSettings, taskItem.chkFormSynthetic, numberExercises); 
		addConstructionIfOccurs("ana", ExerciseTopic.COMPARISON, 3, visibleSettings, taskItem.chkFormAnalytic, numberExercises);
		visibleSettings.add(taskItem.grpInstructions);
		visibleSettings.add(taskItem.chkNTargets);
		return visibleSettings;
	}
}