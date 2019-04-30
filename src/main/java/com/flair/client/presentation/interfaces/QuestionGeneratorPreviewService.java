package com.flair.client.presentation.interfaces;

import com.flair.shared.interop.dtos.QuestionDTO;
import com.flair.shared.interop.dtos.RankableDocument;
import com.google.gwt.dom.client.Element;

import java.util.List;

public interface QuestionGeneratorPreviewService {
	interface InterruptHandler {
		void handle();
	}

	interface GenerateHandler {
		boolean handle(RankableDocument document, int numQuestions, boolean randomize);
	}

	interface ShowHandler {
		void handle(RankableDocument document);
	}

	void show(RankableDocument document, Element origin);
	void display(List<QuestionDTO> questions);

	void setGenerateHandler(GenerateHandler handler);
	void setInterruptHandler(InterruptHandler handler);
	void setShowHandler(ShowHandler handler);
}
