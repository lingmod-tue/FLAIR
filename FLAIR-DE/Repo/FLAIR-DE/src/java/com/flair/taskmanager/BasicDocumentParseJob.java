/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentKeywordSearcherFactory;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.AbstractParsingStrategyFactory;
import com.flair.parser.DocumentCollection;
import com.flair.parser.KeywordSearcherInput;
import java.util.List;

/**
 * Takes a collection of document sources and parses them
 * @author shadeMe
 */
class BasicDocumentParseJobInput
{
    public final Language				    sourceLanguage;
    public final List<AbstractDocumentSource>		    docSources;
    public final DocumentParseTaskExecutor		    docParsingExecutor;
    public final DocumentParserPool			    docParserPool;
    public final AbstractParsingStrategyFactory		    docParsingStrategy;
    public final AbstractDocumentKeywordSearcherFactory	    keywordSearcher;
    public final KeywordSearcherInput			    keywordSearcherInput;
    
    public BasicDocumentParseJobInput(Language sourceLanguage,
				     List<AbstractDocumentSource> docSources,
				     DocumentParseTaskExecutor docParser,
				     DocumentParserPool parserPool,
				     AbstractParsingStrategyFactory strategy,
				     AbstractDocumentKeywordSearcherFactory keywordSearcher,
				     KeywordSearcherInput keywordSearcherInput)
    {
	this.sourceLanguage = sourceLanguage;
	this.docSources = docSources;
	this.docParsingExecutor = docParser;
	this.docParserPool = parserPool;
	this.docParsingStrategy = strategy;
	this.keywordSearcher = keywordSearcher;
	this.keywordSearcherInput = keywordSearcherInput;
    }
}

class BasicDocumentParseJobOutput
{
    public final DocumentCollection	parsedDocs;
    
    public BasicDocumentParseJobOutput() {
	this.parsedDocs = new DocumentCollection();
    }
}

class BasicDocumentParseJob extends AbstractTaskLinkingJob
{
    private final BasicDocumentParseJobInput	    input;
    private final BasicDocumentParseJobOutput	    output;
    
    public BasicDocumentParseJob(BasicDocumentParseJobInput in)
    {
	super();
	this.input = in;
	this.output = new BasicDocumentParseJobOutput();
    }
    
    @Override
    public Object getOutput()
    {
	waitForCompletion();
	return output.parsedDocs;
    }
    
    @Override
    public void begin() 
    {
	if (isStarted())
	    throw new IllegalStateException("Job has already begun");
	
	for (AbstractDocumentSource itr : input.docSources)
	{
	    DocumentParseTask newTask = new DocumentParseTask(this,
								  new BasicTaskChain(this),
								  itr,
								  input.docParsingStrategy.create(),
								  input.docParserPool,
								  input.keywordSearcher.create(),
								  input.keywordSearcherInput);

	    registerTask(newTask);
	    input.docParsingExecutor.queue(newTask);
	}
	
	flagStarted();
    }

    @Override
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult)
    {
	switch (previousType)
	{
	    case PARSE_DOCUMENT:
	    {
		// add the result to the doc collection
		DocumentParseTaskResult result = (DocumentParseTaskResult)previousResult;
		if (result.getOutput() != null)
		{
		    output.parsedDocs.add(result.getOutput(), true);
		    output.parsedDocs.sort();
		}
		break;
	    }
	}
    }

    @Override
    public String toString()
    {
	if (isStarted() == false)
	    return "BasicDocumentParseJob has not started yet";
	else if (isCompleted() == false)
	    return "BasicDocumentParseJob is still running";
	else if (isCancelled())
	    return "BasicDocumentParseJob was cancelled";
	else
	    return "BasicDocumentParseJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tDocument Sources: " + input.docSources.size() + "\nOutput:\n\tParsed Docs: " + output.parsedDocs.size();
    }
}

