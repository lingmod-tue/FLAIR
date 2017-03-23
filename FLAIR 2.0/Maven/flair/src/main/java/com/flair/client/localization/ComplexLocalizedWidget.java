package com.flair.client.localization;

import java.util.Set;

import com.flair.client.localization.interfaces.AbstractLocalizationWrapper;
import com.google.gwt.dev.util.collect.HashSet;
import com.google.gwt.user.client.ui.Widget;

/*
 * Localization wrapper around GWT widgets with multiple localizable fields
 */
public class ComplexLocalizedWidget<T> implements AbstractLocalizationWrapper 
{
	public interface Updateable<T>
	{
		public void update(T widget, Iterable<String> desc, LocalizationData data);
	}
	
	public final T					w;
	public final Set<String>		desc;
	private final Updateable<T>		invokable;
	
	public ComplexLocalizedWidget(T w, Updateable<T> invokable, String... desc)
	{
		if (w instanceof Widget == false)
			throw new RuntimeException("Invalid widget class");
		
		this.w = w;
		this.desc = new HashSet<>();
		this.invokable = invokable;
		
		for (String itr : desc)
			this.desc.add(itr);
	}

	public ComplexLocalizedWidget(T w, String... desc) {
		this(w, null, desc);
	}

	@Override
	public void setLocale(LocalizationData data)
	{
		if (invokable == null)
			throw new RuntimeException("No update method specified");
		else
			invokable.update(w, desc, data);
	}

	@Override
	public int hashCode() {
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
		if (!(obj instanceof ComplexLocalizedWidget)) {
			return false;
		}
		ComplexLocalizedWidget other = (ComplexLocalizedWidget) obj;
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
