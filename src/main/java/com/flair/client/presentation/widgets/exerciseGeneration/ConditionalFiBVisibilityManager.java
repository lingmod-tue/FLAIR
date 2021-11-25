package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class ConditionalFiBVisibilityManager extends VisibilityManager {

	public ConditionalFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpBrackets);
		visibleSettings.add(taskItem.chkBracketsLemma);
		visibleSettings.add(taskItem.chkBracketsConditional);
		visibleSettings.add(taskItem.chkBracketsWill);
		visibleSettings.add(taskItem.grpCondTypes);
		addConstructionIfOccurs("condUnreal", "'if'", 1, visibleSettings, taskItem.chkscopeType1, numberExercises);  
		addConstructionIfOccurs("condReal", "'if'", 1, visibleSettings, taskItem.chkscopeType2, numberExercises);    			
		visibleSettings.add(taskItem.grpClauses);
		visibleSettings.add(taskItem.grpInstructions);
		visibleSettings.add(taskItem.chkLemmas);
		
		return visibleSettings;
	}
}