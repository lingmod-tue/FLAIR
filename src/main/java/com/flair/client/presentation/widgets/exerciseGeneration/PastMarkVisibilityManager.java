package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class PastMarkVisibilityManager extends VisibilityManager {

	public PastMarkVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.lblTensesWords);
		addConstructionIfOccurs("TENSE_PAST_SIMPLE", ExerciseTopic.PAST, 1, visibleSettings, taskItem.chkPastSimple, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT", ExerciseTopic.PAST, 1, visibleSettings, taskItem.chkPresentPerfect, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT", ExerciseTopic.PAST, 1, visibleSettings, taskItem.chkPastPerfect, numberExercises);        
		addConstructionIfOccurs("TENSE_PAST_PROGRESSIVE", ExerciseTopic.PAST, 1, visibleSettings, taskItem.chkPastProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT_PROGRESSIVE", ExerciseTopic.PAST, 1, visibleSettings, taskItem.chkPresentPerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT_PROGRESSIVE", ExerciseTopic.PAST, 1, visibleSettings, taskItem.chkPastPerfectProg, numberExercises);      			
		visibleSettings.add(taskItem.grpVerbForms);
		addConstructionIfOccurs("irreg", ExerciseTopic.PAST, 4, visibleSettings, taskItem.chkIrregularVerbs, numberExercises);   
		addConstructionIfOccurs("reg", ExerciseTopic.PAST, 4, visibleSettings, taskItem.chkRegularVerbs, numberExercises);  
		visibleSettings.add(taskItem.grpInstructions);
		visibleSettings.add(taskItem.chkNTargets);

		return visibleSettings;
	}
}