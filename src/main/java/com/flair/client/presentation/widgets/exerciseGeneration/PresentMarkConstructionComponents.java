package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialCheckBox;


public class PresentMarkConstructionComponents extends ConstructionComponents {

	public PresentMarkConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "present"));
		
		ArrayList<Pair<MaterialCheckBox, String>> secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "question"));
		secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "stmt"));
		
		ArrayList<Pair<MaterialCheckBox, String>> thirdLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "neg"));
		thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "affirm"));

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