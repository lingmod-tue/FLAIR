/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.crawler.SearchResult;
import com.flair.grammar.GrammaticalConstruction;
import com.flair.grammar.Language;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Represents a text document that's parsed by the NLP Parser
 * @author shadeMe
 */
class Document implements AbstractDocument
{
    private static final String		        READABILITY_LEVEL_A = "LEVEL-a";
    private static final String		        READABILITY_LEVEL_B = "LEVEL-b";
    private static final String			READABILITY_LEVEL_C = "LEVEL-c";

    private final AbstractDocumentSource	source;
    private final double			readabilityScore;
    private final String			readabilityLevel;	// calculate from the readability score
    private final ConstructionDataCollection	constructionData;
      
    private int					numCharacters;
    private int					numSentences;
    private int					numDependencies;
    
    private double				avgWordLength;	// doesn't include punctuation
    private double				avgSentenceLength;
    private double				avgTreeDepth;

    private int					tokenCount;		// no of words "essentailly" (kinda), formerly known as "docLength". updated with the dependency count after parsing
    private double				fancyDocLength;	    // ### TODO better name needed, formerly "docLenTfIdf"
    
    private boolean				parsed;
    
    public Document(AbstractDocumentSource parent)
    {
	assert parent != null;
	
	source = parent;
	constructionData = new ConstructionDataCollection(new DocumentConstructionDataFactory(this));
	
	String pageText = source.getSourceText();
	StringTokenizer tokenizer = new StringTokenizer(pageText, " ");
	
	// calculate readability score, level, etc
	tokenCount = tokenizer.countTokens();
	int whitespaceCount = 0;
	for (int i = 0; i < pageText.length(); i++)
	{
	    if (pageText.charAt(i) == ' ')
		whitespaceCount++;
	}
	numCharacters = pageText.length() - whitespaceCount;
	tokenizer = new StringTokenizer(pageText, "[.!?]");
	numSentences = tokenizer.countTokens();
	numDependencies = tokenCount;
	
	if (tokenCount > 100 && numSentences != 0 && numCharacters != 0)
            readabilityScore = Math.ceil(4.71 * ((double) numCharacters / (double)numDependencies) + 0.5 * (numDependencies / (double)numSentences) - 21.43);
	else
	    readabilityScore= -10.0;

	if (readabilityScore < 6)
	    readabilityLevel = READABILITY_LEVEL_A;
	else if (6 <= readabilityScore && readabilityScore <= 10)
	    readabilityLevel = READABILITY_LEVEL_B;
	else
	    readabilityLevel = READABILITY_LEVEL_C;

	avgWordLength = avgSentenceLength = avgTreeDepth = fancyDocLength = 0;
	parsed = false;
    }
    
    @Override
    public Language getLanguage() {
	return source.getLanguage();
    }
    
    @Override
    public String getText() {
	return source.getSourceText();
    }
    
    @Override
    public String getDescription() {
	return "{" + source.getDescription() + " | S[" + numSentences + "], C[" + numCharacters + "]" + "}";
    }
    
    @Override
    public DocumentConstructionData getConstructionData(GrammaticalConstruction type)
    {
	return (DocumentConstructionData)constructionData.getData(type);
    }
    
    public void calculateFancyDocLength()
    {
	double sumOfPowers = 0.0;
        double squareRoot = 0.0;
	// iterate through the construction data set and calc
	for (GrammaticalConstruction itr : GrammaticalConstruction.values())
	{
	    DocumentConstructionData data = getConstructionData(itr);
	    if (data.hasConstruction())
		sumOfPowers += Math.pow(data.getWeightedFrequency(), 2);
	}

	if (sumOfPowers > 0)
	    squareRoot = Math.sqrt(sumOfPowers);
	
	fancyDocLength = squareRoot;
    }
    
    @Override
    public double getReadabilityScore() {
	return readabilityScore;
    }
    
    @Override
    public String getReadabilityLevel() {
	return readabilityLevel;
    }
    
    @Override
    public int getNumCharacters() {
	return numCharacters;
    }
    
    @Override
    public int getNumSentences() {
	return numSentences;
    }
    
    
    @Override
    public int getNumDependencies() {
	return numDependencies;
    }
    
    @Override
    public double getAvgWordLength() {
	return avgWordLength;
    }
    
