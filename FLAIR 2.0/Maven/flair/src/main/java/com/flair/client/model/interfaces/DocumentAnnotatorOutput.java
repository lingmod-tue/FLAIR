package com.flair.client.model.interfaces;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/*
 * Output of the DocumentAnnotator class
 */
public interface DocumentAnnotatorOutput
{
	public interface HighlightText {
		public SafeHtml			getHighlightedText();		// returns text with the highlight markup
	}
	
	public interface ExportAnnotation {
		public String			getBRATAnnotation();		// returns the annotations in BRAT's text-bound format
	}
}
