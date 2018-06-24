package com.flair.client.localization.wrappers;

import com.flair.client.localization.interfaces.LocalizableEntity;
import com.flair.client.localization.interfaces.LocalizationDataCache;
import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.ui.Widget;

/*
 * Localization wrapper around GWT widgets with a single localizable field
 */
public class SimpleLocalizedWidget<T> implements LocalizableEntity
{
	// autmatically resolves the localized string and passes it to the handler
	public interface UpdateableAuto<T> {
		public void update(T widget, String localizedStr);
	}
	
	// the handler fully controls how the localized string is resolved
	public interface UpdateableManual<T> {
		public void update(T widget, String provider, String tag, Language lang, LocalizationDataCache data);
	}

	public final T						w;
	public final String					provider;
	public final String					tag;
	private final UpdateableAuto<T>		auto;
	private final UpdateableManual<T>	manual;
	
	public SimpleLocalizedWidget(T w, String provider, String tag, UpdateableAuto<T> auto)
	{
		if (w instanceof Widget == false)
			throw new RuntimeException("Invalid widget class");
		else if (provider.isEmpty() || tag.isEmpty())
			throw new RuntimeException("Invalid localization provider/tag");
		
		this.w = w;
		this.provider = provider;
		this.tag = tag;
		this.auto = auto;
		this.manual = null;
	}

	public SimpleLocalizedWidget(T w, String provider, String tag, UpdateableManual<T> manual)
	{
		if (w instanceof Widget == false)
			throw new RuntimeException("Invalid widget class");
		else if (provider.isEmpty() || tag.isEmpty())
			throw new RuntimeException("Invalid localization provider/tag");
		
		this.w = w;
		this.provider = provider;
		this.tag = tag;
		this.auto = null;
		this.manual = manual;
	}
	
	@Override
	public final void setLocale(Language lang, LocalizationDataCache data)
	{
		if (auto == null && manual == null)
			throw new RuntimeException("No update method specified");
		
		if (auto != null)
			auto.update(w, data.getLocalizedString(provider, tag, lang));
		else
			manual.update(w, provider, tag, lang, data);
	}
}
