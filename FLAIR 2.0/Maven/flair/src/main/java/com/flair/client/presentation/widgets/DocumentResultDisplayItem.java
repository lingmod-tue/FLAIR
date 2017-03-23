package com.flair.client.presentation.widgets;

import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

public class DocumentResultDisplayItem extends Composite
{
	private static DocumentResultDisplayItemUiBinder uiBinder = GWT.create(DocumentResultDisplayItemUiBinder.class);

	interface DocumentResultDisplayItemUiBinder extends UiBinder<Widget, DocumentResultDisplayItem>
	{
	}
	
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
	MaterialLink			btnTitleUI;
	@UiField
	MaterialLabel			lblURLUI;
	@UiField
	MaterialLabel			lblSnippetUI;

	@UiConstructor
	public DocumentResultDisplayItem(AbstractResultItem item, ClickHandler selectHandler)
	{
		initWidget(uiBinder.createAndBindUi(this));
		
		btnTitleUI.setText(item.getTitle());

		if (item.hasUrl())
		{
			btnTitleUI.addClickHandler(e -> {
				Window.open(item.getUrl(), "_blank", "");
			});
		
			lblURLUI.setText(item.getDisplayUrl());
		}
		else
			lblURLUI.setVisible(false);
		
		lblSnippetUI.setText(item.getSnippet());
		lblSnippetUI.addClickHandler(selectHandler);
		
		if (item.getType() == Type.IN_PROGRESS)
			colCompletedHeaderUI.setVisible(false);
		else
		{
			CompletedResultItem c = (CompletedResultItem)item;
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
	
	public Widget getWidget() {
		return this;
	}
}
