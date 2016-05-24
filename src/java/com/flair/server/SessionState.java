/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server;

import com.flair.crawler.SearchResult;
import com.flair.grammar.DefaultVocabularyList;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocument;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.DocumentCollection;
import com.flair.parser.KeywordSearcherInput;
import com.flair.parser.SearchResultDocumentSource;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.AbstractPipelineOperationCompletionListener;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.taskmanager.PipelineOperationType;
import com.flair.utilities.FLAIRLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import javax.websocket.Session;

/**
 * Represents an active session on the server
 * @author shadeMe
 */
class SessionState
{   
    private final Session						parentSession;
    private final HashMap<String, AbstractPipelineOperation>		idTable;		// maps operations to UIDs
    private final HashMap<AbstractPipelineOperation, String>		operationTable;		// maps UIDs to operations
    private AbstractPipelineOperation					lastQueuedOperation;
    private KeywordSearcherInput					activeKeywords;		// as sent by the client
    
    private boolean							valid;			// invalidated after release()
    
    final class SessionOperationCompletionListener implements AbstractPipelineOperationCompletionListener
    {
	private final String				    opID;
	private final BasicInteropMessage.MessageType	    reqType;

	public SessionOperationCompletionListener(String opID, BasicInteropMessage.MessageType reqType)
	{
	    this.opID = opID;
	    this.reqType = reqType;
	}
	
	@Override
	public void handleCompletion(AbstractPipelineOperation source) 
	{
	    if (isValid() && source.isCancelled() == false)
	    {
		FLAIRLogger.get().info("Pipeline operation " + opID + " complete. Details:\n" + source.toString());
		
		switch (source.getType())
		{
		    case WEB_SEARCH_CRAWL:
		    {	
			Object output = source.getOutput();
			List<SearchResult> searchResults = (List<SearchResult>)output;
			sendMessage(new WebSearchCompleteResponse(opID, searchResults.size()));
			break;
		    }
		    
		    case PARSE_DOCUMENTS:
		    {
			Object output = source.getOutput();
			DocumentCollection parsedDocs = (DocumentCollection)output;
			sendMessage(new ParseSearchResultsCompleteResponse(opID, parsedDocs.size()));
			break;
		    }
				    
		    default:
			sendErrorResponse(BasicInteropMessage.MessageType.JOB_COMPLETE, "Couldn't generate response for pipeline operation " + opID + " of type " + source.getType());
		}
	    }
	}
    }
    
    public SessionState(Session parent)
    {
	parentSession = parent;
	lastQueuedOperation = null;
	idTable = new HashMap<>();
	operationTable = new HashMap<>();
	activeKeywords = null;
	
	valid = true;
    }
    
    private String generateUID() {
	return UUID.randomUUID().toString();
    }
    
    private String registerOperation(AbstractPipelineOperation operation, BasicInteropMessage.MessageType requestType)
    {
	if (lastQueuedOperation != null && lastQueuedOperation.isCompleted() == false)
	    throw new IllegalStateException("New operation started before previous operation was complete. Previous operation type: " + lastQueuedOperation.getType() + "\nDetails: " + lastQueuedOperation.toString());
	
	String id = generateUID();
	idTable.put(id, operation);
	operationTable.put(operation, id);
	lastQueuedOperation = operation;
	operation.registerCompletionListener(new SessionOperationCompletionListener(id, requestType));
	
	FLAIRLogger.get().info("Queued pipeline operation of type " + operation.getType() + " for session " + parentSession.getId());
	return id;
    }
    
    private synchronized void sendMessage(Object response)
    {
	try 
	{
	    String msg = ServerClientInteropManager.toResponseJSON(response);
	    parentSession.getBasicRemote().sendText(msg);
	}
	catch (IOException ex) {
	    FLAIRLogger.get().error("Couldn't send message to session " + parentSession.getId() + ". Exception - " + ex.getMessage());
	}
    }
    
    private AbstractPipelineOperation performWebSearchJob(String query, Language lang, int numResults)
    {
	AbstractPipelineOperation op = MasterJobPipeline.get().performWebSearch(lang, query, numResults);
	return op;
    }
    
    private AbstractPipelineOperation performDocumentParsingJob(Language lang, List<AbstractDocumentSource> docSources)
    {
	KeywordSearcherInput keywords;
	if (activeKeywords != null)
	    keywords = activeKeywords;
	else
	    keywords = new KeywordSearcherInput(DefaultVocabularyList.get(lang));
	
	AbstractPipelineOperation op = MasterJobPipeline.get().performDocumentParsing(lang, docSources, keywords);
	return op;
    }
    
    private void sendErrorResponse(BasicInteropMessage.MessageType source, String errorString)
    {
	FLAIRLogger.get().error(errorString);
	sendMessage(new ServerErrorResponse(source, errorString));
    }
    
