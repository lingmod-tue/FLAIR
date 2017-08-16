package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.SimpleLocalizedTooltipWidget;
import com.flair.client.localization.SimpleLocalizedWidget;
import com.flair.client.localization.locale.DocumentComparerLocale;
import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.presentation.interfaces.DocumentCompareService;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableDocument;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.addins.client.subheader.MaterialSubHeader;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class DocumentComparer extends LocalizedComposite implements DocumentCompareService
{
	private static final double					COMPARE_FAB_BOTTOM = 25;
	
	private static DocumentComparerUiBinder uiBinder = GWT.create(DocumentComparerUiBinder.class);

	interface DocumentComparerUiBinder extends UiBinder<Widget, DocumentComparer>
	{
	}

	@UiField
	MaterialModal			mdlRootUI;
	@UiField
	MaterialLabel			lblTitleUI;
	@UiField
	MaterialIcon			btnClearSelectionUI;
	@UiField
	MaterialRow				pnlListContainerUI;
	@UiField
	MaterialSubHeader		lblSelCountUI;
	@UiField
	MaterialCollection		pnlSelectionUI;
	@UiField
	MaterialEmptyState		lblPlaceholderUI;
	@UiField
	MaterialButton			btnCompareUI;
	@UiField
	MaterialButton			btnCancelUI;
	@UiField
	MaterialFAB				fabCompareUI;
	@UiField
	MaterialButton			btnFabContentUI;

	SimpleLocalizedTextWidget<MaterialLabel>			lblTitleLC;
	SimpleLocalizedTooltipWidget<MaterialIcon>			btnClearSelectionLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnCompareLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnCancelLC;
	SimpleLocalizedWidget<MaterialEmptyState>			lblPlaceholderLC;
	SimpleLocalizedTooltipWidget<MaterialButton>		btnFabContentLC;
	
	Language										activeLang;
	EnumMap<Language, List<RankableDocument>>		selection;
	DocumentCompareService.CompareHandler			compareHandler;
	
	private void showCompareFAB(boolean visible)
	{
		if (visible)
			fabCompareUI.setBottom(COMPARE_FAB_BOTTOM);
		else
			fabCompareUI.setBottom(-100);
	}
	
	private void reloadUI()
	{
		pnlSelectionUI.clear();
		
		List<RankableDocument> docs = selection.get(activeLang);
		
		if (docs.isEmpty())
		{
			lblPlaceholderUI.setVisible(true);
			pnlListContainerUI.setVisible(false);
			btnCompareUI.setEnabled(false);
		}
		else
		{
			lblPlaceholderUI.setVisible(false);
			pnlListContainerUI.setVisible(true);
			btnCompareUI.setEnabled(true);
		}
		
		lblSelCountUI.setText(docs.size() + " " + getLocalizedString(DocumentComparerLocale.DESC_ResultCount));
		for (RankableDocument itr : docs)
		{
			MaterialCollectionItem wrapper = new MaterialCollectionItem();
			MaterialLabel title = new MaterialLabel(itr.getTitle());
			title.setFontSize(1.15, Unit.EM);
			MaterialLabel url = new MaterialLabel(itr.getDisplayUrl(), Color.BLUE);
			url.setFontSize(0.8, Unit.EM);
			MaterialCollectionSecondary buttonCont = new MaterialCollectionSecondary();
			MaterialIcon delButton = new MaterialIcon(IconType.DELETE);
			delButton.setTextColor(Color.RED);
			delButton.addClickHandler(e -> {
				removeFromSelection(itr);
				reloadUI();
			});
			delButton.setFloat(Float.RIGHT);
			buttonCont.add(delButton);
			wrapper.add(title);
			wrapper.add(url);
			wrapper.add(buttonCont);
			
			pnlSelectionUI.add(wrapper);
		}
	}
	
	private void onCompare()
	{
		if (compareHandler != null)
			compareHandler.handle(activeLang, selection.get(activeLang));
		
		hide();
	}

	private void onCancel()
	{
		hide();
	}

	private void initLocale()
	{
		lblTitleLC = new SimpleLocalizedTextWidget<>(lblTitleUI, DocumentComparerLocale.DESC_lblTitleUI);
		btnClearSelectionLC = new SimpleLocalizedTooltipWidget<>(btnClearSelectionUI, DocumentComparerLocale.DESC_btnClearSelectionUI);
		btnCompareLC = new SimpleLocalizedTextButtonWidget<>(btnCompareUI, DocumentComparerLocale.DESC_btnCompareUI);
		btnCancelLC = new SimpleLocalizedTextButtonWidget<>(btnCancelUI, DocumentComparerLocale.DESC_btnCancelUI);
		lblPlaceholderLC = new SimpleLocalizedWidget<>(lblPlaceholderUI, DocumentComparerLocale.DESC_NotifyEmpty, (w,s) -> w.setTitle(s));
		btnFabContentLC = new SimpleLocalizedTooltipWidget<>(btnFabContentUI, DocumentComparerLocale.DESC_lblTitleUI);
		
		registerLocale(DocumentComparerLocale.INSTANCE.en);
		registerLocale(DocumentComparerLocale.INSTANCE.de);

		registerLocalizedWidget(lblTitleLC);
		registerLocalizedWidget(btnClearSelectionLC);
		registerLocalizedWidget(btnCompareLC);
		registerLocalizedWidget(btnCancelLC);
		registerLocalizedWidget(lblPlaceholderLC);
		registerLocalizedWidget(btnFabContentLC);

		refreshLocalization();
	}

	private void initHandlers()
	{
		btnCompareUI.addClickHandler(e -> onCompare());
		btnCancelUI.addClickHandler(e -> onCancel());
		btnClearSelectionUI.addClickHandler(e -> {
			clearSelection(activeLang);
			reloadUI();
			hide();
		});
		
		btnFabContentUI.addClickHandler(e -> show());
	}
	
	private void initUI()
	{
		btnFabContentUI.setTooltipPosition(Position.TOP);
		
		MaterialAnimation pulse = new MaterialAnimation();
		pulse.setTransition(Transition.PULSE);
		pulse.setDelay(10);
		pulse.setDuration(2000);
		pulse.setInfinite(true);
		pulse.animate(btnFabContentUI);
	}

	public DocumentComparer()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));

		activeLang = Language.ENGLISH;
		selection = new EnumMap<>(Language.class);
		for (Language itr : Language.values())
			selection.put(itr, new ArrayList<>());
		compareHandler = null;

		initLocale();
		initHandlers();
		initUI();
	}

	private boolean docEquality(RankableDocument a, RankableDocument b)
	{
		if (a.getDisplayUrl().length() != 0 && b.getDisplayUrl().length() != 0)
			return a.getDisplayUrl().equalsIgnoreCase(b.getDisplayUrl());
		else
			return a.getTitle().equalsIgnoreCase(b.getTitle()) && a.getSnippet().equalsIgnoreCase(b.getSnippet());
	}
	
	private void onSelectionChange(Language lang, int selCount) {
		showCompareFAB(selCount != 0);
	}

	public void show()
	{
		reloadUI();
		mdlRootUI.open();
	}

	public void hide() {
		mdlRootUI.close();
	}

	@Override
	public void addToSelection(RankableDocument doc)
	{
		if (isInSelection(doc))
			return;
		
		List<RankableDocument> list = selection.get(doc.getLanguage());
		list.add(doc);
		onSelectionChange(doc.getLanguage(), list.size());
	}

	@Override
	public void removeFromSelection(RankableDocument doc)
	{
		int i = 0;
		List<RankableDocument> list = selection.get(doc.getLanguage());
		for (RankableDocument itr : list)
		{
			if (docEquality(doc, itr))
			{
				list.remove(i);
				onSelectionChange(doc.getLanguage(), list.size());
				return;
			}
			
			i++;
		}
	}

	@Override
	public boolean isInSelection(RankableDocument doc)
	{
		for (RankableDocument itr : selection.get(doc.getLanguage()))
		{
			if (docEquality(doc, itr))
				return true;
		}
		
		return false;
	}

	@Override
	public void clearSelection(Language lang)
	{
		List<RankableDocument> list = selection.get(lang);
		list.clear();
		
		onSelectionChange(lang, list.size());
	}

	@Override
	public int getSelectionCount(Language lang) {
		return selection.get(lang).size();
	}
	
	@Override
	public void setCompareHandler(CompareHandler handler) {
		compareHandler = handler;
	}

	@Override
	public void bindToWebRankerCore(AbstractWebRankerCore core)
	{
		// deferred binding due to cyclic dependencies
		core.addBeginOperationHandler(o -> {
			hide();
			showCompareFAB(false);
		});
		core.addEndOperationHandler(o -> {
			if (o.success)
			{
				activeLang = o.lang;
				onSelectionChange(activeLang, getSelectionCount(activeLang));
			}
		});
	}

}
