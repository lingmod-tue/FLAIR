/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.AbstractParsingStrategyFactory;
import com.flair.parser.DocumentCollection;
import java.util.List;

/**
 * Takes a collection of document sources and parses them
 * @author shadeMe
 */
class LocalDocumentParserJob extends AbstractTaskLinkingJob implements BasicParsingJobOutput
{
    private final LocalDocumentParserJobInput	    input;
    private final LocalDocumentParserJobOutput	    output;
    
    public LocalDocumentParserJob(LocalDocumentParserJobInput in)
    {
	super();
	this.input = in;
	this.output = new LocalDocumentParserJobOutput();
    }
    
    public LocalDocumentParserJobOutput getOutput()
    {
	waitForCompletion();
	return output;
    }
    
    @Override
    public void begin() 
    {
	if (jobStarted)
	    throw new IllegalStateException("Job has already begun");
	
	jobStarted = true;
	for (AbstractDocumentSource itr : input.docSources)
	{
	    DocumentParsingTask newTask = new DocumentParsingTask(this,
								  new BasicTaskChain(this),
								  itr,
								  input.docParsingStrategy.create(),
								  input.docParserPool);

	    registerTask(newTask);
	    input.docParsingExecutor.queue(newTask);
	}
    }

    @Override
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult)
    {
	switch (previousType)
	{
	    case PARSE_DOCUMENT:
	    {
		// add the result to the doc collection
		DocumentParsingTaskResult result = (DocumentParsingTaskResult)previousResult;
		output.parsedDocs.add(result.getOutput(), true);
		break;
	    }
	}
    }

    @Override
    public DocumentCollection getParsedDocuments() {
	return getOutput().parsedDocs;
    }
    
    @Override
    public String toString()
    {
	if (isCompleted() == false)
	    return "LocalDocumentParserJob is still running";
	else if (isCancelled())
	    return "LocalDocumentParserJob was cancelled";
	else
	    return "LocalDocumentParserJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tDocument Sources: " + input.docSources.size() + "\nOutput:\n\tParsed Docs: " + output.parsedDocs.size();
    }
}

class LocalDocumentParserJobInput
{
    public final Language			    sourceLanguage;
    public final List<AbstractDocumentSource>	    docSources;
    public final DocumentParsingTaskExecutor	    docParsingExecutor;
    public final DocumentParserPool		    docParserPool;
    public final AbstractParsingStrategyFactory	    docParsingStrategy;
    
    public LocalDocumentParserJobInput(Language sourceLanguage,
				     List<AbstractDocumentSource> docSources,
				     DocumentParsingTaskExecutor docParser,
				     DocumentParserPool parserPool,
				     AbstractParsingStrategyFactory strategy)
    {
	this.sourceLanguage = sourceLanguage;
	this.docSources = docSources;
	this.docParsingExecutor = docParser;
	this.docParserPool = parserPool;
	this.docParsingStrategy = strategy;
    }
}

class LocalDocumentParserJobOutput
{
    public final DocumentCollection	parsedDocs;
    
    public LocalDocumentParserJobOutput()
    {
	this.parsedDocs = new DocumentCollection();
    }
}