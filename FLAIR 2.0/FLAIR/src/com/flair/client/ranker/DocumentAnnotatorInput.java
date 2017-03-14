package com.flair.client.ranker;

import com.flair.shared.RankableDocument;
import com.flair.shared.grammar.GrammaticalConstruction;

/*
 * Input for the DocumentAnnotator class
 */
public interface DocumentAnnotatorInput
{
	public interface HighlightText
	{
		public RankableDocument							getDocument();
		
		public Iterable<GrammaticalConstruction>		getAnnotationConstructions();						// constructions that need to be annotated
		public String									getAnnotationColor(GrammaticalConstruction gram);	// the color string for the construction
		public String									getConstructionTitle(GrammaticalConstruction gram);	// the text to be displayed on mouse-over
		
		public boolean									shouldAnnotateKeywords();		// true if keywords are to be annotated
		public String									getKeywordAnnotationColor();
		public String									getKeywordTitle();
	}
}
