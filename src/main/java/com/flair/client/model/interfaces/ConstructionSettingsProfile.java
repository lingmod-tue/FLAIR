package com.flair.client.model.interfaces;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;

/*
 * Represents an settings profile that can be applied to the ranker settings pane
 */
public interface ConstructionSettingsProfile {
	Language getLanguage();

	boolean isDocLevelEnabled(DocumentReadabilityLevel level);
	int getDocLengthWeight();

	boolean isKeywordsEnabled();
	int getKeywordsWeight();

	boolean hasConstruction(GrammaticalConstruction gram);
	boolean isConstructionEnabled(GrammaticalConstruction gram);
	int getConstructionWeight(GrammaticalConstruction gram);
}