    @Override
    public double getAvgSentenceLength() {
	return avgSentenceLength;
    }
    
    @Override
    public double getAvgTreeDepth() {
	return avgTreeDepth;
    }
    
    @Override
    public int getLength() {
	return tokenCount;
    }
    
    @Override
    public double getFancyLength() {
	return fancyDocLength;
    }
    
    @Override
    public void setNumCharacters(int value) {
	numCharacters = value;
    }
    
    @Override
    public void setNumSentences(int value) {
	numSentences = value;
    }
    
    @Override
    public void setNumDependencies(int value) {
	numDependencies = value;
    }
    
    @Override
    public void setAvgWordLength(double value) {
	avgWordLength = value;
    }
    
    @Override
    public void setAvgSentenceLength(double value) {
	avgSentenceLength = value;
    }
    
    @Override
    public void setAvgTreeDepth(double value) {
	avgTreeDepth = value;
    }
    
    @Override
    public void setLength(int value) {
	tokenCount = value;
    }

    @Override
    public boolean isParsed() {
	return parsed;
    }

    @Override
    public void flagAsParsed()
    {
	if (parsed)
	    throw new IllegalStateException("Document already flagged as parsed");
	
	parsed = true;
	calculateFancyDocLength();
    }

    @Override
    public Serializable getSerializable() {
	return new DocumentFirstRevisionDecorator(this);
    }
    
    final class DocumentFirstRevisionDecorator implements java.io.Serializable
    {
	private final String			query;

	private final int			preRank;
	private final int			postRank;

	private final String			title;
	private final String			url;
	private final String			urlToDisplay;
	private final String			snippet;
	private final String			html;
	private final String			text;


	private final ArrayList<String>		constructions;
	private final ArrayList<Double>		relFrequencies;
	private final ArrayList<Integer>	frequencies;
	private final ArrayList<Double>		tfNorm;
	private final ArrayList<OccurrenceFirstRevisionDecorator>  highlights;
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

	public DocumentFirstRevisionDecorator(Document source)
	{
	    if (parsed== false)
		throw new IllegalStateException("Document not flagged as parsed");
	    
	    SearchResultDocumentSource searchSource = (SearchResultDocumentSource)source.source;
	    SearchResult searchResult = searchSource.getSearchResult();
	    
	    query = searchResult.getQuery();
	    title = searchResult.getTitle();
	    url = searchResult.getURL();
	    urlToDisplay = searchResult.getDisplayURL();
	    snippet = searchResult.getSnippet();
	    text = source.getText();
	    html = "";
	    
	    preRank = postRank = 0;
	    
	    constructions = new ArrayList<>();
	    relFrequencies = new ArrayList<>();
	    frequencies = new ArrayList<>();
	    tfNorm = new ArrayList<>();
	    highlights = new ArrayList<>();
	    tfIdf = new ArrayList<>();
	    
	    for (GrammaticalConstruction itr : GrammaticalConstruction.values())
	    {
		DocumentConstructionData data = getConstructionData(itr);
		if (data.hasConstruction())
		{
		    constructions.add(itr.name());
		    relFrequencies.add(data.getRelativeFrequency());
		    frequencies.add(data.getFrequency());
		    tfNorm.add(data.getWeightedFrequency());
		    tfIdf.add(data.getRelativeWeightedFrequency());
		    
		    for (Occurrence occr : data.getOccurrences())
		    {
			highlights.add(new OccurrenceFirstRevisionDecorator(occr.getStart(),
									    occr.getEnd(),
									    occr.getText(),
									    itr.name()));
		    }
		}
	    }
	    
	    docLenTfIdf = source.fancyDocLength;
	    docLength = source.tokenCount;
	    readabilityScore = source.readabilityScore;
	    readabilityLevel = source.readabilityLevel;
	    readabilityARI = readabilityBennoehr = textWeight = 0;
	    rankWeight = gramScore = totalWeight = 0;

	    numChars = source.numCharacters;
	    numSents = source.numSentences;
	    numDeps = source.numDependencies;

	    avWordLength = source.avgWordLength;
	    avSentLength = source.avgSentenceLength;
	    avTreeDepth = source.avgTreeDepth;
	}
    }
}


class DocumentFactory implements AbstractDocumentFactory
{
    @Override
    public AbstractDocument create(AbstractDocumentSource source)
    {
	return new Document(source);
    }
}