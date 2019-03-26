package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.QuestionGeneratorPreviewService;
import com.flair.client.utilities.GlobalWidgetAnimator;
import com.flair.client.utilities.GwtUtil;
import com.flair.shared.interop.QuestionDTO;
import com.flair.shared.interop.RankableDocument;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.Transition;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuestionGeneratorPreview extends LocalizedComposite implements QuestionGeneratorPreviewService {
	private static QuestionGeneratorPreviewUiBinder uiBinder = GWT.create(QuestionGeneratorPreviewUiBinder.class);

	interface QuestionGeneratorPreviewUiBinder extends UiBinder<Widget, QuestionGeneratorPreview> {
	}

	private static QuestionGeneratorPreviewLocalizationBinder localeBinder = GWT.create(QuestionGeneratorPreviewLocalizationBinder.class);

	interface QuestionGeneratorPreviewLocalizationBinder extends LocalizationBinder<QuestionGeneratorPreview> {}

	enum LocalizationTags {
		IN_PROGRESS,
		NO_QUESTIONS_TITLE,
		NO_QUESTIONS_DESC,
		ALL_ANSWERS_INCORRECT_TITLE,
		ALL_ANSWERS_INCORRECT_DESC,
		SOME_ANSWERS_CORRECT_TITLE,
		SOME_ANSWERS_CORRECT_DESC,
	}

	@UiField
	MaterialOverlay mdlRootUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_DESCRIPTION)
	MaterialTitle lblTitleUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.CLOSE, type = LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialIcon btnCloseUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnGenerateQuestUI;
	@UiField
	MaterialRow pnlPreviewFrameUI;
	@UiField
	Frame pnlPreviewTargetUI;
	@UiField
	MaterialRow pnlPlaceholderUI;
	@UiField
	MaterialEmptyState lblPlaceholderUI;
	@UiField
	MaterialRow pnlQuestFormUI;
	@UiField
	MaterialProgress lblQuestProgressUI;
	@UiField
	MaterialRow pnlQuestCardsUI;
	@UiField
	MaterialRow pnlHiddenUI;
	@UiField
	MaterialEmptyState pnlScorecardUI;

	GenerateHandler generateHandler;
	InterruptHandler interruptHandler;

	private static final int MAX_TITLE_LENGTH = 35;
	private static final int MAX_ANSWER_OPTIONS = 4;

	static class QuestionWidget {
		QuestionGeneratorPreviewCard container;
		QuestionDTO parent;
		boolean correctAnswerSelected;
		int answerIndex;

		QuestionWidget(QuestionDTO question) {
			parent = question;
			correctAnswerSelected = false;
			answerIndex = Random.nextInt(MAX_ANSWER_OPTIONS);

			if (parent.getDistractors().size() < MAX_ANSWER_OPTIONS - 1)
				throw new RuntimeException("Too few distractors (" + parent.getDistractors().size() + ") for question '" + parent.getQuestion() + "'");

			String[] options = new String[MAX_ANSWER_OPTIONS];
			for (int i = 0, j = 0; i < MAX_ANSWER_OPTIONS; ++i) {
				if (i == answerIndex)
					options[i] = parent.getAnswer();
				else {
					options[i] = parent.getDistractors().get(j);
					++j;
				}
			}

			container = new QuestionGeneratorPreviewCard(parent.getQuestion(), options);
			container.setVisible(false);
		}
	}

	class DisplayState {
		List<QuestionWidget> questionForms;
		int currentDisplayIndex;

		void updateProgressBar() {
			float percent = (currentDisplayIndex / (float) questionForms.size()) * 100.f;
			lblQuestProgressUI.setPercent(percent);
		}

		int getScore() {
			int score = 0;
			for (QuestionWidget qw : questionForms)
				if (qw.correctAnswerSelected)
					score += 1;
			return score;
		}

		void nextQuestion(int currentAnswerIndex) {
			QuestionWidget currentlyDisplayed = currentDisplayIndex < questionForms.size() && currentDisplayIndex >= 0 ?
					questionForms.get(currentDisplayIndex) : null;

			if (currentlyDisplayed != null)
				currentlyDisplayed.correctAnswerSelected = currentAnswerIndex == currentlyDisplayed.answerIndex;

			++currentDisplayIndex;
			QuestionWidget nextToBeDisplayed = currentDisplayIndex < questionForms.size() ? questionForms.get(currentDisplayIndex) : null;

			if (currentlyDisplayed == null) {
				// show first card
				GlobalWidgetAnimator.get().seqAnimateWithStart(nextToBeDisplayed.container,
						Transition.FADEINRIGHT,
						0,
						500,
						() -> {
							pnlQuestCardsUI.add(nextToBeDisplayed.container);
							nextToBeDisplayed.container.setVisible(true);
						});

				GlobalWidgetAnimator.get().animateWithStart(lblQuestProgressUI,
						Transition.FADEINDOWN,
						100,
						500,
						() -> lblQuestProgressUI.setVisible(true));
			} else if (nextToBeDisplayed == null) {
				// show score card
				GlobalWidgetAnimator.get().seqAnimateWithStop(currentlyDisplayed.container,
						Transition.FADEOUTLEFT,
						0,
						500,
						() -> {
							currentlyDisplayed.container.setVisible(false);
							GlobalWidgetAnimator.get().seqAnimateWithStart(pnlScorecardUI,
									Transition.FADEINRIGHT,
									0,
									500,
									() -> {
										pnlQuestCardsUI.add(pnlScorecardUI);

										int score = getScore(), maxScore = questionForms.size();
										if (score == 0) {
											pnlScorecardUI.setIconType(IconType.SENTIMENT_VERY_DISSATISFIED);
											pnlScorecardUI.setIconColor(Color.RED);
											pnlScorecardUI.setTitle(getLocalizedString(LocalizationTags.ALL_ANSWERS_INCORRECT_TITLE.toString()));
											pnlScorecardUI.setDescription(getLocalizedString(LocalizationTags.ALL_ANSWERS_INCORRECT_DESC.toString()));
										} else {
											pnlScorecardUI.setIconType(IconType.DONE);
											pnlScorecardUI.setIconColor(Color.GREEN_DARKEN_2);
											pnlScorecardUI.setTitle(getLocalizedString(LocalizationTags.SOME_ANSWERS_CORRECT_TITLE.toString()));
											String scorecard = GwtUtil.formatString(getLocalizedString(LocalizationTags.SOME_ANSWERS_CORRECT_DESC.toString()),
													score, maxScore);
											pnlScorecardUI.setDescription(scorecard);
										}

										pnlScorecardUI.setVisible(true);
									});

							GlobalWidgetAnimator.get().animateWithStop(lblQuestProgressUI,
									Transition.FADEOUTUP,
									0,
									750,
									() -> lblQuestProgressUI.setOpacity(0));
						});
			} else {
				// show intermediate card
				GlobalWidgetAnimator.get().seqAnimateWithStop(currentlyDisplayed.container,
						Transition.FADEOUTLEFT,
						0,
						500,
						() -> {
							currentlyDisplayed.container.setVisible(false);
							GlobalWidgetAnimator.get().seqAnimateWithStart(nextToBeDisplayed.container,
									Transition.FADEINRIGHT,
									0,
									500,
									() -> {
										pnlQuestCardsUI.add(nextToBeDisplayed.container);
										nextToBeDisplayed.container.setVisible(true);
									});

						});
			}

			updateProgressBar();
		}

		void init(List<QuestionDTO> questions) {
			questionForms = questions.stream().map(v -> {
				if (v.getDistractors().size() < MAX_ANSWER_OPTIONS - 1)
					return null;

				QuestionWidget qw = new QuestionWidget(v);
				qw.container.setAnswerHandler(this::nextQuestion);
				return qw;
			}).filter(Objects::nonNull).collect(Collectors.toList());
			currentDisplayIndex = -1;

			// set up progress bar
			lblQuestProgressUI.setPercent(0);

			// hide the placeholder
			GlobalWidgetAnimator.get().seqAnimateWithStartStop(pnlPlaceholderUI,
					Transition.PULSE, 0, 1000, () -> {
						lblPlaceholderUI.setIconType(IconType.DONE);
						lblPlaceholderUI.setIconColor(Color.GREEN_DARKEN_2);
					}, () -> {
						GlobalWidgetAnimator.get().seqAnimateWithStop(pnlPlaceholderUI,
								Transition.FADEOUTLEFT, 0, 500, () -> {
									pnlPlaceholderUI.setVisible(false);

									GlobalWidgetAnimator.get().animateWithStartStop(pnlQuestFormUI,
											Transition.FADEINRIGHT,
											0,
											500,
											() -> pnlQuestFormUI.setVisible(true),
											() -> {
												GlobalWidgetAnimator.get().animateWithStart(lblQuestProgressUI,
														Transition.FADEINDOWN,
														0,
														750,
														() -> lblQuestProgressUI.setOpacity(1));
											});
								});
					});

			nextQuestion(0);
		}
	}

	RankableDocument previewDocument;
	boolean generationInProgress;
	DisplayState displayState;


	private void resetUI(String title, String url) {
		pnlPreviewFrameUI.setVisible(false);
		pnlPlaceholderUI.setVisible(false);
		pnlQuestFormUI.setVisible(false);
		pnlScorecardUI.setVisible(false);
		pnlHiddenUI.add(pnlScorecardUI);
		pnlQuestCardsUI.clear();
		btnGenerateQuestUI.setVisible(true);
		lblQuestProgressUI.setOpacity(0);
		lblPlaceholderUI.setTitle("");
		lblPlaceholderUI.setDescription("");
		lblPlaceholderUI.setIconColor(Color.ORANGE);

		if (title.length() > MAX_TITLE_LENGTH)
			title = title.substring(0, MAX_TITLE_LENGTH) + "...";

		lblTitleUI.setTitle(title);
		pnlPreviewTargetUI.setUrl(url);

		GlobalWidgetAnimator.get().seqAnimateWithStart(pnlPreviewFrameUI,
				Transition.FADEINUP, 0, 650, () -> pnlPreviewFrameUI.setVisible(true));
	}

	private void generateQuestions() {
		if (!generateHandler.handle(previewDocument))
			return;

		generationInProgress = true;
		GlobalWidgetAnimator.get().seqAnimateWithStop(pnlPreviewFrameUI,
				Transition.FADEOUTLEFT, 0, 500, () -> {
					pnlPreviewFrameUI.setVisible(false);
					GlobalWidgetAnimator.get().seqAnimateWithStart(pnlPlaceholderUI,
							Transition.FADEINRIGHT, 0, 500, () -> {
								lblPlaceholderUI.setIconType(IconType.QUESTION_ANSWER);
								lblPlaceholderUI.setLoading(true);
								lblPlaceholderUI.setTitle(getLocalizedString(LocalizationTags.IN_PROGRESS.toString()));
								pnlPlaceholderUI.setVisible(true);
							});
				});

		GlobalWidgetAnimator.get().animateWithStop(btnGenerateQuestUI, Transition.ZOOMOUT, 10, 800, () -> btnGenerateQuestUI.setVisible(false));
	}

	private void interruptGeneration() {
		if (generationInProgress) {
			interruptHandler.handle();
			generationInProgress = false;
		}
	}

	private void displayQuestions(List<QuestionDTO> questions) {
		if (!generationInProgress) {
			// the preview modal is closed, do nothing
			generationInProgress = false;
			return;
		}

		generationInProgress = false;
		lblPlaceholderUI.setLoading(false);

		if (questions.isEmpty()) {
			lblPlaceholderUI.setTitle(getLocalizedString(LocalizationTags.NO_QUESTIONS_TITLE.toString()));
			lblPlaceholderUI.setDescription(getLocalizedString(LocalizationTags.NO_QUESTIONS_DESC.toString()));
			lblPlaceholderUI.setIconType(IconType.SENTIMENT_DISSATISFIED);
			GlobalWidgetAnimator.get().animateWithStop(lblPlaceholderUI, Transition.BOUNCE, 0, 750, () -> {});
		} else
			displayState.init(questions);
	}

	private void initUIAndHandlers() {
		btnGenerateQuestUI.addClickHandler(e -> {
			generateQuestions();
		});

		btnCloseUI.addClickHandler(e -> {
			interruptGeneration();
			mdlRootUI.close();
		});

		lblTitleUI.addClickHandler(e -> {
			String url = previewDocument.getUrl();
			if (!url.isEmpty())
				Window.open(url, "_blank", "");
		});
		lblTitleUI.getElement().getStyle().setCursor(Style.Cursor.POINTER);
	}

	public QuestionGeneratorPreview() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		displayState = new DisplayState();
		generationInProgress = false;

		initUIAndHandlers();
	}

	@Override
	public void show(RankableDocument document, Element origin) {
		// TODO add support for non-URL documents
		if (generationInProgress)
			throw new RuntimeException("Previous generation operation is still in progress");

		previewDocument = document;

		resetUI(previewDocument.getTitle(), previewDocument.getUrl());
		mdlRootUI.open(origin);
	}

	@Override
	public void display(List<QuestionDTO> questions) {
		displayQuestions(questions);
	}

	@Override
	public void setGenerateHandler(GenerateHandler handler) {
		generateHandler = handler;
	}

	@Override
	public void setInterruptHandler(InterruptHandler handler) {
		interruptHandler = handler;
	}


}
