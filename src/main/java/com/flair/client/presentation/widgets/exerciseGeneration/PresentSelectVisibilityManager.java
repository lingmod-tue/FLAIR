package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PresentSelectVisibilityManager extends VisibilityManager {

	public PresentSelectVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	public ArrayList<Widget> getVisibleWidgets() {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpDistractors);    
		visibleSettings.add(taskItem.chkDistractorsWrongSuffixUse);  
		visibleSettings.add(taskItem.chkDistractorsWrongSuffix);  
		visibleSettings.add(taskItem.grpVerbPerson);  
		addConstructionIfOccurs("3", "Present", 4, visibleSettings, taskItem.chk3Pers);   			
		addConstructionIfOccurs("not3", "Present", 4, visibleSettings, taskItem.chkNot3Pers);   			
		visibleSettings.add(taskItem.grpSentenceTypes); 
		addConstructionIfOccurs("affirm", "Present", 3, visibleSettings, taskItem.chkAffirmativeSent);   			
		addConstructionIfOccurs("neg", "Present", 3, visibleSettings, taskItem.chkNegatedSent);   			
		addConstructionIfOccurs("question", "Present", 2, visibleSettings, taskItem.chkQuestions);   			
		addConstructionIfOccurs("stmt", "Present", 2, visibleSettings, taskItem.chkStatements);   
		
		return visibleSettings;
	}
}