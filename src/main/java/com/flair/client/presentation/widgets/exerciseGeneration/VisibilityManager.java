package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public abstract class VisibilityManager {

	public VisibilityManager(TaskItem taskItem) {
		this.taskItem = taskItem;
	}
	
	protected final TaskItem taskItem;
	
	/**
	 * Determines all widgets that need to be displayed for this topic, exercise type and document.
	 * @return	The list of widgets that need to be visible
	 */
	public abstract ArrayList<Widget> getVisibleWidgets();
	
    /**
     * Adds a widget to the visible widgets list if the corresponding construction occurs at least once in the text.
     * @param construction		The construction denoted by the widget
     * @param topic				The topic to which the construction belongs
     * @param group				The index of the construction in the detailed construction name
     * @param visibleSettings	The list of widgets to be displayed
     * @param widget			The widget
     */
    protected void addConstructionIfOccurs(String construction, String topic, int group, ArrayList<Widget> visibleSettings, Widget widget) {
    	if(checkConstructionOccurs(construction, topic, group)) {
			visibleSettings.add(widget);
		}
    }
    
    /**
     * Checks for a construction whether it occurs in the document.
     * @param constructionName	The name of the construction
     * @param topic				The topic to which the construction belongs
     * @param group				The index (in terms of first, second, third or fourth part) of the construction in the name
     * @return					<code>true</code> if the construction occurs in the text; otherwise <code>false</code>
     */
    private boolean checkConstructionOccurs(String constructionName, String topic, int group) {
    	int numberOccurrences = 0;
    	
		for(String name : taskItem.getPartConstructionNames(topic, constructionName, group)) {
			numberOccurrences += taskItem.relevantConstructionsInEntireDocument.get(name);
		}

		return numberOccurrences > 0;
    }
	
}