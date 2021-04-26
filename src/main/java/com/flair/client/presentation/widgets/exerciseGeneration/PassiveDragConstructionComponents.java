package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialCheckBox;


public class PassiveDragConstructionComponents extends ConstructionComponents {

	public PassiveDragConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkScopePassive, "passive"));
		
		ArrayList<Pair<MaterialCheckBox, String>> secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPresentSimple, "TENSE_PRESENT_SIMPLE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFutureSimple, "TENSE_FUTURE_SIMPLE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPresentProgressive, "TENSE_PRESENT_PROGRESSIVE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPastProgressive, "TENSE_PAST_PROGRESSIVE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFutureProgressive, "TENSE_FUTURE_PROGRESSIVE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFuturePerfect, "TENSE_FUTURE_PERFECT"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPresentPerfectProg, "TENSE_PRESENT_PERFECT_PROGRESSIVE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPastPerfectProg, "TENSE_PAST_PERFECT_PROGRESSIVE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFuturePerfectProg, "TENSE_FUTURE_PERFECT_PROGRESSIVE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPastSimple, "TENSE_PAST_SIMPLE"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPresentPerfect, "TENSE_PRESENT_PERFECT"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPastPerfect, "TENSE_PAST_PERFECT"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
		constructionLevels.add(secondLevelConstructions);		
	}

}