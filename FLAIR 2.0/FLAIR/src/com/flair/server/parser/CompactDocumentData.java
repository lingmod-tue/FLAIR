/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.parser;

import com.flair.server.crawler.SearchResult;
import com.flair.shared.grammar.GrammaticalConstruction;

import java.util.ArrayList;
import java.util.List;

/**
 * A flattened version of a Document that is JSON-serializable
 * @author shadeMe
 */
public final class CompactDocumentData
{
    final class ConstructionOccurrence
    {
	public final int		start;
	public final int		end;
	public final String		construction;

	public ConstructionOccurrence(int start, int end, String construction) 
	{
	    this.start = start;
	    this.end = end;
	    this.construction = construction;
	}
    }
    
    final class KeywordOccurrence
    {
	public final int		start;
	public final int		end;
	public final String		keyword;

	public KeywordOccurrence(int start, int end, String keyword) 
	{
	    this.start = start;
	    this.end = end;
	    this.keyword = keyword;
	}
    }

    private final int				preRank;
    private final int				postRank;

    private final String			title;
    private final String			url;
    private final String			urlToDisplay;
    private final String			snippet;
    private final String			text;

    private final ArrayList<String>			constructions;
    private final ArrayList<Double>			relFrequencies;
    private final ArrayList<Integer>			frequencies;
    private final ArrayList<ConstructionOccurrence>	highlights;
    private final ArrayList<KeywordOccurrence>		keywords;
    private final double				totalKeywords;	    // total hit count
    private final double				relFreqKeywords;
    private final double				docLength;

    private final String			readabilityLevel;

    private final int				numSents;
    private final int				numDeps;

    public CompactDocumentData(AbstractDocument source) 
    {
	if (source.isParsed() == false)
	    throw new IllegalStateException("Document not flagged as parsed");

	if (source.getDocumentSource() instanceof SearchResultDocumentSource)
	{
	    SearchResultDocumentSource searchSource = (SearchResultDocumentSource)source.getDocumentSource();
	    SearchResult searchResult = searchSource.getSearchResult();

	    title = searchResult.getTitle();
	    url = searchResult.getURL();
	    urlToDisplay = searchResult.getDisplayURL();
	    snippet = searchResult.getSnippet();
	    preRank = searchResult.getRank();
	}
	else if (source.getDocumentSource() instanceof StreamDocumentSource)
	{
	    StreamDocumentSource localSource = (StreamDocumentSource)source.getDocumentSource();
	    
	    title = localSource.getName();
	    url = urlToDisplay = "";
	    
	    String textSnip = source.getText();
	    if (textSnip.length() > 35)
		snippet = textSnip.substring(0, 35) + "...";
	    else
		snippet = textSnip;

	    preRank = -1;
	}
	else
	{
	    title = url = urlToDisplay = snippet = "";
	    preRank = -1;
	}
	
	text = source.getText();
	postRank = 0;

	constructions = new ArrayList<>();
	relFrequencies = new ArrayList<>();
	frequencies = new ArrayList<>();
	highlights = new ArrayList<>();
	keywords = new ArrayList<>();

	for (GrammaticalConstruction itr : GrammaticalConstruction.values())
	{
	    DocumentConstructionData data = source.getConstructionData(itr);
	    if (data.hasConstruction())
	    {
		constructions.add(itr.getFrontendID());
		relFrequencies.add(data.getRelativeFrequency());
		frequencies.add(data.getFrequency());
		
		for (com.flair.server.parser.ConstructionOccurrence occr : data.getOccurrences())
		    highlights.add(new ConstructionOccurrence(occr.getStart(), occr.getEnd(), itr.getFrontendID()));
	    }
	}
	
	KeywordSearcherOutput keywordData = source.getKeywordData();
	if (keywordData != null)
	{
	    for (String itr: keywordData.getKeywords())
	    {
		List<TextSegment> hits = keywordData.getHits(itr);
		for (TextSegment hit : hits)
		    keywords.add(new KeywordOccurrence(hit.getStart(), hit.getEnd(), itr));
	    }
	    
	    totalKeywords = keywordData.getTotalHitCount();
	    relFreqKeywords = totalKeywords / (double)source.getNumWords();
	}
	else
	    totalKeywords = relFreqKeywords = 0;

	docLength = source.getLength();
	readabilityLevel = source.getReadabilityLevel();
	numSents = source.getNumSentences();
	numDeps = source.getNumDependencies();
    }
}
