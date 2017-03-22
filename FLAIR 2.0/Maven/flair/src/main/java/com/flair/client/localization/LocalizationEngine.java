package com.flair.client.localization;

import java.util.HashSet;
import java.util.Set;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.interfaces.LocalizedUI;
import com.flair.shared.grammar.Language;

/*
 * Manages localization state
 */
public class LocalizationEngine
{
	private Language					currentLang;
	private final Set<LocalizedUI>		activeLocalizedViews;
	
	public LocalizationEngine() 
	{
		this.currentLang = Language.ENGLISH;
		this.activeLocalizedViews = new HashSet<>();
	}
	
	private void refreshActiveViews()
	{
		for (LocalizedUI itr : activeLocalizedViews)
			itr.setLocalization(currentLang);
	}
	
	public Language getLanguage() {
		return currentLang;
	}
	
	public void setLanguage(Language lang) 
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
	
	public static LocalizationEngine get() {
		return ClientEndPoint.get().getLocalization();
	}
}
