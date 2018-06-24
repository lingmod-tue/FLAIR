/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.parser;

/**
 * Represents a segement in a text
 * @author shadeMe
 */
public class TextSegment
{
    private final int		startIdx;
    private final int		endIdx;

    public TextSegment(int startIdx, int endIdx)
    {
	this.startIdx = startIdx;
	this.endIdx = endIdx;
    }
    
    public int getStart() {
	return startIdx;
    }
    
    public int getEnd() {
	return endIdx;
    }
}
