package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PastFiBVisibilityManager extends VisibilityManager {

	public PastFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	public ArrayList<Widget> getVisibleWidgets() {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpBrackets);
		visibleSettings.add(taskItem.chkBracketsLemma);
		visibleSettings.add(taskItem.chkBracketsTense);
		visibleSettings.add(taskItem.grpSentenceTypes);
		addConstructionIfOccurs("affirm", "Past", 3, visibleSettings, taskItem.chkAffirmativeSent);   			
		addConstructionIfOccurs("neg", "Past", 3, visibleSettings, taskItem.chkNegatedSent);   			
		addConstructionIfOccurs("question", "Past", 2, visibleSettings, taskItem.chkQuestions);   			
		addConstructionIfOccurs("stmt", "Past", 2, visibleSettings, taskItem.chkStatements);   
		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.lblTensesWords);
		addConstructionIfOccurs("TENSE_PAST_SIMPLE", "Past", 1, visibleSettings, taskItem.chkPastSimple);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT", "Past", 1, visibleSettings, taskItem.chkPresentPerfect);
		addConstructionIfOccurs("TENSE_PAST_PERFECT", "Past", 1, visibleSettings, taskItem.chkPastPerfect);       			
		visibleSettings.add(taskItem.grpVerbForms);
		addConstructionIfOccurs("irreg", "Past", 4, visibleSettings, taskItem.chkIrregularVerbs);   
		addConstructionIfOccurs("reg", "Past", 4, visibleSettings, taskItem.chkRegularVerbs);
		
		return visibleSettings;
	}
}