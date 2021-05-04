package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PassiveFiBVisibilityManager extends VisibilityManager {

	public PassiveFiBVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpBrackets);
		visibleSettings.add(taskItem.chkBracketsSentenceType);
		visibleSettings.add(taskItem.chkBracketsTense);
		visibleSettings.add(taskItem.chkBracketsActiveSentence);
		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.lblTensesSentences);
		addConstructionIfOccurs("TENSE_PRESENT_SIMPLE", "Passive", 2, visibleSettings, taskItem.chkPresentSimple, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_SIMPLE", "Passive", 2, visibleSettings, taskItem.chkFutureSimple, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPresentProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPastProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkFutureProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_PERFECT", "Passive", 2, visibleSettings, taskItem.chkFuturePerfect, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPresentPerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPastPerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_PERFECT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkFuturePerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_SIMPLE", "Passive", 2, visibleSettings, taskItem.chkPastSimple, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT", "Passive", 2, visibleSettings, taskItem.chkPresentPerfect, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT", "Passive", 2, visibleSettings, taskItem.chkPastPerfect, numberExercises);
		visibleSettings.add(taskItem.grpSentTypes);
		addConstructionIfOccurs("active", "Passive", 1, visibleSettings, taskItem.chkScopeActive, numberExercises);	
		
		return visibleSettings;
	}
}