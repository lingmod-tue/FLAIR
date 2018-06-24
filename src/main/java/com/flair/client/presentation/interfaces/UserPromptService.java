package com.flair.client.presentation.interfaces;

/*
 * Provides an interface to prompt input from the user
 */
public interface UserPromptService
{
	public interface YesHandler {
		public void handle();
	}
	
	public interface NoHandler {
		public void handle();
	}
	
	public void					yesNo(String title, String desc, YesHandler yes, NoHandler no);
}
