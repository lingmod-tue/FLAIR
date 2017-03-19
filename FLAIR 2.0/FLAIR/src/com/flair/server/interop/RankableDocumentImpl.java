package com.flair.server.interop;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Flattened, serializable version of an AbstractDocument
 */
public final class RankableDocumentImpl implements RankableDocument
{
	public static final class ConstructionOccurrence implements RankableDocument.ConstructionRange, IsSerializable
	{
		int							start;
		int							end;
		GrammaticalConstruction		construction;

		public ConstructionOccurrence(int start, int end, GrammaticalConstruction gram)
		{
			this.start = start;
			this.end = end;
			this.construction = gram;
		}
		
		public ConstructionOccurrence()
		{
			start = end = -1;
			construction = null;
		}

		@Override
		public int getStart() {
			return start;
		}

		@Override
		public int getEnd() {
			return end;
		}

		@Override
		public GrammaticalConstruction getConstruction() {
			return construction;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public void setConstruction(GrammaticalConstruction construction) {
			this.construction = construction;
		}
	}

	public static final class KeywordOccurrence implements RankableDocument.KeywordRange, IsSerializable
	{
		int		start;
		int		end;
		String	keyword;

		public KeywordOccurrence(int start, int end, String keyword)
		{
			this.start = start;
			this.end = end;
			this.keyword = keyword;
		}

		public KeywordOccurrence()
		{
			start = end = -1;
			keyword = "";
		}

		@Override
		public int getStart() {
			return start;
		}

		@Override
		public int getEnd() {
			return end;
		}

		@Override
		public String getKeyword() {
			return keyword;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
	}
	
	Language							language;
	int									rank;
	
	String								title;
	String								url;
	String								displayUrl;
	String								snippet;
	String								text;

	HashSet<GrammaticalConstruction>										constructions;
	EnumMap<GrammaticalConstruction, Double>								relFrequencies;
	EnumMap<GrammaticalConstruction, Integer>								frequencies;
	EnumMap<GrammaticalConstruction, ArrayList<ConstructionOccurrence>>		constOccurrences;
	ArrayList<KeywordOccurrence>											keywordOccurrences;
	double							keywordCount;
	double							keywordRelFreq;
	
	int								rawTextLength;
	double							numWords;
	double							numSentences;
	double							numDependencies;
	DocumentReadabilityLevel 		readabilityLevel;
	
	public RankableDocumentImpl()
	{
		language = null;
		rank = -1;
		
		title = url = displayUrl = snippet = text = "";
		
		constructions = new HashSet<>();
		relFrequencies = new EnumMap<>(GrammaticalConstruction.class);
		frequencies = new EnumMap<>(GrammaticalConstruction.class);
		constOccurrences = new EnumMap<>(GrammaticalConstruction.class);
		
		keywordOccurrences = new ArrayList<>();
		keywordCount = keywordRelFreq = 0;
		
		rawTextLength = 0;
		numWords = numSentences = numDependencies = 0;
		readabilityLevel = null;
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getDisplayUrl() {
		return displayUrl;
	}

	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}

	@Override
	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public HashSet<GrammaticalConstruction> getConstructions() {
		return constructions;
	}

	public void setConstructions(HashSet<GrammaticalConstruction> constructions) {
		this.constructions = constructions;
	}

	public EnumMap<GrammaticalConstruction, Double> getRelFrequencies() {
		return relFrequencies;
	}

	public void setRelFrequencies(EnumMap<GrammaticalConstruction, Double> relFrequencies) {
		this.relFrequencies = relFrequencies;
	}

	public EnumMap<GrammaticalConstruction, Integer> getFrequencies() {
		return frequencies;
	}

	public void setFrequencies(EnumMap<GrammaticalConstruction, Integer> frequencies) {
		this.frequencies = frequencies;
	}

	public EnumMap<GrammaticalConstruction, ArrayList<ConstructionOccurrence>> getConstOccurrences() {
		return constOccurrences;
	}

	public void setConstOccurrences(EnumMap<GrammaticalConstruction, ArrayList<ConstructionOccurrence>> constOccurrences) {
		this.constOccurrences = constOccurrences;
	}

	@Override
	public ArrayList<KeywordOccurrence> getKeywordOccurrences() {
		return keywordOccurrences;
	}

	public void setKeywordOccurrences(ArrayList<KeywordOccurrence> keywordOccurrences) {
		this.keywordOccurrences = keywordOccurrences;
	}

	@Override
	public double getKeywordCount() {
		return keywordCount;
	}

	public void setKeywordCount(double keywordCount) {
		this.keywordCount = keywordCount;
	}

	@Override
	public double getKeywordRelFreq() {
		return keywordRelFreq;
	}

	public void setKeywordRelFreq(double keywordRelFreq) {
		this.keywordRelFreq = keywordRelFreq;
	}

	@Override
	public int getRawTextLength() {
		return rawTextLength;
	}

	public void setRawTextLength(int rawTextLength) {
		this.rawTextLength = rawTextLength;
	}

	@Override
	public double getNumWords() {
		return numWords;
	}

	public void setNumWords(double numWords) {
		this.numWords = numWords;
	}

	@Override
	public double getNumSentences() {
		return numSentences;
	}

	public void setNumSentences(double numSentences) {
		this.numSentences = numSentences;
	}

	@Override
	public double getNumDependencies() {
		return numDependencies;
	}

	public void setNumDependencies(double numDependencies) {
		this.numDependencies = numDependencies;
	}

	@Override
	public DocumentReadabilityLevel getReadabilityLevel() {
		return readabilityLevel;
	}

	public void setReadabilityLevel(DocumentReadabilityLevel readabilityLevel) {
		this.readabilityLevel = readabilityLevel;
	}

	@Override
	public boolean hasConstruction(GrammaticalConstruction gram) {
		return constructions.contains(gram);
	}

	@Override
	public double getConstructionFreq(GrammaticalConstruction gram) 
	{
		if (hasConstruction(gram))
			return frequencies.get(gram);
		else
			return 0;
	}

	@Override
	public double getConstructionRelFreq(GrammaticalConstruction gram)
	{
		if (hasConstruction(gram))
			return relFrequencies.get(gram);
		else
			return 0;
	}

	@Override
	public ArrayList<? extends ConstructionRange> getConstructionOccurrences(GrammaticalConstruction gram) 
	{
		if (hasConstruction(gram))
			return constOccurrences.get(gram);
		else
			return new ArrayList<>();
	}
}
