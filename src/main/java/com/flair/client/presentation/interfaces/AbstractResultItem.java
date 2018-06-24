package com.flair.client.presentation.interfaces;

/*
 * Represents a basic item displayed in the results pane
 */
public interface AbstractResultItem
{
	public enum Type
	{
		IN_PROGRESS,
		COMPLETED
	}
	
	public Type				getType();
	public String			getTitle();
	public boolean			hasUrl();
	public String			getUrl();
	public String			getDisplayUrl();
	public String			getSnippet();
	
	public void				selectItem();
	
	public boolean			hasOverflowMenu();
	public void				addToCompare();
}
