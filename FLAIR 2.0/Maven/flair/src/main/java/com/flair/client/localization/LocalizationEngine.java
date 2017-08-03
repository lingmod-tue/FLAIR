package com.flair.client.localization;

import java.util.HashSet;
import java.util.Set;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.interfaces.LocalizedUI;
import com.flair.shared.grammar.Language;
import com.flair.shared.utilities.GenericEventSource;

/*
 * Manages localization state
 */
public class LocalizationEngine
{
	public static class LanguageChanged
	{
		public Language newLang;
	}
	
	private Language									currentLang;
	private final Set<LocalizedUI>						activeLocalizedViews;
	private final GenericEventSource<LanguageChanged>	langChangeListeners;
	
	public LocalizationEngine()
	{
		this.currentLang = Language.ENGLISH;
		this.activeLocalizedViews = new HashSet<>();
		this.langChangeListeners = new GenericEventSource<>();
	}
	
	private void refreshActiveViews()
	{
		for (LocalizedUI itr : activeLocalizedViews)
			itr.setLocalization(currentLang);
	}
	
	private void notifyLanguageChange()
	{
		LanguageChanged e = new LanguageChanged();
		e.newLang = currentLang;
		langChangeListeners.raiseEvent(e);
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
			notifyLanguageChange();
		}
	}
	
	public void registerLocalizedView(LocalizedUI view) {
		activeLocalizedViews.add(view);
	}
	
	public void deregisterLocalizedView(LocalizedUI view) {
		activeLocalizedViews.remove(view);
	}
	
	public void addLanguageChangeHandler(GenericEventSource.EventHandler<LanguageChanged> handler) {
		langChangeListeners.addHandler(handler);
	}
	
	public static LocalizationEngine get() {
		return ClientEndPoint.get().getLocalization();
	}
}
