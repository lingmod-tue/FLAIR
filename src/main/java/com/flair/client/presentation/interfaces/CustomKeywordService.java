package com.flair.client.presentation.interfaces;

import java.util.List;

import com.flair.client.presentation.widgets.KeywordWeightSlider;

/*
 * Provides interface for using custom keywords
 */
public interface CustomKeywordService
{
	public void				bindToSlider(KeywordWeightSlider slider);
	
	public boolean			hasCustomKeywords();
	public List<String>		getCustomKeywords();
}
