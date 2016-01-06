/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.parser.AbstractDocumentParser;
import com.flair.parser.AbstractDocumentParserFactory;
import java.util.concurrent.Semaphore;

/**
 * Provides a pool of document parsers for executing parsing tasks
 * @author shadeMe
 */
class DocumentParserPool
{
    private final AbstractDocumentParserFactory		    parserFactory;
    private final AbstractDocumentParser[]		    parsers;
    private final boolean[]				    usageState;
    private final Semaphore				    synchronizer;
    
    public DocumentParserPool(AbstractDocumentParserFactory factory)
    {
	parserFactory = factory;
	assert Constants.PARSER_INSTANCEPOOL_SIZE >= Constants.PARSER_THREADPOOL_SIZE;
	
	parsers = new AbstractDocumentParser[Constants.PARSER_INSTANCEPOOL_SIZE];
	usageState = new boolean[Constants.PARSER_INSTANCEPOOL_SIZE];
	
	for (int i = 0; i < Constants.PARSER_INSTANCEPOOL_SIZE; i++)
	{
	    parsers[i] = parserFactory.create();
	    usageState[i] = false;
	}
	
	synchronizer = new Semaphore(Constants.PARSER_INSTANCEPOOL_SIZE, true);
    }
    
    private synchronized DocumentParserPoolData getNextAvailable()
    {
	for (int i = 0; i < Constants.PARSER_INSTANCEPOOL_SIZE; i++)
	{
	    if (usageState[i] == false)
	    {
		DocumentParserPoolData result = new DocumentParserPoolData(this, i, parsers[i]);
		usageState[i] = true;
		return result;
	    }
	}
	
	return null;
    }
    
    private synchronized void markAsUnused(DocumentParserPoolData acquired)
    {
	assert usageState[acquired.getID()] == true;
	usageState[acquired.getID()] = false;
    }
    
    public DocumentParserPoolData acquire() throws InterruptedException
    {
	synchronizer.acquire();
	return getNextAvailable();
    }
    
    public void release(DocumentParserPoolData data)
    {
	markAsUnused(data);
	synchronizer.release();
    }
}

class DocumentParserPoolData
{
    private final DocumentParserPool		parent;
    private final int				id;	// index into the pool array
    private final AbstractDocumentParser	parser;

    public DocumentParserPoolData(DocumentParserPool parent, int id, AbstractDocumentParser parser)
    {
	this.parent = parent;
	this.id = id;
	this.parser = parser;
    }
    
    public int getID() {
	return id;
    }

    public AbstractDocumentParser getParser() {
	return parser;
    }

    public void release() {
	parent.release(this);
    }
}
