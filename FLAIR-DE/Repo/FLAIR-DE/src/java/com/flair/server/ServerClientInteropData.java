/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocument;
import com.flair.parser.CompactDocumentData;
import com.flair.parser.DocumentCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Represents a message passed between the server and the client
 * Limited use of polymorphism to prevent issues during (de)serialization
 * @author shadeMe
 */
class BasicInteropMessage
{
    public enum MessageSource
    {
	@SerializedName("CLIENT")
	CLIENT,		// requests
	@SerializedName("SERVER")
	SERVER		// responses/push messages
    }
    
    public enum MessageType
    {
	// request-only
	@SerializedName("CANCEL_JOB")
	CANCEL_JOB,
	@SerializedName("SET_KEYWORDS")
	SET_KEYWORDS,
	
	// response-only
	@SerializedName("NEW_JOB")
	NEW_JOB,
	@SerializedName("JOB_COMPLETE")
	JOB_COMPLETE,
	@SerializedName("SERVER_ERROR")
	SERVER_ERROR,
	@SerializedName("CUSTOM_CORPUS_UPLOADED")
	CUSTOM_CORPUS_UPLOADED,
	
	// bi-directional
	@SerializedName("PERFORM_SEARCH")
	PERFORM_SEARCH,
	@SerializedName("FETCH_SEARCH_RESULTS")
	FETCH_SEARCH_RESULTS,
	@SerializedName("PARSE_SEARCH_RESULTS")
	PARSE_SEARCH_RESULTS,
	@SerializedName("FETCH_PARSED_DATA")
	FETCH_PARSED_DATA,
	@SerializedName("FETCH_PARSED_VISUALISATION_DATA")
	FETCH_PARSED_VISUALISATION_DATA,
	@SerializedName("PARSE_CUSTOM_CORPUS")
	PARSE_CUSTOM_CORPUS,
	;
	
	@Override
	public String toString() {
	    return name();
	}
    }
    
    public final MessageSource		    source;
    public final MessageType		    type;

    public BasicInteropMessage(MessageSource source, MessageType message)
    {
	this.source = source;
	this.type = message;
    }
    
    public boolean isRequest() {
	return source == MessageSource.CLIENT;
    }
    
    public boolean isResponse() {
	return source == MessageSource.SERVER;
    }
}

// doesn't have a corresponding response
class CancelJobRequest
{
    public final BasicInteropMessage	baseData;
    public final String			jobID;

    public CancelJobRequest(String jobID) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.CANCEL_JOB);
	this.jobID = jobID;
    }
}

class SetKeywordsRequest
{
    public final BasicInteropMessage		baseData;
    public final List<String>			keywords;

    public SetKeywordsRequest() 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.SET_KEYWORDS);
	this.keywords = new ArrayList<>();
    }
}

class JobCompleteResponse
{
    public final BasicInteropMessage			    baseData;
    public final BasicInteropMessage.MessageType	    request;	    // the message associated with the job
    public final String					    jobID;

    public JobCompleteResponse(BasicInteropMessage.MessageType requestMessage, String jobID) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.JOB_COMPLETE);
	this.request = requestMessage;
	this.jobID = jobID;
    }
}

class ServerErrorResponse
{
    public final BasicInteropMessage			    baseData;
    public final BasicInteropMessage.MessageType	    source;	    // the message associated with the error
    public final String					    errorMessage;

    public ServerErrorResponse(BasicInteropMessage.MessageType sourceMsg, String errorString)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.SERVER_ERROR);
	this.source = sourceMsg;
	this.errorMessage = errorString;
    }
}

class CustomCorpusUploadedResponse
{
    public final BasicInteropMessage			    baseData;
    public final int					    filesUploaded;	// no of files that were successfully uploaded

    public CustomCorpusUploadedResponse(int uploadCount)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.CUSTOM_CORPUS_UPLOADED);
	filesUploaded = uploadCount;
    }
}

class WebSearchCompleteResponse
{
    public final BasicInteropMessage			    baseData;
    public final BasicInteropMessage.MessageType	    request;			// the message associated with the job
    public final String					    jobID;
    public final int					    totalResultsFetched;	// no of results crawled/fetched

    public WebSearchCompleteResponse(String jobID, int totalResults) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.JOB_COMPLETE);
	this.request = BasicInteropMessage.MessageType.PERFORM_SEARCH;
	this.jobID = jobID;
	this.totalResultsFetched = totalResults;
    }
}

class ParseSearchResultsCompleteResponse
{
    public final BasicInteropMessage			    baseData;
    public final BasicInteropMessage.MessageType	    request;		// the message associated with the job
    public final String					    jobID;
    public final int					    totalResultsParsed;	// no of search results parsed

    public ParseSearchResultsCompleteResponse(String jobID, int totalResults) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.JOB_COMPLETE);
	this.request = BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS;
	this.jobID = jobID;
	this.totalResultsParsed = totalResults;
    }
}

