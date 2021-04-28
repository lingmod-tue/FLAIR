package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

import gwt.material.design.client.ui.MaterialCheckBox;


public class PastMarkDragConstructionComponents extends ConstructionComponents {

	public PastMarkDragConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPastSimple, "TENSE_PAST_SIMPLE"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPresentPerfect, "TENSE_PRESENT_PERFECT"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPastPerfect, "TENSE_PAST_PERFECT"));
		
		ArrayList<Pair<MaterialCheckBox, String>> secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "question"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "stmt"));
		
		ArrayList<Pair<MaterialCheckBox, String>> thirdLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "neg"));
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "affirm"));
		
		ArrayList<Pair<MaterialCheckBox, String>> fourthLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkRegularVerbs, "reg"));
		fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkIrregularVerbs, "irreg"));

		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
		constructionLevels.add(secondLevelConstructions);
		constructionLevels.add(thirdLevelConstructions);
		constructionLevels.add(fourthLevelConstructions);
	}
	
}