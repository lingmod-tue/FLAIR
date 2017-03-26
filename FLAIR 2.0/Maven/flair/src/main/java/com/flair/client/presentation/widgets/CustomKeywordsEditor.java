package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.List;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedWidget;
import com.flair.client.localization.locale.CustomKeywordsEditorLocale;
import com.flair.client.presentation.interfaces.CustomKeywordService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;

public class CustomKeywordsEditor extends LocalizedComposite implements CustomKeywordService
{

	private static CustomKeywordsEditorUiBinder uiBinder = GWT.create(CustomKeywordsEditorUiBinder.class);

	interface CustomKeywordsEditorUiBinder extends UiBinder<Widget, CustomKeywordsEditor>
	{
	}

	@UiField
	MaterialModal			mdlKeywordEditorUI;
	@UiField
	MaterialTitle			lblTitleUI;
	@UiField
	MaterialTextArea		txtKeywordsUI;
	@UiField
	MaterialButton			btnApplyUI;
	@UiField
	MaterialButton			btnCancelUI;

	SimpleLocalizedWidget<MaterialTitle>				lblTitleLC;
	SimpleLocalizedWidget<MaterialTextArea>				txtKeywordsLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnApplyLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnCancelLC;

	KeywordWeightSlider		slider;
	List<String>			keywords;

	private void onApply()
	{
		String text = txtKeywordsUI.getText();
		String[] splits = text.replace("\n", ",").split(",");

		if (splits.length == 0 || (splits.length == 1 && splits[0].length() == 0))
			MaterialToast.fireToast(getLocalizedString(CustomKeywordsEditorLocale.DESC_NotifyEmpty));
		else
		{
			keywords.clear();
			for (String itr : splits)
				keywords.add(itr.trim());
			slider.setCustomVocab(true);

			MaterialToast.fireToast(getLocalizedString(CustomKeywordsEditorLocale.DESC_NotifySuccess));
			hide();
		}
	}

	private void onCancel()
	{
		hide();
	}

	private void onEdit()
	{
		show();
	}

	private void onReset()
	{
		keywords.clear();
		slider.setCustomVocab(false);
	}

	private void initLocale()
	{
		lblTitleLC = new SimpleLocalizedWidget<>(lblTitleUI, CustomKeywordsEditorLocale.DESC_lblTitleUI, (w,s) -> w.setTitle(s));
		txtKeywordsLC = new SimpleLocalizedWidget<>(txtKeywordsUI, CustomKeywordsEditorLocale.DESC_txtKeywordsUI, (w,s) -> w.setLabel(s));
		btnApplyLC = new SimpleLocalizedTextButtonWidget<>(btnApplyUI, CustomKeywordsEditorLocale.DESC_btnApplyUI);
		btnCancelLC = new SimpleLocalizedTextButtonWidget<>(btnCancelUI, CustomKeywordsEditorLocale.DESC_btnCancelUI);

		registerLocale(CustomKeywordsEditorLocale.INSTANCE.en);
		registerLocale(CustomKeywordsEditorLocale.INSTANCE.de);

		registerLocalizedWidget(lblTitleLC);
		registerLocalizedWidget(txtKeywordsLC);
		registerLocalizedWidget(btnApplyLC);
		registerLocalizedWidget(btnCancelLC);

		refreshLocalization();
	}

	private void initHandlers()
	{
		btnApplyUI.addClickHandler(e -> onApply());
		btnCancelUI.addClickHandler(e -> onCancel());
	}

	public CustomKeywordsEditor()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));

		slider = null;
		keywords = new ArrayList<>();

		initLocale();
		initHandlers();
	}

	public void show() {
		mdlKeywordEditorUI.open();
	}

	public void hide() {
		mdlKeywordEditorUI.close();
	}

	@Override
	public void bindToSlider(KeywordWeightSlider s)
	{
		if (slider != null)
			throw new RuntimeException("Slider already set");

		slider = s;

		slider.setEditHander(e -> onEdit());
		slider.setResetHandler(e -> onReset());
	}

	@Override
	public boolean hasCustomKeywords() {
		return slider.hasCustomVocab();
	}

	@Override
	public List<String> getCustomKeywords() {
		return keywords;
	}
}
