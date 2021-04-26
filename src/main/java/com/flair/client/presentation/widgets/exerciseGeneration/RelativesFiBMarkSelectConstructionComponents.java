package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialCheckBox;


public class RelativesFiBMarkSelectConstructionComponents extends ConstructionComponents {

	public RelativesFiBMarkSelectConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkScopePassive, "who"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkScopePassive, "which"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkScopePassive, "that"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkScopePassive, "otherRelPron"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
		
	}
	
}