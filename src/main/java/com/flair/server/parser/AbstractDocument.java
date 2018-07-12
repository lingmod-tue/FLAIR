/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;

/**
 * The interface all parsable documents must implement
 *
 * @author shadeMe
 */
public interface AbstractDocument extends Comparable<AbstractDocument> {
	public AbstractDocumentSource getDocumentSource();
	public Language getLanguage();
	public String getText();
	public String getDescription();
	public Iterable<GrammaticalConstruction> getSupportedConstructions();    // returns the constructions pertinent to the doc's language
	public DocumentConstructionData getConstructionData(GrammaticalConstruction type);

	void addSentenceSegment(TextSegment span);
	Iterable<TextSegment> getSentenceSegments();    // returns the ordered list of document text's sentences as spans
	String getSentenceText(TextSegment sentSpan, boolean sanitize);

	public double getReadabilityScore();
	public DocumentReadabilityLevel getReadabilityLevel();

	public int getNumCharacters();
	public int getNumSentences();
	public int getNumDependencies();
	public int getNumWords();
	public int getNumTokens();

	public double getAvgWordLength();
	public double getAvgSentenceLength();
	public double getAvgTreeDepth();

	public void setNumCharacters(int value);
	public void setNumSentences(int value);
	public void setNumDependencies(int value);
	public void setNumWords(int value);
	public void setNumTokens(int value);

	public void setAvgWordLength(double value);
	public void setAvgSentenceLength(double value);
	public void setAvgTreeDepth(double value);

	public int getLength();
	public void setLength(int value);
	public double getFancyLength();

	public KeywordSearcherOutput getKeywordData();
	public void setKeywordData(KeywordSearcherOutput data);

	public boolean isParsed();
	public void flagAsParsed();
}
