package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;

public abstract class VisibilityManager {

	public VisibilityManager(TaskItem taskItem) {
		this.taskItem = taskItem;
	}
	
	protected final TaskItem taskItem;
	
	/**
	 * Determines all widgets that need to be displayed for this topic, exercise type and document.
	 * @return	The list of widgets that need to be visible
	 */
	public abstract ArrayList<Widget> getVisibleWidgets(int numberExercises);
	
    /**
     * Adds a widget to the visible widgets list if the corresponding construction occurs at least once in the text.
     * @param construction		The construction denoted by the widget
     * @param topic				The topic to which the construction belongs
     * @param group				The index of the construction in the detailed construction name
     * @param visibleSettings	The list of widgets to be displayed
     * @param widget			The widget
     * @param numberExercises	The number of exercises that can be generated for the current settings
     */
    protected void addConstructionIfOccurs(String construction, String topic, int group, ArrayList<Widget> visibleSettings, 
    		Widget widget, int numberExercises) {
    	int numberOccurrences = taskItem.getNumberOfConstructionOccurrences(construction, topic, group);
    	if(numberOccurrences > 0) {
			visibleSettings.add(widget);
			
			if(numberOccurrences == numberExercises) {
				if(widget instanceof MaterialCheckBox) {
					((MaterialCheckBox) widget).setEnabled(false);
				} else {
					((MaterialCheckBox) widget).setEnabled(true);
				}
			}
		}
    }
	
}