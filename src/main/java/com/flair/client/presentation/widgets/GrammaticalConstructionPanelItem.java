package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.DefaultLocalizationProviders;
import com.flair.client.localization.LocalizationBinderData;
import com.flair.client.localization.LocalizedComposite;
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
import gwt.material.design.client.ui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Represents an item in a collapsable panel
 */
public class GrammaticalConstructionPanelItem extends LocalizedComposite implements HasWidgets, HasText, CanReset, GrammaticalConstructionContainer {
	private static final int MAX_LABEL_LENGTH = 25;

	private final MaterialCollapsibleItem item;
	private final MaterialCollapsibleHeader header;
	private final MaterialCollapsibleBody body;
	private final MaterialLink text;
	private final MaterialIcon reset;

	private final List<Widget> children;

	private void initHandlers() {
		reset.addClickHandler(e -> {
			resetState(true);
			e.stopPropagation();
		});
	}

	public GrammaticalConstructionPanelItem() {
		text = new MaterialLink();
		reset = new MaterialIcon(IconType.UNDO);
		header = new MaterialCollapsibleHeader(text, reset);
		body = new MaterialCollapsibleBody();
		item = new MaterialCollapsibleItem(header, body);
		header.setMargin(0);
		header.setPadding(0);
		body.setMargin(0);
		body.setPadding(0);
		item.setMargin(0);
		item.setPadding(0);
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
		initLocale(new LocalizationBinderData("GrammaticalConstructionPanelItem"));        // ### this class doesn't need the binder, so init with dummy data
		initHandlers();
	}

	public void setIconType(IconType ico) {
		text.setIconType(ico);
	}

	public Widget getWrapper() {
		return item;
	}

	@Override
	public void setLocale(Language lang) {
		super.setLocale(lang);

		// update tooltip
		reset.setTitle(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.RESET.toString()));
	}

	@Override
	public void resetState(boolean fireEvents) {
		// reset all appropriate (direct) children
		for (Widget itr : children) {
			if (itr instanceof CanReset) {
				CanReset w = (CanReset) itr;
				w.resetState(fireEvents);
			}
		}
	}

	@Override
	public void add(Widget w) {
		children.add(w);
		body.add(w);
	}

	@Override
	public void clear() {
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
		if (text.length() > MAX_LABEL_LENGTH) {
			this.text.setTitle(text);
			text = text.substring(0, MAX_LABEL_LENGTH - 4) + "...";
		}

		this.text.setText(text);
	}

	@Override
	public boolean hasConstruction(GrammaticalConstruction val) {
		return getWeightSlider(val) != null;
	}

	private GrammaticalConstructionWeightSlider getAsSlider(Widget w, GrammaticalConstruction gram) {
		if (w instanceof GrammaticalConstructionWeightSlider) {
			GrammaticalConstructionWeightSlider slider = (GrammaticalConstructionWeightSlider) w;
			if (gram == null || slider.getGram() == gram)
				return slider;
		}

		return null;
	}

	private GrammaticalConstructionWeightSlider lookupWeightSlider(Widget w, GrammaticalConstruction gram) {
		GrammaticalConstructionWeightSlider slider = getAsSlider(w, gram);
		if (slider != null)
			return slider;
		else if (w instanceof HasWidgets) {
			for (Widget itr : (HasWidgets) w) {
				slider = lookupWeightSlider(itr, gram);
				if (slider != null)
					return slider;
			}
		}

		return null;
	}

	private void doForEachWeightSlider(Widget w, ForEachHandler h) {
		GrammaticalConstructionWeightSlider slider = getAsSlider(w, null);
		if (slider != null)
			h.handle(slider);
		else if (w instanceof GrammaticalConstructionPanel) {
			GrammaticalConstructionPanel panel = (GrammaticalConstructionPanel) w;
			panel.forEachWeightSlider(h);
		} else if (w instanceof GrammaticalConstructionPanelItem) {
			GrammaticalConstructionPanelItem item = (GrammaticalConstructionPanelItem) w;
			item.forEachWeightSlider(h);
		} else if (w instanceof HasWidgets) {
			for (Widget itr : (HasWidgets) w)
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
