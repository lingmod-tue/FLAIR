package com.flair.client.presentation.interfaces;

/*
 * Displays a notification to the user
 */
public interface NotificationService
{
	public void		notify(String text);
	public void		notify(String text, int timeout);
}
