package com.flair.client.localization;

import com.flair.client.localization.interfaces.AbstractLocalizationWrapper;
import com.google.gwt.user.client.ui.Widget;

/*
 * Localization wrapper around GWT widgets with a single localizable field
 */
public class SimpleLocalizedWidget<T> implements AbstractLocalizationWrapper
{
	// autmatically resolves the localized string and passes it to the handler
	public interface UpdateableAuto<T> {
		public void update(T widget, String localizedStr);
	}
	
	// the handler fully controls how the localized string is resolved
	public interface UpdateableManual<T> {
		public void update(T widget, String desc, LocalizationData data);
	}

	public final T						w;
	public final String					desc;
	private final UpdateableAuto<T>		auto;
	private final UpdateableManual<T>	manual;
	
	public SimpleLocalizedWidget(T w, String desc, UpdateableAuto<T> auto)
	{
		if (w instanceof Widget == false)
			throw new RuntimeException("Invalid widget class");
		
		this.w = w;
		this.desc = desc;
		this.auto = auto;
		this.manual = null;
	}

	public SimpleLocalizedWidget(T w, String desc, UpdateableManual<T> manual)
	{
		if (w instanceof Widget == false)
			throw new RuntimeException("Invalid widget class");
		
		this.w = w;
		this.desc = desc;
		this.auto = null;
		this.manual = manual;
	}
	
	@Override
	public final void setLocale(LocalizationData data)
	{
		if (auto == null && manual == null)
			throw new RuntimeException("No update method specified");
		
		if (auto != null)
			auto.update(w, desc.isEmpty() ? desc : data.get(desc));
		else
			manual.update(w, desc, data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auto == null) ? 0 : auto.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((manual == null) ? 0 : manual.hashCode());
		result = prime * result + ((w == null) ? 0 : w.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof SimpleLocalizedWidget))
		{
			return false;
		}
		SimpleLocalizedWidget other = (SimpleLocalizedWidget) obj;
		if (auto == null)
		{
			if (other.auto != null)
			{
				return false;
			}
		} else if (!auto.equals(other.auto))
		{
			return false;
		}
		if (desc == null)
		{
			if (other.desc != null)
			{
				return false;
			}
		} else if (!desc.equals(other.desc))
		{
			return false;
		}
		if (manual == null)
		{
			if (other.manual != null)
			{
				return false;
			}
		} else if (!manual.equals(other.manual))
		{
			return false;
		}
		if (w == null)
		{
			if (other.w != null)
			{
				return false;
			}
		} else if (!w.equals(other.w))
		{
			return false;
		}
		return true;
	}
}
