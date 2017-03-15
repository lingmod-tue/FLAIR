package com.flair.client.presentation.interfaces;

import com.flair.client.presentation.widgets.GrammaticalConstructionWeightSlider;
import com.flair.shared.grammar.GrammaticalConstruction;

/*
 * Implemented by widgets that store gram const sliders as children
 */
public interface GrammaticalConstructionContainer
{
	public boolean									hasConstruction(GrammaticalConstruction val);
	public GrammaticalConstructionWeightSlider		getWeightSlider(GrammaticalConstruction val);	// returns null if absent
}
