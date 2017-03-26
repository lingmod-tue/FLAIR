package com.flair.client.presentation.interfaces;

/*
 * Displays in-progress and parsed results
 */
public interface AbstractDocumentResultsPane
{
	public interface SelectHandler {
		public void handle(AbstractResultItem selection);
	}
	
	public void			show();
	public void			hide();
	
	public void			setPanelTitle(String title);
	public void			setPanelSubtitle(String subtitle);
	
	public void			addCompleted(CompletedResultItem item);
	public void			addInProgress(InProgressResultItem item);
	
	public void			removeCompleted(CompletedResultItem item);
	public void			removeInProgress(InProgressResultItem item);
	
	public void			clearCompleted();
	public void			clearInProgress();
	
	public void			setSelectHandler(SelectHandler handler);
}
