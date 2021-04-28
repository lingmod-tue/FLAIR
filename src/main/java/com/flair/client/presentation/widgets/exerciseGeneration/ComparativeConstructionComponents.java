package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

import gwt.material.design.client.ui.MaterialCheckBox;


public class ComparativeConstructionComponents extends ConstructionComponents {

	public ComparativeConstructionComponents(TaskItem taskItem) {
		super(taskItem);
		
		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPosAdj, "adj"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkPosAdv, "adv"));
		
		ArrayList<Pair<MaterialCheckBox, String>> secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFormComparatives, "comp"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFormSuperlatives, "sup"));
		
		ArrayList<Pair<MaterialCheckBox, String>> thirdLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFormSynthetic, "syn"));
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkFormAnalytic, "ana"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
		constructionLevels.add(secondLevelConstructions);
		constructionLevels.add(thirdLevelConstructions);
	}
	
}