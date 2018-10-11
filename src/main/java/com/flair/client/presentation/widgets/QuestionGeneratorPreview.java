package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.QuestionGeneratorPreviewService;
import com.flair.shared.interop.QuestionDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

import java.util.ArrayList;
import java.util.List;

public class QuestionGeneratorPreview extends LocalizedComposite implements QuestionGeneratorPreviewService {
	private static QuestionGeneratorPreviewUiBinder uiBinder = GWT.create(QuestionGeneratorPreviewUiBinder.class);

	interface QuestionGeneratorPreviewUiBinder extends UiBinder<Widget, QuestionGeneratorPreview> {
	}

	private static QuestionGeneratorPreviewLocalizationBinder localeBinder = GWT.create(QuestionGeneratorPreviewLocalizationBinder.class);

	interface QuestionGeneratorPreviewLocalizationBinder extends LocalizationBinder<QuestionGeneratorPreview> {}

	@UiField
	MaterialOverlay mdlQuestGenUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_TITLE)
	MaterialTitle lblTitleUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.CLOSE, type = LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialIcon btnCloseUI;
	@LocalizedField(type = LocalizedFieldType.TEXT_DESCRIPTION)
	@UiField
	MaterialEmptyState lblPlaceholderUI;
	@UiField
	MaterialRow pnlCardContainerUI;

	List<QuestionCard> previewQuestions;

	static final class QuestionCard {
		MaterialColumn wrapper = new MaterialColumn();
		MaterialCard card = new MaterialCard();
		MaterialCardTitle question = new MaterialCardTitle();
		MaterialCardReveal reveal = new MaterialCardReveal();
		MaterialCardTitle answer = new MaterialCardTitle();

		QuestionCard(QuestionDTO generatedQuestion) {
			wrapper.setGrid("l4 m6 s12");
			wrapper.setPadding(2.5);
			card.setBackgroundColor(Color.BLUE_GREY_DARKEN_1);
			question.setTextColor(Color.WHITE);
			question.setText(generatedQuestion.getQuestion());


			reveal.setBackgroundColor(Color.BLUE_GREY_LIGHTEN_1);
			answer.setText(generatedQuestion.getAnswer());
			answer.setTextColor(Color.WHITE);
			answer.setIconType(IconType.CLOSE);
			answer.setIconPosition(IconPosition.RIGHT);
			reveal.add(answer);

			MaterialCardContent questionWrapper = new MaterialCardContent();
			questionWrapper.add(question);
			card.add(questionWrapper);

			if (!generatedQuestion.getAnswer().isEmpty()) {
				question.setIconType(IconType.DONE);
				question.setIconPosition(IconPosition.RIGHT);
				card.add(reveal);
			}

			wrapper.add(card);
		}
	}


	private void reloadUI(List<QuestionDTO> generatedQuestions) {
		pnlCardContainerUI.clear();
		lblPlaceholderUI.setVisible(false);
		previewQuestions.clear();

		if (generatedQuestions.isEmpty()) {
			lblPlaceholderUI.setVisible(true);
			return;
		}

		pnlCardContainerUI.getElement().getStyle().setOpacity(0);

		for (QuestionDTO gq : generatedQuestions) {
			QuestionCard qc = new QuestionCard(gq);
			pnlCardContainerUI.add(qc.wrapper);
			previewQuestions.add(qc);
		}
	}

	private void initHandlers() {
		btnCloseUI.addClickHandler(e -> {
			hide();
		});
	}

	public QuestionGeneratorPreview() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		previewQuestions = new ArrayList<>();

		initHandlers();
	}

	@Override
	public void previewQuestions(List<QuestionDTO> generatedQuestions) {
		reloadUI(generatedQuestions);

		mdlQuestGenUI.open(btnCloseUI);
		MaterialAnimation gridAnimation = new MaterialAnimation();
		gridAnimation.setTransition(Transition.SHOW_GRID);
		gridAnimation.delay(350).duration(5000);
		gridAnimation.animate(pnlCardContainerUI);
	}

	@Override
	public void hide() {
		mdlQuestGenUI.close();
	}

}
