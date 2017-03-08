package com.flair.client.localization;

import java.util.HashSet;
import java.util.Set;

/*
 * Manages localization state
 */
public class LocalizationEngine
{
	private LocalizationLanguage			currentLang;
	private final Set<LocalizedUI>			activeLocalizedViews;
	
	public LocalizationEngine() 
	{
		this.currentLang = LocalizationLanguage.ENGLISH;
		this.activeLocalizedViews = new HashSet<>();
	}
	
	private void refreshActiveViews()
	{
		for (LocalizedUI itr : activeLocalizedViews)
			itr.setLocalization(currentLang);
	}
	
	public LocalizationLanguage getLanguage() {
		return currentLang;
	}
	
	public void setLanguage(LocalizationLanguage lang) 
	{
		if (lang != currentLang)
		{
			currentLang = lang;
			refreshActiveViews();
		}
	}
	
	public void registerLocalizedView(LocalizedUI view) {
		activeLocalizedViews.add(view);
	}
	
	public void deregisterLocalizedView(LocalizedUI view) {
		activeLocalizedViews.remove(view);
	}
}
