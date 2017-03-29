package com.flair.client.localization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.interfaces.LocalizedUI;
import com.flair.shared.grammar.Language;

/*
 * Manages localization state
 */
public class LocalizationEngine
{
	public interface LanguageChangeHandler {
		public void handle(Language newLang);
	}
	
	private Language							currentLang;
	private final Set<LocalizedUI>				activeLocalizedViews;
	private final List<LanguageChangeHandler>	langChangeListeners;
	
	public LocalizationEngine()
	{
		this.currentLang = Language.ENGLISH;
		this.activeLocalizedViews = new HashSet<>();
		this.langChangeListeners = new ArrayList<>();
	}
	
	private void refreshActiveViews()
	{
		for (LocalizedUI itr : activeLocalizedViews)
			itr.setLocalization(currentLang);
	}
	
	private void notifyListeners()
	{
		for (LanguageChangeHandler itr : langChangeListeners)
			itr.handle(currentLang);
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
			notifyListeners();
		}
	}
	
	public void registerLocalizedView(LocalizedUI view) {
		activeLocalizedViews.add(view);
	}
	
	public void deregisterLocalizedView(LocalizedUI view) {
		activeLocalizedViews.remove(view);
	}
	
	public void addLanguageChangeHandler(LanguageChangeHandler handler) {
		langChangeListeners.add(handler);
	}
	
	public static LocalizationEngine get() {
		return ClientEndPoint.get().getLocalization();
	}
}
