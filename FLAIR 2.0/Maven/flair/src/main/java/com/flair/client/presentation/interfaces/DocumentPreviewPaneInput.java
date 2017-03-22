package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.RankableDocument;
import com.google.gwt.safehtml.shared.SafeHtml;

/*
 * Input for the document preview pane
 */
public interface DocumentPreviewPaneInput
{
	public interface Rankable
	{
		public RankableDocument						getDocument();
		public Iterable<GrammaticalConstruction>	getWeightedConstructions();
		public boolean								isConstructionWeighted(GrammaticalConstruction gram);
		public SafeHtml								getPreviewMarkup();
		
		public String								getConstructionAnnotationColor(GrammaticalConstruction gram);
		public String								getKeywordAnnotationColor();
		
		public boolean								shouldShowKeywords();
		public boolean								hasCustomKeywords();
		
		public double								getConstructionWeight(GrammaticalConstruction gram);
		public double								getKeywordWeight();
	}
	
	public interface UnRankable
	{
		public String								getTitle();
		public String								getText();
	}
}
