package com.flair.client.localization;

import com.google.gwt.user.client.ui.Widget;

/*
 * Localization wrapper around GWT widgets with a single localizable field
 */
public class SimpleLocalizedWidget<T> implements AbstractLocalizationWrapper
{
	public interface Updateable<T>
	{
		public void update(T widget, String localizedStr);
	}
	
	public final T					w;
	public final String				desc;
	private final Updateable<T>		invokable;
	
	public SimpleLocalizedWidget(T w, String desc, Updateable<T> invokable)
	{
		if (w instanceof Widget == false)
			throw new RuntimeException("Invalid widget class");
		
		this.w = w;
		this.desc = desc;
		this.invokable = invokable;
	}

	public SimpleLocalizedWidget(T w, String desc) {
		this(w, desc, null);
	}
	
	@Override
	public final void setLocale(LocalizationData data)
	{
		if (invokable == null)
			throw new RuntimeException("No update method specified");
		else
			invokable.update(w, data.get(desc));
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((w == null) ? 0 : w.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SimpleLocalizedWidget)) {
			return false;
		}
		SimpleLocalizedWidget other = (SimpleLocalizedWidget) obj;
		if (desc == null) {
			if (other.desc != null) {
				return false;
			}
		} else if (!desc.equals(other.desc)) {
			return false;
		}
		if (w == null) {
			if (other.w != null) {
				return false;
			}
		} else if (!w.equals(other.w)) {
			return false;
		}
		return true;
	}
}
