package com.flair.client.presentation.interfaces;

/*
 * Allows the user to cancel an ongoing operation
 */
public interface OperationCancelService
{
	public interface CancelHandler {
		public void handle();
	}
	
	public void			cancelOperation();
	public void			setCancelHandler(CancelHandler handler);
}
