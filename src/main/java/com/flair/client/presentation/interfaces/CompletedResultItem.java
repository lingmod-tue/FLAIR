package com.flair.client.presentation.interfaces;

/*
 * Represents a result that has a rank
 */
public interface CompletedResultItem extends AbstractResultItem
{
	public int			getOriginalRank();
	public int			getCurrentRank();
}
