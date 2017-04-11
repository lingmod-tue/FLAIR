package com.flair.shared.interop;

import java.util.EnumMap;
import java.util.Map;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;

public class ConstructionSettingsProfileImpl implements ConstructionSettingsProfile
{
	public static final class WeightData
	{
		boolean		enabled;
		int			weight;
		
		WeightData()
		{
			enabled = true;
			weight = 0;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
	}
	
	Language									language;
	Map<DocumentReadabilityLevel, WeightData>	docLevels;
	int											docLengthWeight;
	WeightData									keywords;
	Map<GrammaticalConstruction, WeightData>	grams;
	
	
	public ConstructionSettingsProfileImpl()
	{
		language = null;
		docLevels = new EnumMap<>(DocumentReadabilityLevel.class);
		docLengthWeight = 0;
		keywords = new WeightData();
		grams = new EnumMap<>(GrammaticalConstruction.class);
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	@Override
	public boolean isDocLevelEnabled(DocumentReadabilityLevel level)
	{
		if (docLevels.containsKey(level) == false)
			return false;
		else
			return docLevels.get(level).enabled;
	}

	@Override
	public int getDocLengthWeight() {
		return docLengthWeight;
	}

	@Override
	public boolean isKeywordsEnabled() {
		return keywords.enabled;
	}

	@Override
	public int getKeywordsWeight() {
		return keywords.weight;
	}

	@Override
	public boolean isConstructionEnabled(GrammaticalConstruction gram)
	{
		if (grams.containsKey(gram) == false)
			return false;
		else
			return grams.get(gram).enabled;
	}

	@Override
	public int getConstructionWeight(GrammaticalConstruction gram)
	{
		if (grams.containsKey(gram) == false)
			return 0;
		else
			return grams.get(gram).weight;
	}

	public Map<DocumentReadabilityLevel, WeightData> getDocLevels() {
		return docLevels;
	}

	public void setDocLevels(Map<DocumentReadabilityLevel, WeightData> docLevels) {
		this.docLevels = docLevels;
	}

	public WeightData getKeywords() {
		return keywords;
	}

	public void setKeywords(WeightData keywords) {
		this.keywords = keywords;
	}

	public Map<GrammaticalConstruction, WeightData> getGrams() {
		return grams;
	}

	public void setGrams(Map<GrammaticalConstruction, WeightData> grams) {
		this.grams = grams;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setDocLengthWeight(int docLengthWeight) {
		this.docLengthWeight = docLengthWeight;
	}

	@Override
	public boolean hasConstruction(GrammaticalConstruction gram) {
		return grams.containsKey(gram);
	}

	public void setDocLevelEnabled(DocumentReadabilityLevel level, boolean enabled)
	{
		WeightData d = new WeightData();
		d.enabled = enabled;
		
		docLevels.remove(level);
		docLevels.put(level, d);
	}
	
	public void setGramData(GrammaticalConstruction gram, boolean enabled, int weight)
	{
		WeightData d = new WeightData();
		d.enabled = enabled;
		d.weight = weight;
		
		grams.remove(gram);
		grams.put(gram, d);
	}
}
