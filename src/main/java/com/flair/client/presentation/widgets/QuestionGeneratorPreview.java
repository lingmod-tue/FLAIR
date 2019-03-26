package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.QuestionGeneratorPreviewService;
import com.flair.client.utilities.GlobalWidgetAnimator;
import com.flair.shared.interop.QuestionDTO;
import com.flair.shared.interop.RankableDocument;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.Transition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionGeneratorPreview extends LocalizedComposite implements QuestionGeneratorPreviewService {
	private static QuestionGeneratorPreviewUiBinder uiBinder = GWT.create(QuestionGeneratorPreviewUiBinder.class);

	interface QuestionGeneratorPreviewUiBinder extends UiBinder<Widget, QuestionGeneratorPreview> {
	}

	private static QuestionGeneratorPreviewLocalizationBinder localeBinder = GWT.create(QuestionGeneratorPreviewLocalizationBinder.class);

	interface QuestionGeneratorPreviewLocalizationBinder extends LocalizationBinder<QuestionGeneratorPreview> {}

	@UiField
	MaterialOverlay mdlRootUI;
	@UiField
	MaterialTitle lblTitleUI;
	@UiField
	MaterialIcon btnCloseUI;
	@UiField
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

	static class QuestionWidget {
		QuestionGeneratorPreviewCard container;
		QuestionDTO parent;
		boolean correctAnswerSelected;

		QuestionWidget(QuestionDTO question) {
			parent = question;
			correctAnswerSelected = false;

			List<String> options = Arrays.asList(
					"Answer 1 - The quick brown fox jumped over the lazy dog.",
					"Answer 2 - Twinkle twinkle little star",
					"Answer 3 - Shut up, Ned!",
					"Answer 4 - Respect mah authoratah!");
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

			if (currentlyDisplayed != null) {
				// TODO fix this logic to check the selection
				currentlyDisplayed.correctAnswerSelected = currentAnswerIndex == 1;
			}

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

										pnlScorecardUI.setTitle("Score!");
										pnlScorecardUI.setDescription("You answered " + getScore() + " question(s) out of " + questionForms.size() + " correctly!");
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
				QuestionWidget qw = new QuestionWidget(v);
				qw.container.setAnswerHandler(this::nextQuestion);
				return qw;
			}).collect(Collectors.toList());
			currentDisplayIndex = -1;

			// set up progress bar
			lblQuestProgressUI.setPercent(0);

			// hide the placeholder
			GlobalWidgetAnimator.get().seqAnimateWithStop(pnlPlaceholderUI,
					Transition.FADEOUTLEFT, 0, 500, () ->
					{
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

			nextQuestion(0);
		}
	}

	RankableDocument previewDocument;
	boolean generationInProgress;
	DisplayState displayState;

	private static int MAX_TITLE_LENGTH = 35;

	private void resetUI(String title, String url) {
		pnlPreviewFrameUI.setVisible(false);
		pnlPlaceholderUI.setVisible(false);
		pnlQuestFormUI.setVisible(false);
		pnlScorecardUI.setVisible(false);
		pnlHiddenUI.add(pnlScorecardUI);
		pnlQuestCardsUI.clear();
		btnGenerateQuestUI.setVisible(true);
		lblQuestProgressUI.setOpacity(0);

		if (title.length() > MAX_TITLE_LENGTH)
			title = title.substring(0, MAX_TITLE_LENGTH) + "...";

		lblTitleUI.setTitle(title);
		lblTitleUI.setDescription("If the website does not display correctly, click on the title above to open it in a new tab.");
		pnlPreviewTargetUI.setUrl(url);

		GlobalWidgetAnimator.get().seqAnimateWithStart(pnlPreviewFrameUI,
				Transition.FADEINUP, 0, 650, () -> pnlPreviewFrameUI.setVisible(true));
	}

	private void generateQuestions() {
		if (!generateHandler.handle(previewDocument))
			return;

		generationInProgress = true;
		GlobalWidgetAnimator.get().seqAnimateWithStop(pnlPreviewFrameUI,
				Transition.FADEOUTLEFT, 0, 500, () ->
				{
					pnlPreviewFrameUI.setVisible(false);
					GlobalWidgetAnimator.get().seqAnimateWithStart(pnlPlaceholderUI,
							Transition.FADEINRIGHT, 0, 500, () ->
							{
								pnlPlaceholderUI.setVisible(true);
								lblPlaceholderUI.setLoading(true);
								lblPlaceholderUI.setTitle("Generating questions...");
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

		if (questions.isEmpty())
			lblPlaceholderUI.setDescription("No questions :(");
		else
			displayState.init(questions);
	}

	private void initHandlers() {
		btnGenerateQuestUI.addClickHandler(e -> {
			generateQuestions();
		});

		btnCloseUI.addClickHandler(e -> {
			interruptGeneration();
			mdlRootUI.close();
		});
	}

	public QuestionGeneratorPreview() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		displayState = new DisplayState();
		generationInProgress = false;

		initHandlers();
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
