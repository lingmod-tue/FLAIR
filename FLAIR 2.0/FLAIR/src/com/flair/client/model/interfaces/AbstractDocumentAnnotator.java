package com.flair.client.model.interfaces;

/*
 * Provides annotation services for ranked documents
 */
public interface AbstractDocumentAnnotator
{
	public DocumentAnnotatorOutput.HighlightText 		hightlightText(DocumentAnnotatorInput.HighlightText input);
	public DocumentAnnotatorOutput.ExportAnnotation 	exportAnnotation(DocumentAnnotatorInput.ExportAnnotation input);
}
