package com.flair.client.localization;

import com.flair.client.localization.interfaces.LocalizableEntity;
import com.flair.client.localization.interfaces.LocalizationProvider;
import com.flair.client.localization.interfaces.LocalizedUI;
import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.ui.Composite;

/*
 * Abstract base class for all localized composite widgets/views
 * Naming conventions for widgets: <name>{UI} where:
 * 	UI - UiBinder widget instance
 */
public abstract class LocalizedComposite extends Composite implements LocalizedUI
{
	private LocalizationBinderData		localizationBinderData;
	
	public LocalizedComposite()
	{
		super();
		localizationBinderData = null;
	}
	
	@Override
	protected void onAttach()
	{
		if (localizationBinderData == null)
			throw new RuntimeException("No localization data for composite");
		
		super.onAttach();
		LocalizationEngine.get().registerLocalizedView(this);
	}
	
	@Override
	protected void onDetach()
	{
		super.onDetach();
		LocalizationEngine.get().deregisterLocalizedView(this);
	}
	
	protected void initLocale(LocalizationBinderData data) {
		localizationBinderData = data;
		refreshLocale();
	}
	
	protected String getLocalizedString(String tag) {
		return LocalizationEngine.get().getLocalizedString(localizationBinderData.providerName, tag);
	}
	
	protected String getLocalizedString(String provider, String tag) {
		return LocalizationEngine.get().getLocalizedString(provider, tag);
	}
	
	protected Language getCurrentLocale() {
		return LocalizationEngine.get().getLanguage();
	}
	
	@Override
	public LocalizationProvider getLocalizationProvider() {
		return LocalizationStringTable.get().getProvider(localizationBinderData.providerName);
	}
	
	@Override
	public void setLocale(Language lang)
	{
		for (LocalizableEntity itr : localizationBinderData.localizationWrappers)
			itr.setLocale(lang, LocalizationStringTable.get());
	}
	
	@Override
	public void refreshLocale() {
		setLocale(getCurrentLocale());
	}
}
