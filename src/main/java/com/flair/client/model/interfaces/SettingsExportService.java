package com.flair.client.model.interfaces;

/*
 * Allows construction settings to be exported and imported through URL query parameters
 */
public interface SettingsExportService {
	public ConstructionSettingsProfile importSettings();                                        // returns null if the query params were invalid/missing
	public String exportSettings(ConstructionSettingsProfile settings);    // returns the encoded URL
	// only encodes constructions that have been disabled and/or have non-zero weights
}
