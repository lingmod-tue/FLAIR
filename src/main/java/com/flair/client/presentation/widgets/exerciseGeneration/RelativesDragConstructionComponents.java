package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialCheckBox;


public class RelativesDragConstructionComponents extends ConstructionComponents {

	public RelativesDragConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "who"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "which"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "that"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "otherRelPron"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
	}
	
}