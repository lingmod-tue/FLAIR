//=========================== PIPELINE =================================//

var PIPELINE = null;                            // main websocket
var PIPELINE_CONNECTED = false;                 // socket status

var LAST_QUEUED_JOB_ID = null;                  // string ID of the last job ID returned by the server
var LAST_QUEUED_JOB_TYPE = null;                // type of the last job, as passed by the server
var LAST_SENT_REQUEST_TYPE = null;		// type of the last request sent to the server

//============== BEGIN PIPELINE INTERNAL FUNCTIONS
pipeline_internal_onMessage = function(event)
{
    if (PIPELINE_CONNECTED === false || PIPELINE.readyState !== 1)
    {
	console.log("Eh? Received response from server over a broken/closed pipeline. Received data: " + event.data);
	return;
    }
    
    var msg = JSON.parse(event.data);
    console.log("Received message from server. Type: " + msg.baseData.type);

    switch (msg.baseData.type)
    {
	case "NEW_JOB":
	    var reqType = msg.request;
	    if (reqType !== LAST_SENT_REQUEST_TYPE)
		console.log("New job response doesn't match with request. Expected " + LAST_SENT_REQUEST_TYPE + ", received " + reqType);
	    else
	    {
		LAST_QUEUED_JOB_ID = msg.jobID;
		LAST_QUEUED_JOB_TYPE = reqType;
	    }
	    
	    break;
	    
	case "JOB_COMPLETE":
	    var reqType = msg.request;
	    if (reqType !== LAST_QUEUED_JOB_TYPE)
		console.log("Job complete response doesn't match with last queued job. Expected " + LAST_QUEUED_JOB_TYPE + ", received " + reqType);
	    else
	    {
		switch (reqType)
		{
		    case "PERFORM_SEARCH":
			webRanker_handleCompletion_webSearch(msg.jobID);
			break;
			
		    case "PARSE_SEARCH_RESULTS":
			webRanker_handleCompletion_parseSearchResults(msg.jobID);
			break;
			
		    default:
			console.log("Invalid type for job complete response. Received " + reqType);
			break;
		}
	    }
	    
	    break;
	    
	case "FETCH_SEARCH_RESULTS":
	    if (LAST_SENT_REQUEST_TYPE !== msg.baseData.type)
		console.log("Server response doesn't match with last request. Expected " + LAST_SENT_REQUEST_TYPE + ", received " + msg.baseData.type);
	    else
		webRanker_handleFetch_searchResults(msg.searchResults);
	    
	    break;
	    
	case "FETCH_PARSED_DATA":
	     if (LAST_SENT_REQUEST_TYPE !== msg.baseData.type)
		console.log("Server response doesn't match with last request. Expected " + LAST_SENT_REQUEST_TYPE + ", received " + msg.baseData.type);
	    else
		webRanker_handleFetch_parsedData(msg.parsedDocs);
	    
	    break;
    }
};

pipeline_internal_onOpen = function(event)
{
    PIPELINE_CONNECTED = true;
    
    console.log("Pipeline open");
};  

pipeline_internal_onClose = function(event)
{
    PIPELINE = null;
    PIPELINE_CONNECTED = false;
    LAST_QUEUED_JOB_ID = null;
    LAST_QUEUED_JOB_TYPE = null;
    LAST_SENT_REQUEST_TYPE= null;
    
    console.log("Pipeline closed. Close code: " + event.code + (event.reason === "" ? "" : ", Reason: " + event.reason));
};  

pipeline_internal_onError = function (event)
{
    console.log("Pipeline error: " + event);
};

function pipeline_internal_createRequestMessage(reqType)
{
    var request = null;
    
    switch (reqType)
    {
	case "CANCEL_JOB":
	case "PARSE_SEARCH_RESULTS":
	    request = {
		baseData: {
		    source: "CLIENT",
		    type: reqType
		},
		jobID: ""
	    };
	    
	    break;
	    
	case "PERFORM_SEARCH":
	    request = {
		baseData: {
		    source: "CLIENT",
		    type: reqType
		},
		query: "",
		language: "",
		numResults: 0
	    };
	    
	    break;
	    
	case "FETCH_SEARCH_RESULTS":
   	case "FETCH_PARSED_DATA":
	    request = {
		baseData: {
		    source: "CLIENT",
		    type: reqType
		},
		jobID: "",
		start: 0,
		count: 0
	    };
	    
	    break;
	    
	default:
	    console.log("Couldn't create request message for type " + reqType);
	    break;
    }
    
    return request;
}

