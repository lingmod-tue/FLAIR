package com.flair.client.localization;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;

/*
 * Abstract base class for all localized views
 * Naming conventions for widgets: <name>{UI/LC} where:
 * 	UI - UiBinder widget instance
 * 	LC - Localization widget instance
 */
public abstract class LocalizedCompositeView extends Composite implements LocalizedUI
{
	protected final LocalizationEngine								localeCore;
	protected final Map<LocalizationLanguage, LocalizationData>		localeData;
	protected final List<AbstractLocalizationWrapper>				localizedWidgets;
	
	public LocalizedCompositeView(LocalizationEngine locale)
	{
		localeCore = locale;
		localeData = new EnumMap<>(LocalizationLanguage.class);
		localizedWidgets = new ArrayList<>();
	}
	
	@Override
	protected final void onAttach()
	{
		super.onAttach();
		localeCore.registerLocalizedView(this);
	}
	
	@Override
	protected final void onDetach()
	{
		super.onDetach();
		localeCore.deregisterLocalizedView(this);
	}
	
	protected final void registerLocale(LocalizationData data)
	{
		if (localeData.containsKey(data.getLanguage()))
			throw new RuntimeException("Locale already registered");
		else
			localeData.put(data.getLanguage(), data);
	}
	
	protected final void deregisterLocale(LocalizationLanguage lang) {
		localeData.remove(lang);
	}
	
	protected final void registerLocalizedWidget(AbstractLocalizationWrapper widget) {
		localizedWidgets.add(widget);
	}
	
	protected final void deregisterLocalizedWidget(AbstractLocalizationWrapper widget) {
		localizedWidgets.remove(widget);
	}
	
	protected final void refreshLocalization() {
		setLocalization(localeCore.getLanguage());
	}
	
	@Override
	public final LocalizationData getLocalizationData(LocalizationLanguage lang)
	{
		if (localeData.containsKey(lang) == false)
			throw new RuntimeException("Locale " + lang + " missing for view " + this.getClass().getName());
		else
			return localeData.get(lang);	
	}
	
	@Override
	public void setLocalization(LocalizationLanguage lang)
	{
		LocalizationData ldata = getLocalizationData(lang);

		// simple widget update pass
		for (AbstractLocalizationWrapper itr : localizedWidgets)
			itr.setLocale(ldata);
	}
}
