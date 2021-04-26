package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class CompareSelectVisibilityManager extends VisibilityManager {

	public CompareSelectVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	public ArrayList<Widget> getVisibleWidgets() {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpDistractors);
		visibleSettings.add(taskItem.chkDistractorsOtherForm);
		visibleSettings.add(taskItem.chkDistractorsOtherVariant);
		visibleSettings.add(taskItem.chkDistractorsIncorrectForms);
		visibleSettings.add(taskItem.grpPos);
		addConstructionIfOccurs("adj", "Compare", 1, visibleSettings, taskItem.chkPosAdj); 
		addConstructionIfOccurs("adv", "Compare", 1, visibleSettings, taskItem.chkPosAdv); 
		visibleSettings.add(taskItem.grpCompForm);
		addConstructionIfOccurs("comp", "Compare", 2, visibleSettings, taskItem.chkFormComparatives); 
		addConstructionIfOccurs("sup", "Compare", 2, visibleSettings, taskItem.chkFormSuperlatives); 
		visibleSettings.add(taskItem.grpForms);
		addConstructionIfOccurs("syn", "Compare", 3, visibleSettings, taskItem.chkFormSynthetic); 
		addConstructionIfOccurs("ana", "Compare", 3, visibleSettings, taskItem.chkFormAnalytic);
		
		return visibleSettings;
	}
}