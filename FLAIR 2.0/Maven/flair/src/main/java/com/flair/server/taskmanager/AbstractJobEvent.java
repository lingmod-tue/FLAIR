package com.flair.server.taskmanager;

/*
 * Implemented by all job events
 */
public interface AbstractJobEvent<R>
{
	public boolean		isCompletionEvent();		// returns true if the event denotes the completion of the job (no more pending tasks)
													// completion events will not be triggered if the job was cancelled
	public R			getOutput();				// returns the final output or null if the job's still in progress
}
