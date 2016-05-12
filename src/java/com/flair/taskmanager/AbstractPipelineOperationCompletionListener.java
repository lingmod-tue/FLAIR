/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

/**
 * Represents an observer of an AbstractPipelineOperation object that gets notified of events
 * @author shadeMe
 */
public interface AbstractPipelineOperationCompletionListener
{
    public void		handleCompletion(AbstractPipelineOperation source);	// including cancellation
}
