package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

import gwt.material.design.client.ui.MaterialCheckBox;


public class RelativesFiBMarkConstructionComponents extends ConstructionComponents {

	public RelativesFiBMarkConstructionComponents(TaskItem taskItem) {
		super(taskItem);

		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();			
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkWho, "who"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkWhich, "which"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkThat, "that"));
		firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(this.taskItem.chkOtherRelPron, "otherRelPron"));
		
		constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>(); 
		constructionLevels.add(firstLevelConstructions);
		
	}
	
}