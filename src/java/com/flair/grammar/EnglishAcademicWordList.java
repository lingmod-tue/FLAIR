/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.grammar;

import com.flair.resources.ResourceLoader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * List of words that occur in the English Academic Corpus
 * @author shadeMe
 */
public class EnglishAcademicWordList
{
    private static final List<String>				KEYWORDS = new ArrayList<>();
    
    public static List<String> getKeywords()
    {
	if (KEYWORDS.isEmpty())
	{
	    // load from disk
	    InputStream stream = ResourceLoader.get("awl-english.txt");
	    Scanner input = new Scanner(stream);
	    while (input.hasNext())
	    {
		String line = input.nextLine().trim();
		if (line.isEmpty() == false && KEYWORDS.contains(line) == false)
		    KEYWORDS.add(line);
	    }
	}
	
	return KEYWORDS;
    }    
}
