/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

/**
 * Represents the interface of a (possibly executing) pipeline operation. A Future essentially.
 * @author shadeMe
 */
public interface AbstractPipelineOperation
{
    public PipelineOperationType	getType();
    
    public void				begin();
    public boolean			isCancelled();
    public void				cancel();
    public boolean			isCompleted();	    // returns true if the op was cancelled
    public void				registerCompletionListener(AbstractPipelineOperationCompletionListener listener);
    
    public Object			getOutput();
}
