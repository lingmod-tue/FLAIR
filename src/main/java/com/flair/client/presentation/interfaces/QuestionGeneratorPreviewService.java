package com.flair.client.presentation.interfaces;

import com.flair.shared.interop.QuestionDTO;

import java.util.List;

public interface QuestionGeneratorPreviewService {
	void previewQuestions(List<QuestionDTO> generatedQuestions);
	void hide();
}
