package com.flair.client.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.constants.PanelType;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocale;
import com.flair.client.localization.locale.GrammaticalConstructionSliderPanelLocale;
import com.flair.client.widgets.interfaces.CanReset;
import com.flair.client.widgets.interfaces.GrammaticalConstructionContainer;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/*
 * Container panel for weight sliders 
 */
public class GrammaticalConstructionSliderPanel extends LocalizedComposite implements HasWidgets, HasText, CanReset, GrammaticalConstructionContainer
{
	private static GrammaticalConstructionSliderPanelUiBinder uiBinder = GWT.create(GrammaticalConstructionSliderPanelUiBinder.class);

	interface GrammaticalConstructionSliderPanelUiBinder extends UiBinder<Widget, GrammaticalConstructionSliderPanel>
	{
	}

	@UiField
	Panel			pnlWrapperUI;
	@UiField
	PanelHeader		pnlHeaderUI;
	@UiField
	Anchor			btnHeaderTextUI;
	@UiField
	Anchor			btnResetUI;
	@UiField
	PanelBody		pnlBodyUI;
	@UiField
	PanelCollapse	pnlCollapseUI;
	
	private final List<Widget> children;
	
	private void initLocale()
	{
		registerLocale(GrammaticalConstructionSliderPanelLocale.INSTANCE.en);
		registerLocale(GrammaticalConstructionSliderPanelLocale.INSTANCE.de);
		
		refreshLocalization();
	}	
	
	public GrammaticalConstructionSliderPanel()
	{
		super(ClientEndPoint.get().getLocalization());
		children = new ArrayList<>();
		
		initWidget(uiBinder.createAndBindUi(this));
		
		initLocale();
		
		// setup components
		btnHeaderTextUI.setDataTargetWidget(pnlCollapseUI);
	}
	
	public void setType(PanelType type) {
		pnlWrapperUI.setType(type);
	}

	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		
		// update tooltip
		btnResetUI.setTitle(getLocalizationData(lang).get(GrammaticalConstructionSliderPanelLocale.DESC_resetTooltip));
	}

	@Override
	public void resetState(boolean fireEvents) 
	{
		// reset all appropriate children
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
		pnlBodyUI.add(w);
	}

	@Override
	public void clear() 
	{
		children.clear();
		pnlBodyUI.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return children.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return children.remove(w) && pnlBodyUI.remove(w);
	}

	@Override
	public String getText() {
		return btnHeaderTextUI.getText();
	}

	@Override
	public void setText(String text) {
		btnHeaderTextUI.setText(text);
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
			if (slider.getGram() == gram)
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
				slider = getAsSlider(itr, gram);
				if (slider != null)
					return slider;
			}
		}
		
		return null;
	}

	@Override
	public GrammaticalConstructionWeightSlider getWeightSlider(GrammaticalConstruction val) {
		return lookupWeightSlider(pnlBodyUI, val);
	}
}
