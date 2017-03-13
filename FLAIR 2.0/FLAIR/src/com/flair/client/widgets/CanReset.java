package com.flair.client.widgets;

/*
 * Implemented by widgets that support being reset to a default state
 */
public interface CanReset
{
	public void		resetState(boolean fireEvents);
}
