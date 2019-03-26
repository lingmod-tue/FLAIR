package com.flair.client.presentation.interfaces;

import com.google.gwt.dom.client.Element;

/*
 * Represents a basic item displayed in the results pane
 */
public interface AbstractResultItem {
	enum Type {
		IN_PROGRESS,
		COMPLETED
	}

	enum SelectionType {
		TITLE,      // Clicked on the title text
		DEFAULT     // Clicked anywhere else
	}

	Type getType();
	String getTitle();
	boolean hasUrl();
	String getUrl();
	String getDisplayUrl();
	String getSnippet();

	void selectItem(SelectionType selectionType, Element parentWidget);

	boolean hasOverflowMenu();
	void addToCompare();
}
