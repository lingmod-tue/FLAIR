/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

/**
 * Task scheduler for document parsing tasks
 * @author shadeMe
 */
class DocumentParsingTaskExecutor extends AbstractTaskExecutor
{
    public DocumentParsingTaskExecutor()
    {
	super(Constants.PARSER_THREADPOOL_SIZE);
    }
    
    public void parse(DocumentParsingTask task)
    {
	queue(task);
    }
}