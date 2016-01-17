/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.parser.DocumentCollection;

/**
 * Represents the interface of a(n executing) pipeline operation. A Future essentially
 * @author shadeMe
 */
public interface AbstractPipelineOperation
{
    public boolean			isCancelled();
    public void				cancel();
    
    public DocumentCollection		getOutput();
}
