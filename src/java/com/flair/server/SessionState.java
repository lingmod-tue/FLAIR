/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocument;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.DocumentCollection;
import com.flair.parser.SearchResultDocumentSource;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.AbstractPipelineOperationCompletionListener;
import com.flair.taskmanager.PipelineOperationType;
import com.flair.utilities.FLAIRLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private boolean							valid;			// invalidated after release()
    
    final class SessionOperationCompletionListener implements AbstractPipelineOperationCompletionListener
    {
	private final String				    opID;
	private final BasicInteropMessage.MessageType	    reqType;

	public SessionOperationCompletionListener(String opID, BasicInteropMessage.MessageType reqType) {
	    this.opID = opID;
	    this.reqType = reqType;
	}

	@Override
	public void handleCompletion(AbstractPipelineOperation source) 
	{
	    if (isValid()) {
		sendMessage(new JobCompleteResponse(reqType, opID));
	    }
	}
    }
    
    public SessionState(Session parent)
    {
	parentSession = parent;
	lastQueuedOperation = null;
	idTable = new HashMap<>();
	operationTable = new HashMap<>();
	valid = true;
    }
    
    private String generateUID() {
	return UUID.randomUUID().toString();
    }
    
    private String registerOperation(AbstractPipelineOperation operation, BasicInteropMessage.MessageType requestType)
    {
	if (lastQueuedOperation != null && lastQueuedOperation.isCompleted() == false)
	    throw new IllegalStateException("New operation started before previous operation was complete");
	
	String id = generateUID();
	idTable.put(id, operation);
	operationTable.put(operation, id);
	lastQueuedOperation = operation;
	operation.registerCompletionListener(new SessionOperationCompletionListener(id, requestType));
	return id;
    }
    
    private synchronized void sendMessage(Object response)
    {
	try {
	    parentSession.getBasicRemote().sendText(ServerClientInteropManager.toResponseJSON(response));
	}
	catch (IOException ex) {
	    FLAIRLogger.get().error("Couldn't send message to session " + parentSession + ". Exception - " + ex.getMessage());
	}
    }
    
    private AbstractPipelineOperation performWebSearchJob(String query, Language lang, int numResults)
    {
	AbstractPipelineOperation op = null;
	return op;
    }
    
     private AbstractPipelineOperation performDocumentParsingJob(List<AbstractDocumentSource> docs)
    {
	AbstractPipelineOperation op = null;
	return op;
    }
    
    
    public synchronized void handleMessage(String message)
    {
	BasicInteropMessage.MessageType responseType = ServerClientInteropManager.getRequestType(message);
	switch (responseType)
	{
	    case CANCEL_JOB:
	    {
		CancelJobRequest req = ServerClientInteropManager.toCancelJobRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		if (op == null)
		    FLAIRLogger.get().error("Invalid cancellation request. No operation with ID " + req.jobID);
		else
		    op.cancel();
		
		break;
	    }
	    
	    case PERFORM_SEARCH:
	    {
		PerformSearchRequest req = ServerClientInteropManager.toPerformSearchRequest(message);
		AbstractPipelineOperation op = performWebSearchJob(req.query, req.language, req.numResults);
		String id = registerOperation(op, responseType);
		
		sendMessage(new PerformSearchResponse(id));
		op.begin();
		
		break;
	    }
	    
	    case FETCH_SEARCH_RESULTS:
	    {
		FetchSearchResultsRequest req = ServerClientInteropManager.toFetchSearchResultsRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		
		if (op == null)
		    FLAIRLogger.get().error("Invalid fetch search results request. No operation with ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.WEB_SEARCH_CRAWL)
		    FLAIRLogger.get().error("Invalid fetch search results request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    List<SearchResult> searchResults = (List<SearchResult>)output;
		    List<SearchResult> toSend = new ArrayList<>();
		    
		    if (req.start > searchResults.size())
			FLAIRLogger.get().error("Invalid fetch search results request. Start index " + req.start + " > available results (" + searchResults.size() + ")");
		    else
		    {
			for (int i = req.start - 1; i < searchResults.size() && i < req.start - 1 + req.count; i++)
			    toSend.add(searchResults.get(i));
			
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
		    FLAIRLogger.get().error("Invalid parse search results request. No invalid web search operation ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.WEB_SEARCH_CRAWL)
		    FLAIRLogger.get().error("Invalid parse search results request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    List<SearchResult> searchResults = (List<SearchResult>)output;
		    List<AbstractDocumentSource> docSources = new ArrayList<>();
		    
		    for (SearchResult itr : searchResults)
			docSources.add(new SearchResultDocumentSource(itr));
		    
		    AbstractPipelineOperation newOp = performDocumentParsingJob(docSources);
		    String id = registerOperation(newOp, responseType);
		
		    sendMessage(new ParseSearchResultsResponse(id));
		    newOp.begin();
		}
		
		break;
	    }
	    
	    case FETCH_PARSED_DATA:
	    {
		FetchParsedDataRequest req = ServerClientInteropManager.toFetchParsedDataRequest(message);
		AbstractPipelineOperation op = idTable.get(req.jobID);
		
		if (op == null)
		    FLAIRLogger.get().error("Invalid fetch parsed data request. No operation with ID " + req.jobID);
		else if (op.getType() != PipelineOperationType.PARSE_DOCUMENTS)
		    FLAIRLogger.get().error("Invalid fetch parsed data request. Operation with ID " + req.jobID + " is of type " + op.getType());
		else
		{
		    Object output = op.getOutput();
		    DocumentCollection parsedDocs = (DocumentCollection)output;
		    List<AbstractDocument> toSend = new ArrayList<>();
		    
		    if (req.start > parsedDocs.size())
			FLAIRLogger.get().error("Invalid fetch parsed data request. Start index " + req.start + " > available results (" + parsedDocs.size() + ")");
		    else
		    {
			for (int i = req.start - 1; i < parsedDocs.size() && i < req.start - 1 + req.count; i++)
			    toSend.add(parsedDocs.get(i));
			
			sendMessage(new FetchParsedDataResponse(req.jobID, toSend));
		    }
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
	
	lastQueuedOperation = null;
	idTable.clear();
	operationTable.clear();
    }
    
    public synchronized boolean isValid() {
	return valid;
    }
}

