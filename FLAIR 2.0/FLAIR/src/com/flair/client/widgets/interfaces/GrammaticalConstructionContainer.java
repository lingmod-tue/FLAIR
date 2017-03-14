package com.flair.client.widgets.interfaces;

import com.flair.client.widgets.GrammaticalConstructionWeightSlider;
import com.flair.shared.grammar.GrammaticalConstruction;

/*
 * Implemented by widgets that store gram const sliders as children
 */
public interface GrammaticalConstructionContainer
{
	public boolean									hasConstruction(GrammaticalConstruction val);
	public GrammaticalConstructionWeightSlider		getWeightSlider(GrammaticalConstruction val);	// returns null if absent
}
