package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PresentSelectVisibilityManager extends VisibilityManager {

	public PresentSelectVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpDistractors);    
		visibleSettings.add(taskItem.chkDistractorsWrongSuffixUse);  
		visibleSettings.add(taskItem.chkDistractorsWrongSuffix);  
		visibleSettings.add(taskItem.grpVerbPerson);  
		addConstructionIfOccurs("3", "Present", 4, visibleSettings, taskItem.chk3Pers, numberExercises);   			
		addConstructionIfOccurs("not3", "Present", 4, visibleSettings, taskItem.chkNot3Pers, numberExercises);   			
		visibleSettings.add(taskItem.grpSentenceTypes); 
		addConstructionIfOccurs("affirm", "Present", 3, visibleSettings, taskItem.chkAffirmativeSent, numberExercises);   			
		addConstructionIfOccurs("neg", "Present", 3, visibleSettings, taskItem.chkNegatedSent, numberExercises);   			
		addConstructionIfOccurs("question", "Present", 2, visibleSettings, taskItem.chkQuestions, numberExercises);   			
		addConstructionIfOccurs("stmt", "Present", 2, visibleSettings, taskItem.chkStatements, numberExercises);   
		
		return visibleSettings;
	}
}