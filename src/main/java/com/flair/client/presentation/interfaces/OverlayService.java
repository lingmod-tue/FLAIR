package com.flair.client.presentation.interfaces;

import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.base.MaterialWidget;

/*
 * Proivdes a path-animated overlay service
 */
public interface OverlayService
{
	public MaterialWidget		getOverlay();
	
	public void					show(MaterialWidget source, Widget content);
	public void					hide();
}