    public synchronized void handleMessage(String message)
    {
	FLAIRLogger.get().info("Received request from client. Message: " + message);
	BasicInteropMessage.MessageType requestType = ServerClientInteropManager.getRequestType(message);
	
	switch (requestType)
	{
	    case CANCEL_JOB:
	    {
		CancelJobRequest req = ServerClientInteropManager.toCancelJobRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		if (op == null)
		    sendErrorResponse(requestType, "Invalid cancellation request. No operation with ID " + req.jobID);
		else
		    op.cancel();
		
		break;
	    }
	    
	    case SET_KEYWORDS:
	    {
		SetKeywordsRequest req = ServerClientInteropManager.toSetKeywordsRequest(message);
		
		// remove the active keywords if it's an empty request
		if (req.keywords.isEmpty())
		    activeKeywords = null;
		else
		    activeKeywords = new KeywordSearcherInput(req.keywords);
		
		break;
	    }
	    
	    case PERFORM_SEARCH:
	    {
		PerformSearchRequest req = ServerClientInteropManager.toPerformSearchRequest(message);
		if (req.query.length() == 0)
		     sendErrorResponse(requestType, "Empty search query");
		else
		{
		    AbstractPipelineOperation op = performWebSearchJob(req.query, req.language, req.numResults);
		    String id = registerOperation(op, requestType);

		    sendMessage(new PerformSearchResponse(id));
		    op.begin();
		}
		
		break;
	    }
	    
	    case FETCH_SEARCH_RESULTS:
	    {
		FetchSearchResultsRequest req = ServerClientInteropManager.toFetchSearchResultsRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		
		if (op == null)
		    sendErrorResponse(requestType, "Invalid fetch search results request. No operation with ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.WEB_SEARCH_CRAWL)
		    sendErrorResponse(requestType, "Invalid fetch search results request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    List<SearchResult> searchResults = (List<SearchResult>)output;
		    List<SearchResult> toSend = new ArrayList<>();
		    
		    if (req.start > searchResults.size())
			sendErrorResponse(requestType, "Invalid fetch search results request. Start index " + req.start + " > available results (" + searchResults.size() + ")");
		    else
		    {
			if (req.start == -1 && req.count == -1)
			{
			    // return all
			    for (SearchResult itr : searchResults)
				toSend.add(itr);
			}
			else
			{
			    for (int i = req.start - 1; i < searchResults.size() && i < req.start - 1 + req.count; i++)
				toSend.add(searchResults.get(i)); 
			}
			
			sendMessage(new FetchSearchResultsResponse(req.jobID, toSend));
		    }
		}
		
		break;
	    }
	    
	    case PARSE_SEARCH_RESULTS:
	    {
		ParseSearchResultsRequest req = ServerClientInteropManager.toParseSearchResultsRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		
		if (op == null)
		    sendErrorResponse(requestType, "Invalid parse search results request. No invalid web search operation ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.WEB_SEARCH_CRAWL)
		    sendErrorResponse(requestType, "Invalid parse search results request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    List<SearchResult> searchResults = (List<SearchResult>)output;
		    List<AbstractDocumentSource> docSources = new ArrayList<>();
		    
		    if (searchResults.isEmpty())
			sendErrorResponse(requestType, "Invalid parse search results request. Operation with ID " + req.jobID + " has zero results");
		    else
		    {
			for (SearchResult itr : searchResults)
			    docSources.add(new SearchResultDocumentSource(itr));
		    
			AbstractPipelineOperation newOp = performDocumentParsingJob(searchResults.get(0).getLanguage(), docSources);
			String id = registerOperation(newOp, requestType);

			sendMessage(new ParseSearchResultsResponse(id));
			newOp.begin();
		    }
		    
		}
		
		break;
	    }
	    
	    case FETCH_PARSED_DATA:
	    {
		FetchParsedDataRequest req = ServerClientInteropManager.toFetchParsedDataRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		
		if (op == null)
		    sendErrorResponse(requestType, "Invalid fetch parsed data request. No operation with ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.PARSE_DOCUMENTS)
		    sendErrorResponse(requestType, "Invalid fetch parsed data request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    DocumentCollection parsedDocs = (DocumentCollection)output;
		    List<AbstractDocument> toSend = new ArrayList<>();
		    
		    if (req.start > parsedDocs.size())
			sendErrorResponse(requestType, "Invalid fetch parsed data request. Start index " + req.start + " > available results (" + parsedDocs.size() + ")");
		    else
		    {
			if (req.start == -1 && req.count == -1)
			{
			    // return all
			    for (AbstractDocument itr : parsedDocs)
				toSend.add(itr);
			}
			else
			{
			    for (int i = req.start - 1; i < parsedDocs.size() && i < req.start - 1 + req.count; i++)
				toSend.add(parsedDocs.get(i)); 
			}
			
			sendMessage(new FetchParsedDataResponse(req.jobID, toSend));
		    }
		}
		
		break;
	    }
	    
	    case FETCH_PARSED_VISUALISATION_DATA:
	    {
		FetchParsedVisualisationDataRequest req = ServerClientInteropManager.toFetchParsedVisualisationDataRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		
		if (op == null)
		    sendErrorResponse(requestType, "Invalid fetch parsed data request. No operation with ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.PARSE_DOCUMENTS)
		    sendErrorResponse(requestType, "Invalid fetch parsed data request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    DocumentCollection parsedDocs = (DocumentCollection)output;
		    sendMessage(new FetchParsedVisualisationDataResponse(req.jobID, parsedDocs));
		}
		
		break;
	    }
	}
    }
    
    public synchronized void release()
    {
	if (valid == false)
	    return;
	
	valid = false;
	
	if (lastQueuedOperation != null)
	    lastQueuedOperation.cancel();
	
	for (Entry<String, AbstractPipelineOperation> itr : idTable.entrySet())
	{
	    if (itr.getValue().isCompleted() == false)
	    {
		FLAIRLogger.get().warn("Pipeline operation is still executing at the time of shutdown. Status: " + itr.getValue().toString());
		itr.getValue().cancel();
	    }
	}
	
	lastQueuedOperation = null;
	idTable.clear();
	operationTable.clear();
    }
    
    public synchronized boolean isValid() {
	return valid;
    }
}