class ParseCustomCorpusCompleteResponse
{
    public final BasicInteropMessage			    baseData;
    public final BasicInteropMessage.MessageType	    request;		// the message associated with the job
    public final String					    jobID;
    public final int					    totalFilesParsed;	// no of files parsed

    public ParseCustomCorpusCompleteResponse(String jobID, int totalResults) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.JOB_COMPLETE);
	this.request = BasicInteropMessage.MessageType.PARSE_CUSTOM_CORPUS;
	this.jobID = jobID;
	this.totalFilesParsed = totalResults;
    }
}

class PerformSearchRequest
{
    public final BasicInteropMessage	    baseData;
    public final String			    query;
    public final Language		    language;
    public final int			    numResults;

    public PerformSearchRequest(String query, Language language, int numResults) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.PERFORM_SEARCH);
	this.query = query;
	this.language = language;
	this.numResults = numResults;
    }
}

class PerformSearchResponse
{
    public final BasicInteropMessage			baseData;
    public final BasicInteropMessage.MessageType	request;	    // the message associated with the job
    public final String					jobID;
    
    public PerformSearchResponse(String jobID) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.NEW_JOB);
	request = BasicInteropMessage.MessageType.PERFORM_SEARCH;
	this.jobID = jobID;
    }
}

class FetchSearchResultsRequest
{
    public final BasicInteropMessage	    baseData;
    public final String			    jobID;	    // corresponding to the search operation
    public final int			    start;	    // nth result from which to fetch
    public final int			    count;	    // num of results to fetch from the nth result
							    // if start == count == -1, return all

    public FetchSearchResultsRequest(String jobID, int start, int count)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS);
	this.jobID = jobID;
	this.start = start;
	this.count = count;
    }
}

class FetchSearchResultsResponse
{
    final class CompactSearchResultData
    {
	public final String		title;
	public final String		URL;
	public final String		displayURL;
	public final String		snippet;
	public final int		rank;
	public final String		text;

	public CompactSearchResultData(SearchResult source) 
	{
	    this.title = source.getTitle();
	    this.URL = source.getURL();
	    this.displayURL = source.getDisplayURL();
	    this.snippet = source.getSnippet();
	    this.rank = source.getRank();
	    this.text = source.getPageText();
	}
    }
    
    public final BasicInteropMessage		    baseData;
    public final String				    jobID;		    // corresponding to the search operation
    public final List<CompactSearchResultData>	    searchResults;	    
    
    public FetchSearchResultsResponse(String jobID, List<SearchResult> results)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS);
	this.jobID = jobID;
	this.searchResults = new ArrayList<>();
	
	for (SearchResult itr : results)
	    searchResults.add(new CompactSearchResultData(itr));
    }
}

class ParseSearchResultsRequest
{
    public final BasicInteropMessage	    baseData;
    public final String			    jobID;	    // corresponding to the search operation

    public ParseSearchResultsRequest(String jobID)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS);
	this.jobID = jobID;
    }
}

class ParseSearchResultsResponse
{
    public final BasicInteropMessage			baseData;
    public final BasicInteropMessage.MessageType	request;	    // the message associated with the job
    public final String					jobID;
    
    public ParseSearchResultsResponse(String jobID) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.NEW_JOB);
	request = BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS;
	this.jobID = jobID;
    }
}

class FetchParsedDataRequest
{
    public final BasicInteropMessage	    baseData;
    public final String			    jobID;	    // corresponding to the parse operation
    public final int			    start;	    // nth result from which to fetch
    public final int			    count;	    // num of results to fetch from the nth result
							    // if start == count == -1, return all

    public FetchParsedDataRequest(String jobID, int start, int count)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.FETCH_PARSED_DATA);
	this.jobID = jobID;
	this.start = start;
	this.count = count;
    }
}


class FetchParsedDataResponse
{
    public final BasicInteropMessage		    baseData;
    public final String				    jobID;		    // corresponding to the parse operation
    public final List<CompactDocumentData>	    parsedDocs;	    
    
    public FetchParsedDataResponse(String jobID, List<AbstractDocument> documents)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.FETCH_PARSED_DATA);
	this.jobID = jobID;
	this.parsedDocs = new ArrayList<>();
	
	for (AbstractDocument itr : documents)
	    parsedDocs.add(new CompactDocumentData(itr));
    }
}

class FetchParsedVisualisationDataRequest
{
    public final BasicInteropMessage	    baseData;
    public final String			    jobID;			    // corresponding to the parse operation
    
    public FetchParsedVisualisationDataRequest(String jobID)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.FETCH_PARSED_VISUALISATION_DATA);
	this.jobID = jobID;
    }
}

class FetchParsedVisualisationDataResponse
{
    public final BasicInteropMessage		    baseData;
    public final String				    jobID;		    // corresponding to the parse operation
    public final String				    csvTable;

