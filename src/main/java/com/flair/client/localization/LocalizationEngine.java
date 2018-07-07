package com.flair.client.localization;

import com.flair.client.localization.interfaces.LocalizedUI;
import com.flair.shared.grammar.Language;
import com.flair.shared.utilities.GenericEventSource;

import java.util.HashSet;
import java.util.Set;

/*
 * Manages the locale state of active views
 */
public class LocalizationEngine {
	public static class LanguageChanged {
		public Language newLang;
	}

	private static final LocalizationEngine INSTANCE = new LocalizationEngine();
	public static LocalizationEngine get() {
		return INSTANCE;
	}

	private Language currentLang;
	private final Set<LocalizedUI> activeLocalizedViews;
	private final GenericEventSource<LanguageChanged> langChangeListeners;

	private LocalizationEngine() {
		this.currentLang = Language.ENGLISH;
		this.activeLocalizedViews = new HashSet<>();
		this.langChangeListeners = new GenericEventSource<>();
	}

	private void refreshActiveViews() {
		for (LocalizedUI itr : activeLocalizedViews)
			itr.setLocale(currentLang);
	}

	private void notifyLanguageChange() {
		LanguageChanged e = new LanguageChanged();
		e.newLang = currentLang;
		langChangeListeners.raiseEvent(e);
	}

	public Language getLanguage() {
		return currentLang;
	}

	public void setLanguage(Language lang) {
		if (lang != currentLang) {
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

	public String getLocalizedString(String provider, String tag) {
		return LocalizationStringTable.get().getLocalizedString(provider, tag, currentLang);
	}
}
