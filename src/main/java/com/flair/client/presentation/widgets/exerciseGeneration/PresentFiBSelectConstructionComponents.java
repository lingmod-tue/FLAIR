package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

import gwt.material.design.client.ui.MaterialCheckBox;


public class PresentFiBSelectConstructionComponents extends ConstructionComponents {

	public PresentFiBSelectConstructionComponents(TaskItem taskItem) {
		super(taskItem);
		
		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "present"));
		
		ArrayList<Pair<MaterialCheckBox, String>> secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkQuestions, "question"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkStatements, "stmt"));
		
		ArrayList<Pair<MaterialCheckBox, String>> thirdLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkNegatedSent, "neg"));
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkAffirmativeSent, "affirm"));
		
		ArrayList<Pair<MaterialCheckBox, String>> fourthLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chk3Pers, "3"));
		fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkNot3Pers, "not3"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
		constructionLevels.add(secondLevelConstructions);
		constructionLevels.add(thirdLevelConstructions);
		constructionLevels.add(fourthLevelConstructions);
	}
	
}