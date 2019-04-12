package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionGeneratorPreviewCard extends LocalizedComposite {
	interface AnswerHandler {
		int handle(int selectedAnswer);     // returns the index of the correct answer
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
	boolean selectionComplete;

	static final int MAX_OPTION_LENGTH = 50;

	private void animateCorrectAnswer(MaterialButton correct) {
		MaterialAnimation correctAnim = new MaterialAnimation(correct).delay(0).duration(1250).infinite(false).transition(Transition.PULSE);
		correct.setType(ButtonType.RAISED);
		correct.setTextColor(Color.WHITE);
		correct.setBackgroundColor(Color.GREEN);
		correct.setIconPosition(IconPosition.RIGHT);
		correct.setIconSize(IconSize.MEDIUM);
		correct.setIconColor(Color.WHITE);
		correct.setIconType(IconType.CHECK);
		correct.setShadow(3);
		correctAnim.animate();
	}

	private void animateIncorrectAnswer(MaterialButton incorrect, MaterialButton correct) {
		MaterialAnimation correctAnim = new MaterialAnimation(correct).delay(0).duration(1500).infinite(false).transition(Transition.BOUNCE);
		correct.setType(ButtonType.RAISED);
		correct.setTextColor(Color.WHITE);
		correct.setBackgroundColor(Color.GREEN);
		correct.setIconPosition(IconPosition.RIGHT);
		correct.setIconSize(IconSize.MEDIUM);
		correct.setIconColor(Color.WHITE);
		correct.setIconType(IconType.CHECK);
		correct.setShadow(3);
		correctAnim.animate();

		MaterialAnimation incorrectAnim = new MaterialAnimation(incorrect).delay(0).duration(1500).infinite(false).transition(Transition.SHAKE);
		incorrect.setType(ButtonType.RAISED);
		incorrect.setTextColor(Color.WHITE);
		incorrect.setBackgroundColor(Color.RED);
		incorrect.setIconPosition(IconPosition.RIGHT);
		incorrect.setIconSize(IconSize.MEDIUM);
		incorrect.setIconColor(Color.WHITE);
		incorrect.setIconType(IconType.HIGHLIGHT_OFF);
		//	incorrectAnim.animate();
	}

	public QuestionGeneratorPreviewCard(String question, String[] options) {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		selectionComplete = false;
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
			button.addClickHandler(e -> {
				if (selectionComplete)
					return;

				selectionComplete = true;
				int correctIndex = answerHandler.handle(selectionIndex);
				if (correctIndex == selectionIndex)
					animateCorrectAnswer(button);
				else
					animateIncorrectAnswer(button, buttons.get(correctIndex));
			});
		}
	}

	public void setAnswerHandler(AnswerHandler handler) {
		answerHandler = handler;
	}
}
