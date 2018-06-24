package com.flair.shared.interop;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;

/*
 * Represents an settings profile that can be applied to the ranker settings pane
 */
public interface ConstructionSettingsProfile
{
	public Language				getLanguage();
	
	public boolean				isDocLevelEnabled(DocumentReadabilityLevel level);
	public int					getDocLengthWeight();
	
	public boolean				isKeywordsEnabled();
	public int					getKeywordsWeight();
	
	public boolean				hasConstruction(GrammaticalConstruction gram);
	public boolean				isConstructionEnabled(GrammaticalConstruction gram);
	public int					getConstructionWeight(GrammaticalConstruction gram);
}