    public FetchParsedVisualisationDataResponse(String jobID, DocumentCollection parsedDocs)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.FETCH_PARSED_VISUALISATION_DATA);
	this.jobID = jobID;
	this.csvTable = parsedDocs.generateFrequencyTable();
    }
}

class ParseCustomCorpusRequest
{
    public final BasicInteropMessage	    baseData;
    public final Language		    language;
    
    public ParseCustomCorpusRequest(Language lang)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.PARSE_CUSTOM_CORPUS);
	this.language = lang;
    }
}

class ParseCustomCorpusResponse
{
    public final BasicInteropMessage			baseData;
    public final BasicInteropMessage.MessageType	request;	    // the message associated with the job
    public final String					jobID;
    
    public ParseCustomCorpusResponse(String jobID) 
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.NEW_JOB);
	request = BasicInteropMessage.MessageType.PARSE_CUSTOM_CORPUS;
	this.jobID = jobID;
    }
}

class ServerClientInteropManager
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Gson getGson() {
        return GSON;
    }
    
    public static BasicInteropMessage.MessageType getRequestType(String jsonRequest)
    {
	JsonReader reader = Json.createReader(new StringReader(jsonRequest));
	JsonObject simple = reader.readObject();
	String messageType = simple.getJsonObject("baseData").getString("type");
	
	if (messageType.equals(BasicInteropMessage.MessageType.CANCEL_JOB.toString()))
	    return BasicInteropMessage.MessageType.CANCEL_JOB;
	else if (messageType.equals(BasicInteropMessage.MessageType.SET_KEYWORDS.toString()))
	    return BasicInteropMessage.MessageType.SET_KEYWORDS;
	else if (messageType.equals(BasicInteropMessage.MessageType.PERFORM_SEARCH.toString()))
	    return BasicInteropMessage.MessageType.PERFORM_SEARCH;
	else if (messageType.equals(BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS.toString()))
	    return BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS;
	else if (messageType.equals(BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS.toString()))
	    return BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS;
	else if (messageType.equals(BasicInteropMessage.MessageType.FETCH_PARSED_DATA.toString()))
	    return BasicInteropMessage.MessageType.FETCH_PARSED_DATA;
	else if (messageType.equals(BasicInteropMessage.MessageType.FETCH_PARSED_VISUALISATION_DATA.toString()))
	    return BasicInteropMessage.MessageType.FETCH_PARSED_VISUALISATION_DATA;
	else if (messageType.equals(BasicInteropMessage.MessageType.PARSE_CUSTOM_CORPUS.toString()))
	    return BasicInteropMessage.MessageType.PARSE_CUSTOM_CORPUS;
	else
	    throw new IllegalArgumentException("Invalid request type '" + messageType + "'");
    }
    
    private static void checkMessage(String jsonMessage, BasicInteropMessage.MessageSource source, BasicInteropMessage.MessageType type)
    {
	JsonReader reader = Json.createReader(new StringReader(jsonMessage));
	JsonObject simple = reader.readObject();
	String messageSource = simple.getJsonObject("baseData").getString("source");
	String messageType = simple.getJsonObject("baseData").getString("type");
	
	if (messageSource.equals(source.toString()) == false)
	    throw new IllegalStateException("Invalid messsage source '" + messageSource + "'. Expected '" + source.toString() + "'");
	else if (messageType.equals(type.toString()) == false)
	    throw new IllegalStateException("Invalid messsage type '" + messageType + "'. Expected '" + type.toString() + "'");
    }
    
    
    public static CancelJobRequest toCancelJobRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.CANCEL_JOB);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, CancelJobRequest.class);
    }
    
    public static SetKeywordsRequest toSetKeywordsRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.SET_KEYWORDS);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, SetKeywordsRequest.class);
    }
    
    public static PerformSearchRequest toPerformSearchRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.PERFORM_SEARCH);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, PerformSearchRequest.class);
    }
    
    public static FetchSearchResultsRequest toFetchSearchResultsRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, FetchSearchResultsRequest.class);
    }
    
    public static ParseSearchResultsRequest toParseSearchResultsRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, ParseSearchResultsRequest.class);
    }
    
    public static FetchParsedDataRequest toFetchParsedDataRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.FETCH_PARSED_DATA);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, FetchParsedDataRequest.class);
    }
    
    public static FetchParsedVisualisationDataRequest toFetchParsedVisualisationDataRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.FETCH_PARSED_VISUALISATION_DATA);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, FetchParsedVisualisationDataRequest.class);
    }
    
    public static ParseCustomCorpusRequest toParseCustomCorpusRequest(String requestJSON)
    {
	checkMessage(requestJSON, BasicInteropMessage.MessageSource.CLIENT, BasicInteropMessage.MessageType.PARSE_CUSTOM_CORPUS);
	Gson deserializer = getGson();
	return deserializer.fromJson(requestJSON, ParseCustomCorpusRequest.class);
    }
    
    public static String toResponseJSON(Object response)
    {
	Gson serializer = getGson();
	return serializer.toJson(response);
    }
}