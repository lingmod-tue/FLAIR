/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.parser.AbstractDocument;
import com.flair.parser.AbstractDocumentKeywordSearcher;
import com.flair.parser.AbstractDocumentParser;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.AbstractParsingStrategy;
import com.flair.parser.KeywordSearcherInput;
import com.flair.parser.KeywordSearcherOutput;
import com.flair.utilities.FLAIRLogger;
import com.flair.utilities.SimpleObjectPoolResource;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Parses a document source and returns a parsed document
 * @author shadeMe
 */
class DocumentParseTask extends AbstractTask
{
    private final AbstractDocumentSource		input;
    private final AbstractParsingStrategy		strategy;
    private final DocumentParserPool			parserPool;
    private final AbstractDocumentKeywordSearcher	keywordSearcher;
    private final KeywordSearcherInput			keywordSearcherInput;
  
    public DocumentParseTask(AbstractJob job,
			       AbstractTaskContinuation continuation, 
			       AbstractDocumentSource source,
			       AbstractParsingStrategy strategy,
			       DocumentParserPool parserPool,
			       AbstractDocumentKeywordSearcher keywordSearcher,
			       KeywordSearcherInput keywordSearcherInput)
    {
	super(job, TaskType.PARSE_DOCUMENT, continuation);
	
	this.input = source;
	this.strategy = strategy;
	this.parserPool = parserPool;
	this.keywordSearcher = keywordSearcher;
	this.keywordSearcherInput = keywordSearcherInput;
    }
    
    @Override
    protected AbstractTaskResult performTask()
    {
	if (parserPool == null)
	    throw new IllegalStateException("Parser pool not set");
	else if (keywordSearcher == null)
	    throw new IllegalStateException("Keyword searcher not set");
	
	AbstractDocument output = null;
	SimpleObjectPoolResource<AbstractDocumentParser> parserPoolData = null;
	long startTime = System.currentTimeMillis();
	boolean error = false;
	try 
	{
	    parserPoolData = parserPool.get();
	    output = parserPoolData.get().parse(input, strategy);
	    assert output.isParsed() == true;
	    
	    KeywordSearcherOutput keywordData = keywordSearcher.search(output, keywordSearcherInput);
	    output.setKeywordData(keywordData);
	}
	catch (Exception ex)
	{
	    StringWriter sw = new StringWriter();
	    ex.printStackTrace(new PrintWriter(sw));
	    FLAIRLogger.get().error("Document parsing task encountered an error. Exception: " + ex.toString());
	    FLAIRLogger.get().error("Stacktrace: " + sw.toString());
	    output = null;
	    error = true;
	}
	finally
	{
	    if (parserPoolData != null)
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