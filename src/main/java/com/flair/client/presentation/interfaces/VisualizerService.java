package com.flair.client.presentation.interfaces;

import com.flair.client.presentation.widgets.LanguageSpecificConstructionSliderBundle;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.RankableDocument;

import java.util.List;

/*
 * Provides an interface to visualize and filter parsed documents
 */
public interface VisualizerService {
	public interface Input {
		public Iterable<GrammaticalConstruction> getConstructions();        // langauge specific
		public Iterable<RankableDocument> getDocuments();
		public LanguageSpecificConstructionSliderBundle getSliders();

		public boolean isDocumentFiltered(RankableDocument doc);
	}

	public interface ApplyFilterHandler {
		public void handle(List<RankableDocument> filtered);
	}

	public interface ResetFilterHandler {
		public void handle();
	}

	public void show();
	public void hide();

	public void visualize(Input input);

	public void setApplyFilterHandler(ApplyFilterHandler handler);
	public void setResetFilterHandler(ResetFilterHandler handler);
}
