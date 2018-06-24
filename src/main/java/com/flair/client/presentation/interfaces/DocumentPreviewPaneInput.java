package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableDocument;
import com.google.gwt.safehtml.shared.SafeHtml;

import gwt.material.design.client.constants.Color;

/*
 * Input for the document preview pane
 */
public interface DocumentPreviewPaneInput
{
	public interface Rankable
	{
		public Language								getLanguage();			// language of the data set
		public Iterable<GrammaticalConstruction>	getConstructions();		// language-specific grammatical constructions
		
		public RankableDocument						getDocument();
		
		public Iterable<GrammaticalConstruction>	getWeightedConstructions();
		public boolean								isConstructionWeighted(GrammaticalConstruction gram);
		public SafeHtml								getPreviewMarkup();
		
		public Color								getConstructionAnnotationColor(GrammaticalConstruction gram);
		public Color								getKeywordAnnotationColor();
		
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
