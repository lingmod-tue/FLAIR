package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.RankableDocument;
import com.google.gwt.safehtml.shared.SafeHtml;
import gwt.material.design.client.constants.Color;

/*
 * Input for the document preview pane
 */
public interface DocumentPreviewPaneInput {
	interface Rankable {
		Language getLanguage();            // language of the data set
		Iterable<GrammaticalConstruction> getConstructions();        // language-specific grammatical constructions

		RankableDocument getDocument();

		Iterable<GrammaticalConstruction> getWeightedConstructions();
		boolean isConstructionWeighted(GrammaticalConstruction gram);
		SafeHtml getPreviewMarkup();

		Color getConstructionAnnotationColor(GrammaticalConstruction gram);
		Color getKeywordAnnotationColor();

		boolean shouldShowKeywords();
		boolean hasCustomKeywords();

		double getConstructionWeight(GrammaticalConstruction gram);
		double getKeywordWeight();
	}

	interface UnRankable {
		String getTitle();
		String getText();
	}
}
