package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.locale.GrammaticalConstructionPanelItemLocale;
import com.flair.client.presentation.interfaces.CanReset;
import com.flair.client.presentation.interfaces.GrammaticalConstructionContainer;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;

/*
 * Represents an item in a collapsable panel
 */
public class GrammaticalConstructionPanelItem extends LocalizedComposite implements HasWidgets, HasText, CanReset, GrammaticalConstructionContainer
{
	private final MaterialCollapsibleItem		item;
	private final MaterialCollapsibleHeader		header;
	private final MaterialCollapsibleBody		body;
	private final MaterialLink					text;
	private final MaterialIcon					reset;

	private final List<Widget> 					children;
	
	private void initLocale()
	{
		registerLocale(GrammaticalConstructionPanelItemLocale.INSTANCE.en);
		registerLocale(GrammaticalConstructionPanelItemLocale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		reset.addClickHandler(e -> {
			resetState(true);
		});
	}
	
	public GrammaticalConstructionPanelItem()
	{
		super(ClientEndPoint.get().getLocalization());
		
		text = new MaterialLink();
		reset = new MaterialIcon(IconType.UNDO);
		header = new MaterialCollapsibleHeader(text, reset);
		body = new MaterialCollapsibleBody();
		item = new MaterialCollapsibleItem(header, body);
		children = new ArrayList<>();

		// setup components
		body.setPadding(15);
		text.setIconPosition(IconPosition.LEFT);
		text.setTextColor(Color.BLACK);
		
		reset.setWaves(WavesType.DEFAULT);
		reset.setCircle(true);
		reset.setFloat(Float.RIGHT);
		reset.setIconColor(Color.GREY);
		
		initWidget(item);
		
		initLocale();
		initHandlers();
	}
	
	public void setIconType(IconType ico) {
		text.setIconType(ico);
	}
	
	public Widget getWrapper() {
		return item;
	}

	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		
		// update tooltip
		reset.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionPanelItemLocale.DESC_resetTooltip));
	}

	@Override
	public void resetState(boolean fireEvents)
	{
		// reset all appropriate (direct) children
		for (Widget itr : children)
		{
			if (itr instanceof CanReset)
			{
				CanReset w = (CanReset)itr;
				w.resetState(fireEvents);
			}
		}
	}

	@Override
	public void add(Widget w)
	{
		children.add(w);
		body.add(w);
	}

	@Override
	public void clear()
	{
		children.clear();
		body.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return body.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return children.remove(w) && body.remove(w);
	}

	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public void setText(String text) {
		this.text.setText(text);
	}

	@Override
	public boolean hasConstruction(GrammaticalConstruction val) {
		return getWeightSlider(val) != null;
	}
	
	private GrammaticalConstructionWeightSlider getAsSlider(Widget w, GrammaticalConstruction gram)
	{
		if (w instanceof GrammaticalConstructionWeightSlider)
		{
			GrammaticalConstructionWeightSlider slider = (GrammaticalConstructionWeightSlider)w;
			if (gram == null || slider.getGram() == gram)
				return slider;
		}
		
		return null;
	}
	
	private GrammaticalConstructionWeightSlider lookupWeightSlider(Widget w, GrammaticalConstruction gram)
	{
		GrammaticalConstructionWeightSlider slider = getAsSlider(w, gram);
		if (slider != null)
			return slider;
		else if (w instanceof HasWidgets)
		{
			for (Widget itr : (HasWidgets)w)
			{
				slider = lookupWeightSlider(itr, gram);
				if (slider != null)
					return slider;
			}
		}
		
		return null;
	}
	
	private void doForEachWeightSlider(Widget w, ForEachHandler h)
	{
		GrammaticalConstructionWeightSlider slider = getAsSlider(w, null);
		if (slider != null)
			h.handle(slider);
		else if (w instanceof GrammaticalConstructionPanel)
		{
			GrammaticalConstructionPanel panel = (GrammaticalConstructionPanel)w;
			panel.forEachWeightSlider(h);
		}
		else if (w instanceof GrammaticalConstructionPanelItem)
		{
			GrammaticalConstructionPanelItem item = (GrammaticalConstructionPanelItem)w;
			item.forEachWeightSlider(h);
		}
		else if (w instanceof HasWidgets)
		{
			for (Widget itr : (HasWidgets)w)
				doForEachWeightSlider(itr, h);
		}
	}

	@Override
	public GrammaticalConstructionWeightSlider getWeightSlider(GrammaticalConstruction val) {
		return lookupWeightSlider(body, val);
	}

	@Override
	public void forEachWeightSlider(ForEachHandler handler) {
		doForEachWeightSlider(body, handler);
	}
}
