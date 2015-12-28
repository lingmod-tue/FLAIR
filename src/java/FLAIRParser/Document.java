/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import FLAIRGrammar.Construction;
import java.util.StringTokenizer;

/**
 * Represents a text document that's parsed by the NLP Parser
 * @author shadeMe
 */
public class Document implements AbstractDocument
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
    }
    
    @Override
    public String getText() {
	return source.getSourceText();
    }
    
    @Override
    public DocumentConstructionData getConstructionData(Construction type)
    {
	return (DocumentConstructionData)constructionData.getData(type);
    }
    
    public void calculateFancyDocLength()
    {
	double sumOfPowers = 0.0;
        double squareRoot = 0.0;
	// iterate through the construction data set and calc
	for (Construction itr : Construction.values())
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
    
    /**
     *
     * @return
     */
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
    
    public double getFancyLength(boolean recalculate)
    {
	if (recalculate)
	    calculateFancyDocLength();
	
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
}

class DocumentFactory implements AbstractDocumentFactory
{
    @Override
    public AbstractDocument create(AbstractDocumentSource source)
    {
	return new Document(source);
    }
}