function pipeline_internal_sendRequest(request)
{
    if (PIPELINE_CONNECTED === false || PIPELINE === null || PIPELINE.readyState !== 1) 
    {
	console.log("Couldn't send request - Broken/closed pipeline");
	return false;
    }
    else
    {
	var json = JSON.stringify(request);
	LAST_SENT_REQUEST_TYPE = request.baseData.type;
	PIPELINE.send(json);
	console.log("Send request to server. Data: " + json);
	return true;
    }
}
//=============== END PIPELINE INTERNAL FUNCTIONS


function pipeline_init()
{
    if (PIPELINE !== null || PIPELINE_CONNECTED === true)
    {
	console.log("Pipline already initialized");
	return false;
    }
    else 
    {
	var pipelineURI = "ws://" + document.location.host + document.location.pathname + "webranker";
	console.log("Attempting to establish connection with WebSocket @ " + pipelineURI);
	
	if ('WebSocket' in window)
	    PIPELINE = new WebSocket(pipelineURI);
	else if ('MozWebSocket' in window)
	    PIPELINE = new MozWebSocket(pipelineURI);
	else
	{
	    console.log("FATAL: No WebSockets support");
	    alert("This browser does not support WebSockets. Please upgrade to a newer version or switch to a browser that supports WebSockets.");
	    return false;
	}
	 
	PIPELINE.onopen = pipeline_internal_onOpen;
	PIPELINE.onclose = pipeline_internal_onClose;
	PIPELINE.onmessage = pipeline_internal_onMessage;
	PIPELINE.onerror = pipeline_internal_onError;
	
	window.onbeforeunload = function() {
	    pipeline_deinit();  
	};
	
	console.log("Pipeline initialized");
	return true;
    }
}

function pipeline_deinit()
{
    if (PIPELINE === null || PIPELINE_CONNECTED === false || PIPELINE.readyState === 3)
    {
	console.log("Pipline already deinitalized");
	return false;
    }
    else 
    {
	PIPELINE.close();
	PIPELINE = null;
	PIPELINE_CONNECTED = false;
	LAST_QUEUED_JOB_ID = null;
	LAST_QUEUED_JOB_TYPE = null;
	LAST_SENT_REQUEST_TYPE= null;

	console.log("Pipeline deinitialized");
	return true;
    }
}

function pipeline_request_cancelLastJob()
{
    if (LAST_QUEUED_JOB_ID !== null)
    {
	var req = pipeline_internal_createRequestMessage("CANCEL_JOB");
	req.jobID = LAST_QUEUED_JOB_ID;
	return pipeline_internal_sendRequest(req);
    }
    else
	return true;
}

function pipeline_request_performSearch(query, lang, numResults)
{
    var req = pipeline_internal_createRequestMessage("PERFORM_SEARCH");
    req.query = query;
    req.language = lang;
    req.numResults = numResults;
    return pipeline_internal_sendRequest(req);
}

function pipeline_request_parseSearchResults(jobID)
{
    var req = pipeline_internal_createRequestMessage("PARSE_SEARCH_RESULTS");
    req.jobID = jobID;
    return pipeline_internal_sendRequest(req);
}

function pipeline_request_fetchSearchResults(start, count)
{
    if (LAST_QUEUED_JOB_TYPE !== "PERFORM_SEARCH")
    {
	console.log("Fetch search results request called out of order. Last queued job was " + LAST_QUEUED_JOB_TYPE);
	return false;
    }
    else
    {
	var req = pipeline_internal_createRequestMessage("FETCH_SEARCH_RESULTS");
	req.jobID = LAST_QUEUED_JOB_ID;
	req.start = start;
	req.count = count;
	return pipeline_internal_sendRequest(req);
    }
}

function pipeline_request_fetchParsedData(start, count)
{
    if (LAST_QUEUED_JOB_TYPE !== "PARSE_SEARCH_RESULTS")
    {
	console.log("Fetch parsed data request called out of order. Last queued job was " + LAST_QUEUED_JOB_TYPE);
	return false;
    }
    else
    {
	var req = pipeline_internal_createRequestMessage("FETCH_PARSED_DATA");
	req.jobID = LAST_QUEUED_JOB_ID;
	req.start = start;
	req.count = count;
	return pipeline_internal_sendRequest(req);
    }
}