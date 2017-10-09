package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.List;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.CustomKeywordService;
import com.flair.shared.grammar.Language;
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

	private static CustomKeywordsEditorLocalizationBinder localeBinder = GWT.create(CustomKeywordsEditorLocalizationBinder.class);
	interface CustomKeywordsEditorLocalizationBinder extends LocalizationBinder<CustomKeywordsEditor> {}
	
	static enum LocalizationTags
	{
		txtKeywordsUI,
		KEYWORDS_APPLIED,
		NO_KEYWORDS
	}
	
	@UiField
	MaterialModal			mdlKeywordEditorUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TITLE)
	MaterialTitle			lblTitleUI;
	@UiField
	MaterialTextArea		txtKeywordsUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.APPLY, type=LocalizedFieldType.BUTTON)
	MaterialButton			btnApplyUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.CANCEL, type=LocalizedFieldType.BUTTON)
	MaterialButton			btnCancelUI;

	KeywordWeightSlider		slider;
	List<String>			keywords;

	private void onApply()
	{
		String text = txtKeywordsUI.getText();
		String[] splits = text.replace("\n", ",").split(",");

		if (splits.length == 0 || (splits.length == 1 && splits[0].length() == 0))
			MaterialToast.fireToast(getLocalizedString(LocalizationTags.NO_KEYWORDS.toString()));
		else
		{
			keywords.clear();
			for (String itr : splits)
				keywords.add(itr.trim());
			slider.setCustomVocab(true);

			MaterialToast.fireToast(getLocalizedString(LocalizationTags.KEYWORDS_APPLIED.toString()));
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

	private void initHandlers()
	{
		btnApplyUI.addClickHandler(e -> onApply());
		btnCancelUI.addClickHandler(e -> onCancel());
	}

	public CustomKeywordsEditor()
	{
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		slider = null;
		keywords = new ArrayList<>();

		initHandlers();
	}

	@Override
	public void setLocale(Language lang)
	{
		super.setLocale(lang);
		
		txtKeywordsUI.setLabel(getLocalizedString(LocalizationTags.txtKeywordsUI.toString()));
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
