package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

import gwt.material.design.client.ui.MaterialCheckBox;

/**
 * Collection of detailed constructions which need to be considered per exercise type and topic when counting target occurrences.
 * Each detailed construction is made up of levels corresponding to a construction extracted in the backend.
 * If the first Pair item is a MaterialCheckBox, the construction is only taken into account for the counting if the checkbox is checked.
 * If the first Pair item is null, the construction is always taken into account.
 * @author tanja
 *
 */
public abstract class ConstructionComponents {

	public ConstructionComponents(TaskItem taskItem) {
		this.taskItem = taskItem;
	}
	
	protected final TaskItem taskItem;

	protected ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> constructionLevels;
	
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getConstructionLevels() {
		return constructionLevels;
	}
	
}