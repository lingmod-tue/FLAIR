package com.flair.client.presentation.interfaces;

import com.flair.client.presentation.widgets.KeywordWeightSlider;

import java.util.List;

/*
 * Provides interface for using custom keywords
 */
public interface CustomKeywordService {
	public void bindToSlider(KeywordWeightSlider slider);

	public boolean hasCustomKeywords();
	public List<String> getCustomKeywords();
}
