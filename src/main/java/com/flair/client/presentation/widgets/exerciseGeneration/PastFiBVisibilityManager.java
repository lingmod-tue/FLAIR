package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PastFiBVisibilityManager extends VisibilityManager {

	public PastFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpBrackets);
		visibleSettings.add(taskItem.chkBracketsLemma);
		visibleSettings.add(taskItem.chkBracketsTense);
		visibleSettings.add(taskItem.grpSentenceTypes);
		addConstructionIfOccurs("affirm", "Past", 3, visibleSettings, taskItem.chkAffirmativeSent, numberExercises);   			
		addConstructionIfOccurs("neg", "Past", 3, visibleSettings, taskItem.chkNegatedSent, numberExercises);   			
		addConstructionIfOccurs("question", "Past", 2, visibleSettings, taskItem.chkQuestions, numberExercises);   			
		addConstructionIfOccurs("stmt", "Past", 2, visibleSettings, taskItem.chkStatements, numberExercises);   
		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.lblTensesWords);
		addConstructionIfOccurs("TENSE_PAST_SIMPLE", "Past", 1, visibleSettings, taskItem.chkPastSimple, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT", "Past", 1, visibleSettings, taskItem.chkPresentPerfect, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT", "Past", 1, visibleSettings, taskItem.chkPastPerfect, numberExercises);       			
		visibleSettings.add(taskItem.grpVerbForms);
		addConstructionIfOccurs("irreg", "Past", 4, visibleSettings, taskItem.chkIrregularVerbs, numberExercises);   
		addConstructionIfOccurs("reg", "Past", 4, visibleSettings, taskItem.chkRegularVerbs, numberExercises);
		
		return visibleSettings;
	}
}