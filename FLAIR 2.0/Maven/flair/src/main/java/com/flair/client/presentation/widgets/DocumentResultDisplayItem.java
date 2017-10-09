package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;

public class DocumentResultDisplayItem extends LocalizedComposite
{
	private static DocumentResultDisplayItemUiBinder uiBinder = GWT.create(DocumentResultDisplayItemUiBinder.class);

	interface DocumentResultDisplayItemUiBinder extends UiBinder<Widget, DocumentResultDisplayItem>
	{
	}
	
	private static DocumentResultDisplayItemLocalizationBinder localeBinder = GWT.create(DocumentResultDisplayItemLocalizationBinder.class);
	interface DocumentResultDisplayItemLocalizationBinder extends LocalizationBinder<DocumentResultDisplayItem> {}
	
	static enum LocalizationTags
	{
		ORIGINAL_RANK,
	}
	
	@UiField
	MaterialRow				pnlRootUI;
	@UiField
	MaterialCard			pnlCardUI;
	@UiField
	MaterialColumn			colCompletedHeaderUI;
	@UiField
	MaterialLabel			lblNewRankUI;
	@UiField
	MaterialIcon			icoRankOffsetUI;
	@UiField
	MaterialColumn			colInProgressHeaderUI;
	@UiField
	MaterialColumn			colTextUI;
	@UiField
	MaterialLink			btnTitleUI;
	@UiField
	MaterialLabel			lblURLUI;
	@UiField
	MaterialLabel			lblSnippetUI;
	@UiField
	MaterialLink			btnOverflowUI;
	@UiField
	MaterialDropDown		pnlMenuUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.COMPARE, type=LocalizedFieldType.BUTTON)
	MaterialLink			btnAddToCompareUI;

	int						orgRank;
	
	public DocumentResultDisplayItem(AbstractResultItem item)
	{
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		
		btnTitleUI.setText(item.getTitle());

		if (item.hasUrl())
		{
			btnTitleUI.addClickHandler(e -> {
				Window.open(item.getUrl(), "_blank", "");
				e.stopPropagation();
			});
		
			lblURLUI.setText(item.getDisplayUrl());
		}
		else
			lblURLUI.setVisible(false);
		
		lblSnippetUI.setText(item.getSnippet());
		colTextUI.addClickHandler(e -> item.selectItem());
		
		String guid = Document.get().createUniqueId();
		btnOverflowUI.setActivates(guid.toString());
		pnlMenuUI.setActivator(guid.toString());
		
		btnOverflowUI.setVisible(item.hasOverflowMenu());
		btnAddToCompareUI.addClickHandler(e -> item.addToCompare());
		
		if (item.getType() == Type.IN_PROGRESS)
			colCompletedHeaderUI.setVisible(false);
		else
		{
			CompletedResultItem c = (CompletedResultItem)item;
			orgRank = c.getOriginalRank();
			lblNewRankUI.setText("" + c.getCurrentRank());

			if (c.getCurrentRank() < c.getOriginalRank())
			{
				icoRankOffsetUI.setIconType(IconType.KEYBOARD_ARROW_UP);
				icoRankOffsetUI.setIconColor(Color.GREEN_ACCENT_2);
			}
			else if (c.getCurrentRank() > c.getOriginalRank())
			{
				icoRankOffsetUI.setIconType(IconType.KEYBOARD_ARROW_DOWN);
				icoRankOffsetUI.setIconColor(Color.RED_ACCENT_2);
			}
			else
			{
				icoRankOffsetUI.setIconType(IconType.TRENDING_FLAT);
				icoRankOffsetUI.setIconColor(Color.GREY_LIGHTEN_2);
			}
			
			colInProgressHeaderUI.setVisible(false);
		}
	}
	

	public void setOpacity(double o) {
		pnlCardUI.setOpacity(o);
	}
	
	@Override
	public void setLocale(Language lang)
	{
		super.setLocale(lang);
		
		String orank = getLocalizedString(LocalizationTags.ORIGINAL_RANK.toString()) + ": " + orgRank;
		icoRankOffsetUI.setTooltip(orank);
	}
}
