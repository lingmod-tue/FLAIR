package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialCheckBox;


public class ConditionalsConstructionComponents extends ConstructionComponents {

	public ConditionalsConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkscopeType1, "condReal"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkscopeType2, "condUnreal"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
	}
	
}