/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
