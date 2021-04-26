package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class ConditionalFiBVisibilityManager extends VisibilityManager {

	public ConditionalFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	public ArrayList<Widget> getVisibleWidgets() {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpBrackets);
		visibleSettings.add(taskItem.chkBracketsLemma);
		visibleSettings.add(taskItem.chkBracketsConditional);
		visibleSettings.add(taskItem.chkBracketsWill);
		visibleSettings.add(taskItem.grpCondTypes);
		addConstructionIfOccurs("condUnreal", "'if'", 1, visibleSettings, taskItem.chkscopeType1);  
		addConstructionIfOccurs("condReal", "'if'", 1, visibleSettings, taskItem.chkscopeType2);    			
		visibleSettings.add(taskItem.grpClauses);
		
		return visibleSettings;
	}
}