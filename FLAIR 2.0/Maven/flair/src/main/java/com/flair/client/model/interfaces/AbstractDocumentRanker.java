package com.flair.client.model.interfaces;

/*
 * Ranks documents according to their constructions weights
 */
public interface AbstractDocumentRanker
{
	public DocumentRankerOutput.Rank 	rerank(DocumentRankerInput.Rank input);
}
