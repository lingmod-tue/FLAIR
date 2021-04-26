package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class RelativesSelectVisibilityManager extends VisibilityManager {

	public RelativesSelectVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	public ArrayList<Widget> getVisibleWidgets() {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpDistractors);
		visibleSettings.add(taskItem.grpPronouns);
		addConstructionIfOccurs("who", "Relatives", 1, visibleSettings, taskItem.chkWho);   			
		addConstructionIfOccurs("which", "Relatives", 1, visibleSettings, taskItem.chkWhich);   			
		addConstructionIfOccurs("that", "Relatives", 1, visibleSettings, taskItem.chkThat);   			
		addConstructionIfOccurs("otherRelPron", "Relatives", 1, visibleSettings, taskItem.chkOtherRelPron);
		
		return visibleSettings;
	}
}