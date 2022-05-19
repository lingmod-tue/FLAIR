package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class PresentMemoryVisibilityManager extends VisibilityManager {

	public PresentMemoryVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpVerbPerson);  
		addConstructionIfOccurs("3", ExerciseTopic.PRESENT, 4, visibleSettings, taskItem.chk3Pers, numberExercises);   			
		addConstructionIfOccurs("not3", ExerciseTopic.PRESENT, 4, visibleSettings, taskItem.chkNot3Pers, numberExercises);   			
		visibleSettings.add(taskItem.grpSentenceTypes); 
		addConstructionIfOccurs("affirm", ExerciseTopic.PRESENT, 3, visibleSettings, taskItem.chkAffirmativeSent, numberExercises);   			
		addConstructionIfOccurs("neg", ExerciseTopic.PRESENT, 3, visibleSettings, taskItem.chkNegatedSent, numberExercises);   			
		addConstructionIfOccurs("question", ExerciseTopic.PRESENT, 2, visibleSettings, taskItem.chkQuestions, numberExercises);   			
		addConstructionIfOccurs("stmt", ExerciseTopic.PRESENT, 2, visibleSettings, taskItem.chkStatements, numberExercises); 

		return visibleSettings;
	}
}