package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

import java.util.List;

public class QuestionGeneratorPreviewCard extends LocalizedComposite {
	interface AnswerHandler {
		void handle(int selectedAnswer);
	}

	private static DocumentResultDisplayItemUiBinder uiBinder = GWT.create(DocumentResultDisplayItemUiBinder.class);

	interface DocumentResultDisplayItemUiBinder extends UiBinder<Widget, QuestionGeneratorPreviewCard> {
	}

	private static DocumentResultDisplayItemLocalizationBinder localeBinder = GWT.create(DocumentResultDisplayItemLocalizationBinder.class);

	interface DocumentResultDisplayItemLocalizationBinder extends LocalizationBinder<QuestionGeneratorPreviewCard> {}


	@UiField
	MaterialRow pnlRootUI;
	@UiField
	MaterialCard pnlCardUI;
	@UiField
	MaterialLabel lblQuestionUI;
	@UiField
	MaterialButton btnAnswer1;
	@UiField
	MaterialButton btnAnswer2;
	@UiField
	MaterialButton btnAnswer3;
	@UiField
	MaterialButton btnAnswer4;

	AnswerHandler answerHandler;

	public QuestionGeneratorPreviewCard(String question, List<String> options) {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		if (options.size() != 4)
			throw new RuntimeException("Unexpected number of options received");

		lblQuestionUI.setText(question);

		btnAnswer1.setText(options.get(0));
		btnAnswer1.addClickHandler(e -> answerHandler.handle(1));

		btnAnswer2.setText(options.get(1));
		btnAnswer2.addClickHandler(e -> answerHandler.handle(2));

		btnAnswer3.setText(options.get(2));
		btnAnswer3.addClickHandler(e -> answerHandler.handle(3));

		btnAnswer4.setText(options.get(3));
		btnAnswer4.addClickHandler(e -> answerHandler.handle(4));
	}

	public void setAnswerHandler(AnswerHandler handler) {
		answerHandler = handler;
	}
}
