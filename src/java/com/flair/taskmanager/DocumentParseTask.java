/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.parser.AbstractDocument;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.AbstractParsingStrategy;
import com.flair.utilities.FLAIRLogger;

/**
 * Parses a document source and returns a parsed document
 * @author shadeMe
 */
class DocumentParseTask extends AbstractTask
{
    private final AbstractDocumentSource		input;
    private final AbstractParsingStrategy		strategy;
    private final DocumentParserPool			parserPool;
  
    public DocumentParseTask(AbstractJob job,
			       AbstractTaskContinuation continuation, 
			       AbstractDocumentSource source,
			       AbstractParsingStrategy strategy,
			       DocumentParserPool parserPool)
    {
	super(job, TaskType.PARSE_DOCUMENT, continuation);
	
	this.input = source;
	this.strategy = strategy;
	this.parserPool = parserPool;
    }
    
    @Override
    protected AbstractTaskResult performTask()
    {
	if (parserPool == null)
	    throw new IllegalStateException("Parser pool not set");
	
	AbstractDocument output = null;
	DocumentParserPoolData parserPoolData = null;
	long startTime = System.currentTimeMillis();
	boolean error = false;
	try 
	{
	    parserPoolData = parserPool.acquire();
	    output = parserPoolData.getParser().parse(input, strategy);
	    assert output.isParsed() == true;
	}
	catch (Exception ex)
	{
	    FLAIRLogger.get().error("Document parsing task encountered an error. Exception: " + ex.getMessage());
	    error = true;
	}
	finally {
	    parserPoolData.release();
	}
	
	long endTime = System.currentTimeMillis();
	if (false == error)
	    FLAIRLogger.get().trace("Document " + output.getDescription() + " parsed in " + (endTime - startTime) + " ms");
	
	DocumentParseTaskResult result = new DocumentParseTaskResult(output);
	return result;
    } 
}


class DocumentParseTaskResult extends AbstractTaskResult
{
    private final AbstractDocument		    output;
    
    public DocumentParseTaskResult(AbstractDocument output)
    {
	super(TaskType.PARSE_DOCUMENT);
	this.output = output;
    }
    
    public AbstractDocument getOutput() {
	return output;
    }
}


class DocumentParseTaskExecutor extends AbstractTaskExecutor
{
    public DocumentParseTaskExecutor() {
	super(Constants.PARSER_THREADPOOL_SIZE);
    }
    
    public void parse(DocumentParseTask task) {
	queue(task);
    }
}