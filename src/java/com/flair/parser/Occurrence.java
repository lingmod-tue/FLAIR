/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.grammar.GrammaticalConstruction;

/**
 * Represents an occurrence of a construction in a text
 * @author shadeMe
 */
public class Occurrence extends AbstractConstructionData
{
    private final int				startIdx;
    private final int				endIdx;
    
    public Occurrence(GrammaticalConstruction type, int start, int end)
    {
	super(type);
	this.startIdx = start;
	this.endIdx = end;
    }
    
    public int getStart() {
	return startIdx;
    }
    
    public int getEnd() {
	return endIdx;
    }
    
    public boolean equals(Occurrence rhs)
    {
	return super.equals(rhs) && 
	       startIdx == rhs.startIdx &&
	       endIdx == rhs.endIdx;
    }
}
