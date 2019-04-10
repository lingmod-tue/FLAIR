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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	static final int MAX_OPTION_LENGTH = 50;

	public QuestionGeneratorPreviewCard(String question, String[] options) {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		if (options.length != 4)
			throw new RuntimeException("Unexpected number of options received");

		List<String> originalOptions = Arrays.asList(options);
		List<String> preprocessedOptions = originalOptions.stream().map(v -> {
			if (v.length() > MAX_OPTION_LENGTH)
				return v.substring(0, MAX_OPTION_LENGTH) + "...";
			else
				return v;
		}).collect(Collectors.toList());
		lblQuestionUI.setText(question);

		List<MaterialButton> buttons = Arrays.asList(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4);
		for (int i = 0; i < options.length; ++i) {
			MaterialButton button = buttons.get(i);
			String answer = preprocessedOptions.get(i);

			if (answer.length() == MAX_OPTION_LENGTH + 3 /* elipses */)
				button.setTooltip(originalOptions.get(i));

			button.setText(preprocessedOptions.get(i));
			int selectionIndex = i;
			button.addClickHandler(e -> answerHandler.handle(selectionIndex));
		}
	}

	public void setAnswerHandler(AnswerHandler handler) {
		answerHandler = handler;
	}
}
