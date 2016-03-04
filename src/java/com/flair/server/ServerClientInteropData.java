/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server;

import com.flair.crawler.SearchResult;
import com.flair.grammar.GrammaticalConstruction;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocument;
import com.flair.parser.DocumentConstructionData;
import com.flair.parser.Occurrence;
import com.flair.parser.SearchResultDocumentSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	CLIENT,		// requests
	SERVER		// responses/push messages
    }
    
    public enum MessageType
    {
	// request-only
	CANCEL_JOB,
	
	// response-only
	NEW_JOB,
	JOB_COMPLETE,
	
	// bi-directional
	PERFORM_SEARCH,
	FETCH_SEARCH_RESULTS,
	PARSE_SEARCH_RESULTS,
	FETCH_PARSED_DATA
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

	public CompactSearchResultData(SearchResult source) 
	{
	    this.title = source.getTitle();
	    this.URL = source.getURL();
	    this.displayURL = source.getDisplayURL();
	    this.snippet = source.getSnippet();
	    this.rank = source.getRank();
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
    final class CompactParsedData
    {
	final class CompactOccurrence
	{
	    public final int		start;
	    public final int		end;
	    public final String		construction;

	    public CompactOccurrence(int start, int end, String construction) 
	    {
		this.start = start;
		this.end = end;
		this.construction = construction;
	    }
	}
	
	private final int			preRank;
	private final int			postRank;

	private final String			title;
	private final String			url;
	private final String			urlToDisplay;
	private final String			snippet;
	private final String			text;

	private final ArrayList<String>		constructions;
	private final ArrayList<Double>		relFrequencies;
	private final ArrayList<Integer>	frequencies;
	private final ArrayList<Double>		tfNorm;
	private final ArrayList<CompactOccurrence>  highlights;
	private final ArrayList<Double>		tfIdf;
	private final double			docLenTfIdf;
	private final double			docLength;

	private final double			readabilityScore;
	private final String			readabilityLevel;
	private final double			readabilityARI;
	private final double			readabilityBennoehr;
	
	private final double			textWeight;
	private final double			rankWeight;
	private final double			gramScore;
	private final double			totalWeight;

	private final int			numChars;
	private final int			numSents;
	private final int			numDeps;

	private final double			avWordLength;
	private final double			avSentLength;
	private final double			avTreeDepth;

	public CompactParsedData(AbstractDocument source) 
	{
	    if (source.isParsed() == false)
		throw new IllegalStateException("Document not flagged as parsed");
	    
	    if (SearchResultDocumentSource.class.isAssignableFrom(source.getDocumentSource().getClass()) == false)
	    {
		title = url = urlToDisplay = snippet = "";
		preRank = -1;
	    }
	    else
	    {
		SearchResultDocumentSource searchSource = (SearchResultDocumentSource)source.getDocumentSource();
		SearchResult searchResult = searchSource.getSearchResult();

		title = searchResult.getTitle();
		url = searchResult.getURL();
		urlToDisplay = searchResult.getDisplayURL();
		snippet = searchResult.getSnippet();
		preRank = searchResult.getRank();
	    }
	    
	    text = source.getText();
	    postRank = 0;
	    
	    constructions = new ArrayList<>();
	    relFrequencies = new ArrayList<>();
	    frequencies = new ArrayList<>();
	    tfNorm = new ArrayList<>();
	    highlights = new ArrayList<>();
	    tfIdf = new ArrayList<>();
	    
	    for (GrammaticalConstruction itr : GrammaticalConstruction.values())
	    {
		DocumentConstructionData data = source.getConstructionData(itr);
		if (data.hasConstruction())
		{
		    constructions.add(itr.getLegacyID());
		    relFrequencies.add(data.getRelativeFrequency());
		    frequencies.add(data.getFrequency());
		    tfNorm.add(data.getWeightedFrequency());
		    tfIdf.add(data.getRelativeWeightedFrequency());
		    
		    for (Occurrence occr : data.getOccurrences())
			highlights.add(new CompactOccurrence(occr.getStart(), occr.getEnd(), itr.getLegacyID()));
		}
	    }
	    
	    docLenTfIdf = source.getFancyLength();
	    docLength = source.getLength();
	    readabilityScore = source.getReadabilityScore();
	    readabilityLevel = source.getReadabilityLevel();
	    
	    readabilityARI = readabilityBennoehr = textWeight = 0;
	    rankWeight = gramScore = totalWeight = 0;

	    numChars = source.getNumCharacters();
	    numSents = source.getNumSentences();
	    numDeps = source.getNumDependencies();

	    avWordLength = source.getAvgWordLength();
	    avSentLength = source.getAvgSentenceLength();
	    avTreeDepth = source.getAvgTreeDepth();
	}
    }
    
    public final BasicInteropMessage		    baseData;
    public final String				    jobID;		    // corresponding to the parse operation
    public final List<CompactParsedData>	    parsedDocs;	    
    
    public FetchParsedDataResponse(String jobID, List<AbstractDocument> documents)
    {
	baseData = new BasicInteropMessage(BasicInteropMessage.MessageSource.SERVER, BasicInteropMessage.MessageType.FETCH_PARSED_DATA);
	this.jobID = jobID;
	this.parsedDocs = new ArrayList<>();
	
	for (AbstractDocument itr : documents)
	    parsedDocs.add(new CompactParsedData(itr));
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
	else if (messageType.equals(BasicInteropMessage.MessageType.PERFORM_SEARCH.toString()))
	    return BasicInteropMessage.MessageType.PERFORM_SEARCH;
	else if (messageType.equals(BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS.toString()))
	    return BasicInteropMessage.MessageType.FETCH_SEARCH_RESULTS;
	else if (messageType.equals(BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS.toString()))
	    return BasicInteropMessage.MessageType.PARSE_SEARCH_RESULTS;
	else if (messageType.equals(BasicInteropMessage.MessageType.FETCH_PARSED_DATA.toString()))
	    return BasicInteropMessage.MessageType.FETCH_PARSED_DATA;
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
    
    public static String toResponseJSON(Object response)
    {
	Gson serializer = getGson();
	return serializer.toJson(response);
    }
}