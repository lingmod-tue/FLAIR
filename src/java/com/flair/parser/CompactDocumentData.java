/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.crawler.SearchResult;
import com.flair.grammar.GrammaticalConstruction;
import java.util.ArrayList;

/**
 * A flattened version of a Document that is JSON-serializable
 * @author shadeMe
 */
public final class CompactDocumentData
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

    private final int				preRank;
    private final int				postRank;

    private final String			title;
    private final String			url;
    private final String			urlToDisplay;
    private final String			snippet;
    private final String			text;

    private final ArrayList<String>		constructions;
    private final ArrayList<Double>		relFrequencies;
    private final ArrayList<Integer>		frequencies;
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

    private final int				numChars;
    private final int				numSents;
    private final int				numDeps;

    private final double			avWordLength;
    private final double			avSentLength;
    private final double			avTreeDepth;

    public CompactDocumentData(AbstractDocument source) 
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
