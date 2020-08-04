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
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class DocumentResultDisplayItem extends LocalizedComposite {
	private static DocumentResultDisplayItemUiBinder uiBinder = GWT.create(DocumentResultDisplayItemUiBinder.class);

	interface DocumentResultDisplayItemUiBinder extends UiBinder<Widget, DocumentResultDisplayItem> {
	}

	private static DocumentResultDisplayItemLocalizationBinder localeBinder = GWT.create(DocumentResultDisplayItemLocalizationBinder.class);

	interface DocumentResultDisplayItemLocalizationBinder extends LocalizationBinder<DocumentResultDisplayItem> {}

	static enum LocalizationTags {
		ORIGINAL_RANK,
	}

	@UiField
	MaterialRow pnlRootUI;
	@UiField
	MaterialCard pnlCardUI;
	@UiField
	MaterialColumn colCompletedHeaderUI;
	@UiField
	MaterialLabel lblNewRankUI;
	@UiField
	MaterialIcon icoRankOffsetUI;
	@UiField
	MaterialColumn colInProgressHeaderUI;
	@UiField
	MaterialColumn colTextUI;
	@UiField
	MaterialLabel btnTitleUI;
	@UiField
	MaterialLink lblURLUI;
	@UiField
	MaterialLabel lblSnippetUI;
	@UiField
	MaterialLink btnOverflowUI;
	@UiField
	MaterialDropDown pnlMenuUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.COMPARE, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink btnAddToCompareUI;

	int orgRank;
	boolean selected;
	MaterialAnimation selectionAnimation;

	public DocumentResultDisplayItem(AbstractResultItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		btnTitleUI.setText(item.getTitle());

		if (item.hasUrl())
		{
			lblURLUI.addClickHandler(e -> {
				item.selectItem(AbstractResultItem.SelectionType.URL, this.getElement());
				e.stopPropagation();
			});

			lblURLUI.setText(item.getDisplayUrl());
		}
		else
			lblURLUI.setVisible(false);

		btnTitleUI.addClickHandler(e -> {
			item.selectItem(AbstractResultItem.SelectionType.TITLE, this.getElement());
			e.stopPropagation();
		});

		lblSnippetUI.setText(item.getSnippet());

		lblSnippetUI.getElement().getStyle().setCursor(Style.Cursor.POINTER);
		btnTitleUI.getElement().getStyle().setCursor(Style.Cursor.POINTER);

		colTextUI.addClickHandler(e -> item.selectItem(AbstractResultItem.SelectionType.DEFAULT, this.getElement()));

		String guid = Document.get().createUniqueId();
		btnOverflowUI.setActivates(guid);
		pnlMenuUI.setActivator(guid);

		btnOverflowUI.setVisible(item.hasOverflowMenu());
		btnAddToCompareUI.addClickHandler(e -> item.addToCompare());

		if (item.getType() == Type.IN_PROGRESS)
			colCompletedHeaderUI.setVisible(false);
		else {
			CompletedResultItem c = (CompletedResultItem) item;
			orgRank = c.getOriginalRank();
			lblNewRankUI.setText("" + c.getCurrentRank());

			if (c.getCurrentRank() < c.getOriginalRank()) {
				icoRankOffsetUI.setIconType(IconType.KEYBOARD_ARROW_UP);
				icoRankOffsetUI.setIconColor(Color.GREEN_ACCENT_2);
			} else if (c.getCurrentRank() > c.getOriginalRank()) {
				icoRankOffsetUI.setIconType(IconType.KEYBOARD_ARROW_DOWN);
				icoRankOffsetUI.setIconColor(Color.RED_ACCENT_2);
			} else {
				icoRankOffsetUI.setIconType(IconType.TRENDING_FLAT);
				icoRankOffsetUI.setIconColor(Color.GREY_LIGHTEN_2);
			}

			colInProgressHeaderUI.setVisible(false);
		}

		selectionAnimation = new MaterialAnimation(pnlCardUI);
		selectionAnimation.transition(Transition.PULSE).duration(1000).delay(100).infinite(true);

		setSelected(false);
	}

	private void setShadow(int val) {
		pnlCardUI.setShadow(val);
	}

	private void setInvertColors(boolean val) {
		if (val) {
			pnlCardUI.setBackgroundColor(Color.ORANGE_LIGHTEN_2);
			btnTitleUI.setTextColor(Color.WHITE);
			lblURLUI.setTextColor(Color.WHITE);
			lblSnippetUI.setTextColor(Color.WHITE);
			lblNewRankUI.setTextColor(Color.WHITE);
			btnOverflowUI.setTextColor(Color.WHITE);
		} else {
			pnlCardUI.setBackgroundColor(Color.WHITE);
			btnTitleUI.setTextColor(Color.BLACK);
			lblURLUI.setTextColor(Color.BLUE);
			lblSnippetUI.setTextColor(Color.BLACK);
			lblNewRankUI.setTextColor(Color.BLACK);
			btnOverflowUI.setTextColor(Color.GREY);
		}
	}

	public void setSelected(boolean val) {
		selected = val;
		if (selected) {
			setInvertColors(true);
			setShadow(3);
		} else {
			setInvertColors(false);
			setShadow(1);
		}
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setLocale(Language lang) {
		super.setLocale(lang);

		String orank = getLocalizedString(LocalizationTags.ORIGINAL_RANK.toString()) + ": " + orgRank;
		icoRankOffsetUI.setTooltip(orank);
	}
}
