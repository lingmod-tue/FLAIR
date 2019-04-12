package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;
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
	@UiField
	MaterialIcon icoAnswer1;
	@UiField
	MaterialIcon icoAnswer2;
	@UiField
	MaterialIcon icoAnswer3;
	@UiField
	MaterialIcon icoAnswer4;

	AnswerHandler answerHandler;
	boolean selectionComplete;

	static final int MAX_OPTION_LENGTH = 50;

	private void animateCorrectAnswer(MaterialIcon correct) {
		MaterialAnimation correctAnim = new MaterialAnimation(correct).delay(0).duration(800).infinite(false).transition(Transition.BOUNCE);
		correct.setIconColor(Color.GREEN);
		correct.setIconType(IconType.CHECK);
		correctAnim.animate();
	}

	private void animateIncorrectAnswer(MaterialIcon incorrect, MaterialIcon correct) {
		MaterialAnimation correctAnim = new MaterialAnimation(correct).delay(300).duration(800).infinite(false).transition(Transition.BOUNCE);
		correct.setIconColor(Color.GREEN);
		correct.setIconType(IconType.CHECK);
		correctAnim.animate();

		MaterialAnimation incorrectAnim = new MaterialAnimation(incorrect).delay(0).duration(800).infinite(false).transition(Transition.HEADSHAKE);
		incorrect.setIconColor(Color.RED);
		incorrect.setIconType(IconType.HIGHLIGHT_OFF);
		incorrectAnim.animate();
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
		List<MaterialIcon> icons = Arrays.asList(icoAnswer1, icoAnswer2, icoAnswer3, icoAnswer4);
		for (int i = 0; i < options.length; ++i) {
			MaterialButton button = buttons.get(i);
			MaterialIcon icon = icons.get(i);
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
					animateCorrectAnswer(icon);
				else
					animateIncorrectAnswer(icon, icons.get(correctIndex));
			});

			button.addMouseOverHandler(e -> {
				button.setBackgroundColor(Color.BLUE_DARKEN_2);
				button.setTextColor(Color.WHITE);
			});
			button.addMouseOutHandler(e -> {
				button.setBackgroundColor(Color.TRANSPARENT);
				button.setTextColor(Color.BLACK);
			});
		}
	}

	public void setAnswerHandler(AnswerHandler handler) {
		answerHandler = handler;
	}
}
