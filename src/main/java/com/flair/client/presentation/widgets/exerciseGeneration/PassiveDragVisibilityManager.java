package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class PassiveDragVisibilityManager extends VisibilityManager {

	public PassiveDragVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	public ArrayList<Widget> getVisibleWidgets() {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
		
		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.lblTensesSentences);
		addConstructionIfOccurs("TENSE_PRESENT_SIMPLE", "Passive", 2, visibleSettings, taskItem.chkPresentSimple);
		addConstructionIfOccurs("TENSE_FUTURE_SIMPLE", "Passive", 2, visibleSettings, taskItem.chkFutureSimple);
		addConstructionIfOccurs("TENSE_PRESENT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPresentProgressive);
		addConstructionIfOccurs("TENSE_PAST_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPastProgressive);
		addConstructionIfOccurs("TENSE_FUTURE_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkFutureProgressive);
		addConstructionIfOccurs("TENSE_FUTURE_PERFECT", "Passive", 2, visibleSettings, taskItem.chkFuturePerfect);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPresentPerfectProg);
		addConstructionIfOccurs("TENSE_PAST_PERFECT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkPastPerfectProg);
		addConstructionIfOccurs("TENSE_FUTURE_PERFECT_PROGRESSIVE", "Passive", 2, visibleSettings, taskItem.chkFuturePerfectProg);
		addConstructionIfOccurs("TENSE_PAST_SIMPLE", "Passive", 2, visibleSettings, taskItem.chkPastSimple);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT", "Passive", 2, visibleSettings, taskItem.chkPresentPerfect);
		addConstructionIfOccurs("TENSE_PAST_PERFECT", "Passive", 2, visibleSettings, taskItem.chkPastPerfect);   			
		visibleSettings.add(taskItem.grpVerbSplitting);
		
		return visibleSettings;
	}
}