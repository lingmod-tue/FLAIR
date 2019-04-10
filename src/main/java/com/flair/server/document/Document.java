
package com.flair.server.document;

import com.flair.server.parser.KeywordSearcherOutput;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.utilities.TextSegment;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;

import java.util.StringTokenizer;

/**
 * Represents a text document that's parsed by the NLP Parser
 */
public class Document implements AbstractDocument {
	private final AbstractDocumentSource source;
	private final double readabilityScore;
	private final DocumentReadabilityLevel readabilityLevel;    // calculate from the readability score
	private final ConstructionDataCollection constructionData;
	private ParserAnnotations parserAnnotations;

	private int numCharacters;
	private int numSentences;
	private int numDependencies;
	private int numWords;
	private int numTokens;        // no of words essentially (kinda), later substituted with no of words (without punctuation)

	private double avgWordLength;        // doesn't include punctuation
	private double avgSentenceLength;
	private double avgTreeDepth;

	private double gramL2Norm;
	private KeywordSearcherOutput keywordData;

	private boolean parsed;

	public Document(AbstractDocumentSource parent) {
		assert parent != null;

		source = parent;
		constructionData = new ConstructionDataCollection(parent.getLanguage(), new DocumentConstructionDataFactory(this));
		parserAnnotations = null;

		String pageText = source.getSourceText();
		StringTokenizer tokenizer = new StringTokenizer(pageText, " ");

		// calculate readability score, level, etc
		numTokens = tokenizer.countTokens();
		int whitespaceCount = 0;
		for (int i = 0; i < pageText.length(); i++) {
			if (pageText.charAt(i) == ' ')
				whitespaceCount++;
		}
		numCharacters = pageText.length() - whitespaceCount;
		tokenizer = new StringTokenizer(pageText, "[.!?]");
		numSentences = tokenizer.countTokens();
		numDependencies = 0;

		double readabilityScoreCalc = 0;
		double readabilityLevelThreshold_A = 0;
		double readabilityLevelThreshold_B = 0;

		switch (source.getLanguage()) {
		case ENGLISH:
			readabilityScoreCalc = Math.ceil(4.71 * ((double) numCharacters / (double) numTokens)
					+ 0.5 * (numTokens / (double) numSentences) - 21.43);
			readabilityLevelThreshold_A = 7;
			readabilityLevelThreshold_B = 12;
			break;
		case GERMAN:
			readabilityScoreCalc = Math
					.ceil(((double) numCharacters / (double) numTokens) + (numTokens / (double) numSentences));
			readabilityLevelThreshold_A = 10;
			readabilityLevelThreshold_B = 20;
			break;
		default:
			throw new IllegalArgumentException("Invalid document language");
		}

		if (numSentences != 0 && numCharacters != 0)
			readabilityScore = readabilityScoreCalc;
		else
			readabilityScore = -10.0;

		if (readabilityScore < readabilityLevelThreshold_A)
			readabilityLevel = DocumentReadabilityLevel.LEVEL_A;
		else if (readabilityLevelThreshold_A <= readabilityScore && readabilityScore <= readabilityLevelThreshold_B)
			readabilityLevel = DocumentReadabilityLevel.LEVEL_B;
		else
			readabilityLevel = DocumentReadabilityLevel.LEVEL_C;

		avgWordLength = avgSentenceLength = avgTreeDepth = gramL2Norm = 0;
		keywordData = null;
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
	public String getSpanText(TextSegment span) {
		return source.getSourceText().substring(span.getStart(), span.getEnd());
	}

	@Override
	public String getDescription() {
		return "{" + source.getDescription() + " | S[" + numSentences + "], W[" + numWords + "]" + "}";
	}

	@Override
	public DocumentConstructionData getConstructionData(GrammaticalConstruction type) {
		return (DocumentConstructionData) constructionData.getData(type);
	}

	@Override
	public ParserAnnotations getParserAnnotations() {
		return parserAnnotations;
	}

	private void updateGramL2Norm() {
		double sumOfPowers = 0.0;
		double squareRoot = 0.0;
		// iterate through the construction data set and calc
		for (GrammaticalConstruction itr : getSupportedConstructions()) {
			DocumentConstructionData data = getConstructionData(itr);
			if (data.hasConstruction())
				sumOfPowers += Math.pow(data.getWeightedFrequency(), 2);
		}

		if (sumOfPowers > 0)
			squareRoot = Math.sqrt(sumOfPowers);

		gramL2Norm = squareRoot;
	}

	@Override
	public double getReadabilityScore() {
		return readabilityScore;
	}

	@Override
	public DocumentReadabilityLevel getReadabilityLevel() {
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
	public int getNumWords() {
		return numWords;
	}

	@Override
	public int getNumTokens() {
		return numTokens;
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
		return numWords;
	}

	@Override
	public double getGramL2Norm() {
		return gramL2Norm;
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
	public void setNumWords(int numWords) {
		this.numWords = numWords;
	}

	@Override
	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
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
		numWords = value;
	}

	@Override
	public boolean isParsed() {
		return parsed;
	}

	@Override
	public void flagAsParsed(ParserAnnotations annotations) {
		if (parsed)
			throw new IllegalStateException("Document already flagged as parsed");

		parsed = true;
		parserAnnotations = annotations;
		updateGramL2Norm();
	}

	@Override
	public AbstractDocumentSource getDocumentSource() {
		return source;
	}

	@Override
	public int compareTo(AbstractDocument t) {
		return source.compareTo(t.getDocumentSource());
	}

	@Override
	public KeywordSearcherOutput getKeywordData() {
		return keywordData;
	}

	@Override
	public void setKeywordData(KeywordSearcherOutput data) {
		keywordData = data;
	}

	@Override
	public Iterable<GrammaticalConstruction> getSupportedConstructions() {
		return GrammaticalConstruction.getForLanguage(getLanguage());
	}

	public static final class Factory implements AbstractDocumentFactory {
		@Override
		public AbstractDocument create(AbstractDocumentSource source) {
			return new Document(source);
		}
	}

	public static AbstractDocumentFactory factory() {
		return new Factory();
	}
}