/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Results of an AbstractDocumentKeywordSearcher search operation
 * @author shadeMe
 */
public class KeywordSearcherOutput
{
    private final List<String>				    keywords;
    private final Map<String, List<TextSegment>>	    hits;

    public KeywordSearcherOutput(KeywordSearcherInput input) 
    {
	this.keywords = new ArrayList<>();
	this.hits = new HashMap<>();
	
	for (String itr : input)
	{
	    keywords.add(itr);
	    hits.put(itr, new ArrayList<TextSegment>());
	}
    }
    
    protected void addHit(String keyword, int start, int end)
    {
	if (hits.containsKey(keyword) == false)
	    throw new IllegalArgumentException("Keyword " + keyword + " differs from input");
	
	hits.get(keyword).add(new TextSegment(start, end));
    }
    
    public List<TextSegment> getHits(String keyword)
    {
	if (hits.containsKey(keyword) == false)
	    throw new IllegalArgumentException("Keyword " + keyword + " differs from input");
	
	return hits.get(keyword);
    }
    
    public List<String> getKeywords() {
	return keywords;
    }
    
    public double getHitCount(String keyword) 
    {
	if (hits.containsKey(keyword) == false)
	    throw new IllegalArgumentException("Keyword " + keyword + " differs from input");
	
	return hits.get(keyword).size();
    }
    
    public double getTotalHitCount()
    {
	double hitCount = 0;
	for (List<TextSegment> itr : hits.values())
	    hitCount += itr.size();
	return hitCount;
    }
}
