package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialCheckBox;

/**
 * Collection of detailed constructions which need to be considered per exercise type and topic when counting target occurrences.
 * Each detailed construction is made up of levels corresponding to a construction extracted in the backend.
 * If the first Pair item is a MaterialCheckBox, the construction is only taken into account for the counting if the checkbox is checked.
 * If the first Pair item is null, the construction is always taken into account.
 * @author tanja
 *
 */
public class ConstructionComponentsCollection {

	public ConstructionComponentsCollection(TaskItem taskItem) {		
		comparativeComponents = new ComparativeConstructionComponents(taskItem);
		conditionalComponents = new ConditionalsConstructionComponents(taskItem);
		passiveDragComponents = new PassiveDragConstructionComponents(taskItem);
		passiveFiBComponents = new PassiveFiBConstructionComponents(taskItem);
		pastFiBSelectComponents = new PastFiBSelectConstructionComponents(taskItem);
		pastMarkDragComponents = new PastMarkDragConstructionComponents(taskItem);
		presentFiBSelectComponents = new PresentFiBSelectConstructionComponents(taskItem);
		presentMarkComponents = new PresentMarkConstructionComponents(taskItem);
		relativesDragComponents = new RelativesDragConstructionComponents(taskItem);
		relativesFiBMarkComponents = new RelativesFiBMarkConstructionComponents(taskItem);
		relativesSelectComponents = new RelativesSelectConstructionComponents(taskItem);
	}
	
	private final ComparativeConstructionComponents comparativeComponents;
	private final ConditionalsConstructionComponents conditionalComponents;
	private final PassiveDragConstructionComponents passiveDragComponents;
	private final PassiveFiBConstructionComponents passiveFiBComponents;
	private final PastFiBSelectConstructionComponents pastFiBSelectComponents;
	private final PastMarkDragConstructionComponents pastMarkDragComponents;
	private final PresentFiBSelectConstructionComponents presentFiBSelectComponents;
	private final PresentMarkConstructionComponents presentMarkComponents;
	private final RelativesDragConstructionComponents relativesDragComponents;
	private final RelativesFiBMarkConstructionComponents relativesFiBMarkComponents;
	private final RelativesSelectConstructionComponents relativesSelectComponents;

	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getComparativeComponents() {
		return comparativeComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getConditionalComponents() {
		return conditionalComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getPassiveDragComponents() {
		return passiveDragComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getPassiveFiBComponents() {
		return passiveFiBComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getPastFiBSelectComponents() {
		return pastFiBSelectComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getPastMarkDragComponents() {
		return pastMarkDragComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getPresentFiBSelectComponents() {
		return presentFiBSelectComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getPresentMarkComponents() {
		return presentMarkComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getRelativesDragComponents() {
		return relativesDragComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getRelativesFiBMarkComponents() {
		return relativesFiBMarkComponents.getConstructionLevels();
	}
	public ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> getRelativesSelectComponents() {
		return relativesSelectComponents.getConstructionLevels();
	}
	
}