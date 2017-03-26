package com.flair.client.localization;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.flair.client.localization.interfaces.AbstractLocalizationWrapper;
import com.flair.client.localization.interfaces.LocalizedUI;
import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.ui.Composite;

/*
 * Abstract base class for all localized composite widgets/views
 * Naming conventions for widgets: <name>{UI/LC} where:
 * 	UI - UiBinder widget instance
 * 	LC - Localization widget instance
 */
public abstract class LocalizedComposite extends Composite implements LocalizedUI
{
	protected final LocalizationEngine							localeCore;
	protected final Map<Language, LocalizationData>				localeData;
	protected final List<AbstractLocalizationWrapper>			localizedWidgets;
	
	public LocalizedComposite(LocalizationEngine locale)
	{
		localeCore = locale;
		localeData = new EnumMap<>(Language.class);
		localizedWidgets = new ArrayList<>();
	}
	
	@Override
	protected void onAttach()
	{
		super.onAttach();
		localeCore.registerLocalizedView(this);
	}
	
	@Override
	protected void onDetach()
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
	
	protected final void deregisterLocale(Language lang) {
		localeData.remove(lang);
	}
	
	protected final void registerLocalizedWidget(AbstractLocalizationWrapper widget) {
		localizedWidgets.add(widget);
	}
	
	protected final void deregisterLocalizedWidget(AbstractLocalizationWrapper widget) {
		localizedWidgets.remove(widget);
	}
	
	protected final String getLocalizedString(String desc) {
		return getLocalizationData(localeCore.getLanguage()).get(desc);
	}
	
	@Override
	public final LocalizationData getLocalizationData(Language lang)
	{
		if (localeData.containsKey(lang) == false)
			throw new RuntimeException("Locale " + lang + " missing for view " + this.getClass().getName());
		else
			return localeData.get(lang);
	}
	
	@Override
	public void setLocalization(Language lang)
	{
		LocalizationData ldata = getLocalizationData(lang);

		// simple widget update pass
		for (AbstractLocalizationWrapper itr : localizedWidgets)
			itr.setLocale(ldata);
	}
	
	public final void refreshLocalization() {
		setLocalization(localeCore.getLanguage());
	}
}
