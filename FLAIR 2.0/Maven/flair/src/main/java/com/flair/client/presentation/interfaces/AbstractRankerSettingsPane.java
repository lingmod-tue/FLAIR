package com.flair.client.presentation.interfaces;

import com.flair.client.model.interfaces.DocumentRankerOutput;
import com.flair.client.presentation.widgets.DocumentLengthSlider;
import com.flair.client.presentation.widgets.KeywordWeightSlider;
import com.flair.client.presentation.widgets.LanguageSpecificConstructionSliderBundle;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.ConstructionSettingsProfile;
import com.flair.shared.parser.DocumentReadabilityLevel;

/*
 * Implemented by webranker settings panes
 */
public interface AbstractRankerSettingsPane
{
	public interface EventHandler {
		public void handle();
	}
	
	public void											setSliderBundle(Language lang);
	public void											updateSettings(DocumentRankerOutput.Rank rankData);
	
	public void											show();
	public void											hide();
	
	public void											setSettingsChangedHandler(EventHandler handler);
	public void											setVisualizeHandler(EventHandler handler);
	public void											setExportSettingsHandler(EventHandler handler);
	public void											setResetAllHandler(EventHandler handler);
	
	public LanguageSpecificConstructionSliderBundle		getSliderBundle();
	public DocumentLengthSlider							getLengthSlider();
	public KeywordWeightSlider							getKeywordSlider();
	
	public boolean										isDocLevelEnabled(DocumentReadabilityLevel level);
	
	public ConstructionSettingsProfile					generateSettingsProfile();
	public void											applySettingsProfile(ConstructionSettingsProfile profile, boolean fireEvents);
	
}
