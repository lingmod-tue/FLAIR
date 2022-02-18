package com.flair.client.presentation.widgets;

import com.flair.client.presentation.interfaces.CanReset;
import com.flair.client.presentation.interfaces.GrammaticalConstructionContainer;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.ui.MaterialCollapsible;

import java.util.Iterator;

/*
 * Wraps around a collapsable panel that stores gram const sliders
 */
public class GrammaticalConstructionPanel extends Composite
		implements HasWidgets, CanReset, GrammaticalConstructionContainer {
	private final MaterialCollapsible panel;

	public GrammaticalConstructionPanel() {
		panel = new MaterialCollapsible();
		panel.setMargin(0);
		panel.setPadding(0);
		
		initWidget(panel);
	}

	public void setAccordion(boolean val) {
		panel.setAccordion(val);
	}

	public void setPopout(boolean val) {
		panel.setType(val ? CollapsibleType.POPOUT : CollapsibleType.FLAT);
	}

	@Override
	public boolean hasConstruction(GrammaticalConstruction val) {
		return getWeightSlider(val) != null;
	}

	@Override
	public GrammaticalConstructionWeightSlider getWeightSlider(GrammaticalConstruction val) {
		GrammaticalConstructionWeightSlider out = null;
		for (Widget w : panel) {
			if ((out = ((GrammaticalConstructionPanelItem) w).getWeightSlider(val)) != null)
				break;
		}

		return out;
	}

	@Override
	public void resetState(boolean fireEvents) {
		for (Widget w : panel)
			((CanReset) w).resetState(fireEvents);
	}

	@Override
	public void add(Widget w) {
		if (w instanceof GrammaticalConstructionPanelItem == false)
			throw new RuntimeException("Invalid panel item");

		panel.add(w);
	}

	@Override
	public void clear() {
		panel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return panel.remove(w);
	}

	@Override
	public void forEachWeightSlider(ForEachHandler handler) {
		for (Widget itr : panel) {
			GrammaticalConstructionPanelItem itm = (GrammaticalConstructionPanelItem) itr;
			itm.forEachWeightSlider(handler);
		}
	}
}
