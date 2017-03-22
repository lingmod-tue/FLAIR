/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.shared.grammar;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the languages FLAIR supports
 * @author shadeMe
 */
public enum Language
{
    @SerializedName("ENGLISH")
    ENGLISH,
    @SerializedName("GERMAN")
    GERMAN,
    ;
    
    public static Language fromString(String lang)
    {
    	if (lang.equalsIgnoreCase(ENGLISH.name()))
    		return ENGLISH;
    	else if (lang.equalsIgnoreCase(GERMAN.name()))
    		return GERMAN;
    	else
    		throw new RuntimeException("Invalid language string " + lang);
    }
}
