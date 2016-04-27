//======================== FLAIR WEBRANKER PLUMBiNG =====================//
FLAIR.createNS("FLAIR.PLUMBING");
FLAIR.createNS("FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE");
FLAIR.createNS("FLAIR.PLUMBING.CONSTANTS.MESSAGE.SOURCE");

//================ FLAIR.PLUMBING.CONSTANTS.MESSAGE =====================//
FLAIR.PLUMBING.CONSTANTS.MESSAGE.SOURCE.CLIENT					= "CLIENT";

FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.CANCEL_JOB				= "CANCEL_JOB";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.NEW_JOB					= "NEW_JOB";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.JOB_COMPLETE				= "JOB_COMPLETE";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PERFORM_SEARCH				= "PERFORM_SEARCH";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PARSE_SEARCH_RESULTS			= "PARSE_SEARCH_RESULTS";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_SEARCH_RESULTS			= "FETCH_SEARCH_RESULTS";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_DATA				= "FETCH_PARSED_DATA";
FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_VISUALISATION_DATA		= "FETCH_PARSED_VISUALISATION_DATA";

//=============================== FLAIR.PLUMBING =============================//
FLAIR.PLUMBING.PIPELINE = function(webSearch_complete, parseSearchResults_complete, searchResults_fetch, parsedData_fetch, parsedVisData_fetch) {
    // PRIVATE VARS
    var socket = null;				// main websocket
    var connected = false;			// socket status
    
    var last_queued_job_id = null;		// string ID of the last job ID returned by the server
    var last_queued_job_type = null;		// type of the last job, as passed by the server
    var last_sent_request_type = null;		// type of the last request sent to the server
    
    var handlers = {
	completion: {
	    webSearch: webSearch_complete,
	    parseSearchResults: parseSearchResults_complete
	},
	fetch: {
	    searchResults: searchResults_fetch,
	    parsedData: parsedData_fetch,
	    parsedVisData: parsedVisData_fetch
	}
    };
    
    // PRIVATE INTERFACE
    var private_onmessage = function(event) {
	if (connected === false || socket.readyState !== 1)
	{
	    console.log("Eh? Received response from server over a broken/closed pipeline. Received data: " + event.data);
	    return;
	}

	var msg = JSON.parse(event.data);
	console.log("Received message from server. Type: " + msg.baseData.type);

	switch (msg.baseData.type)
	{
	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.NEW_JOB:
		var reqType = msg.request;
		if (reqType !== last_sent_request_type)
		    console.log("New job response doesn't match with request. Expected " + last_sent_request_type + ", received " + reqType);
		else
		{
		    last_queued_job_id = msg.jobID;
		    last_queued_job_type = reqType;
		}

		break;

	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.JOB_COMPLETE:
		var reqType = msg.request;
		if (reqType !== last_queued_job_type)
		    console.log("Job complete response doesn't match with last queued job. Expected " + last_queued_job_type + ", received " + reqType);
		else
		{
		    switch (reqType)
		    {
			case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PERFORM_SEARCH:
			    handlers.completion.webSearch(msg.jobID, msg.totalResultsFetched);
			    break;

			case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PARSE_SEARCH_RESULTS:
			    handlers.completion.parseSearchResults(msg.jobID, msg.totalResultsParsed);
			    break;

			default:
			    console.log("Invalid type for job complete response. Received " + reqType);
			    break;
		    }
		}

		break;

	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_SEARCH_RESULTS:
		if (last_sent_request_type !== msg.baseData.type)
		    console.log("Server response doesn't match with last request. Expected " + last_sent_request_type + ", received " + msg.baseData.type);
		else
		    handlers.fetch.searchResults(msg.jobID, msg.searchResults);

		break;

	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_DATA:
		if (last_sent_request_type !== msg.baseData.type)
		    console.log("Server response doesn't match with last request. Expected " + last_sent_request_type + ", received " + msg.baseData.type);
		else
		    handlers.fetch.parsedData(msg.jobID, msg.parsedDocs);

		break;
		
	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_VISUALISATION_DATA:
		if (last_sent_request_type !== msg.baseData.type)
		    console.log("Server response doesn't match with last request. Expected " + last_sent_request_type + ", received " + msg.baseData.type);
		else
		    handlers.fetch.parsedVisData(msg.jobID, msg.csvTable);

		break;
	}
    };
    var private_onopen = function(event) {
	connected = true;
	console.log("Pipeline open");
    };  
    var private_onclose = function(event) {
	socket = null;
	connected = false;
	last_queued_job_id = null;
	last_queued_job_type = null;
	last_sent_request_type = null;
	
	handlers = [];

	console.log("Pipeline closed. Close code: " + event.code + (event.reason === "" ? "" : ", Reason: " + event.reason));
    };  
    var private_onerror = function (event) {
	console.log("Pipeline error");
    }; 
    
    var private_createRequestMessage = function(reqType) {
	var request = null;

	switch (reqType)
	{
	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.CANCEL_JOB:
	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PARSE_SEARCH_RESULTS:
	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_VISUALISATION_DATA:
		request = {
		    baseData: {
			source: FLAIR.PLUMBING.CONSTANTS.MESSAGE.SOURCE.CLIENT,
			type: reqType
		    },
		    jobID: ""
		};

		break;

	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PERFORM_SEARCH:
		request = {
		    baseData: {
			source: FLAIR.PLUMBING.CONSTANTS.MESSAGE.SOURCE.CLIENT,
			type: reqType
		    },
		    query: "",
		    language: "",
		    numResults: 0
		};

		break;

	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_SEARCH_RESULTS:
	    case FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_DATA:
		request = {
		    baseData: {
			source: FLAIR.PLUMBING.CONSTANTS.MESSAGE.SOURCE.CLIENT,
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
    };
    var private_sendRequest = function(request) {
	if (connected === false || socket === null || socket.readyState !== 1) 
	{
	    console.log("Couldn't send request - Broken/closed pipeline");
	    return false;
	}
	else
	{
	    var json = JSON.stringify(request);
	    last_sent_request_type = request.baseData.type;
	    socket.send(json);
	    console.log("Send request to server. Data: " + json);
	    return true;
	}
    };

    // PUBLIC INTERFACE
    this.init = function() {
	if (socket !== null || connected === true)
	{
	    console.log("Pipline already initialized");
	    return false;
	}
	else 
	{
	    var pipelineURI = "ws://" + document.location.host + document.location.pathname + "webranker";
	    console.log("Attempting to establish connection with WebSocket @ " + pipelineURI);

	    if ('WebSocket' in window)
		socket = new WebSocket(pipelineURI);
	    else if ('MozWebSocket' in window)
		socket = new MozWebSocket(pipelineURI);
	    else
	    {
		console.log("FATAL: No WebSockets support");
		alert("This browser does not support WebSockets. Please upgrade to a newer version or switch to a browser that supports WebSockets.");
		return false;
	    }

	    socket.onopen = private_onopen;
	    socket.onclose = private_onclose;
	    socket.onmessage = private_onmessage;
	    socket.onerror = private_onerror;

	    console.log("Pipeline initialized");
	    return true;
	}
    };
    this.deinit = function() {
	if (socket === null || connected === false || socket.readyState === 3)
	{
	    console.log("Pipline already deinitalized");
	    return false;
	}
	else 
	{
	    socket.close();
	    socket = null;
	    connected = false;
	    last_queued_job_id = null;
	    last_queued_job_type = null;
	    last_sent_request_type = null;

	    handlers = [];

	    console.log("Pipeline deinitialized");
	    return true;
	}
    };
    
    this.cancelLastJob = function() {
	if (last_queued_job_id !== null)
	{
	    var req = private_createRequestMessage(FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.CANCEL_JOB);
	    req.jobID = last_queued_job_id;
	    return private_sendRequest(req);
	}
	else
	    return true;
    };
    this.performSearch = function(query, lang, numResults) {
	var req = private_createRequestMessage(FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PERFORM_SEARCH);
	req.query = query;
	req.language = lang;
	req.numResults = numResults;
	return private_sendRequest(req);
    };
    this.parseSearchResults = function(jobID) {
	var req = private_createRequestMessage(FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PARSE_SEARCH_RESULTS);
	req.jobID = jobID;
	return private_sendRequest(req);
    };
    this.fetchSearchResults = function(start, count) {
	if (last_queued_job_type !== FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PERFORM_SEARCH)
	{
	    console.log("Fetch search results request called out of order. Last queued job was " + last_queued_job_type);
	    return false;
	}
	else
	{
	    var req = private_createRequestMessage(FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_SEARCH_RESULTS);
	    req.jobID = last_queued_job_id;
	    req.start = start;
	    req.count = count;
	    return private_sendRequest(req);
	}
    };
    this.fetchParsedData = function(start, count) {
	if (last_queued_job_type !== FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PARSE_SEARCH_RESULTS)
	{
	    console.log("Fetch parsed data request called out of order. Last queued job was " + last_queued_job_type);
	    return false;
	}
	else
	{
	    var req = private_createRequestMessage(FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_DATA);
	    req.jobID = last_queued_job_id;
	    req.start = start;
	    req.count = count;
	    return private_sendRequest(req);
	}
    };
    this.fetchParsedVisData = function() {
	if (last_queued_job_type !== FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.PARSE_SEARCH_RESULTS)
	{
	    console.log("Fetch parsed visualisation data request called out of order. Last queued job was " + last_queued_job_type);
	    return false;
	}
	else
	{
	    var req = private_createRequestMessage(FLAIR.PLUMBING.CONSTANTS.MESSAGE.TYPE.FETCH_PARSED_VISUALISATION_DATA);
	    req.jobID = last_queued_job_id;
	    return private_sendRequest(req);
	}
    };
};