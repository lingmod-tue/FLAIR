/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import FLAIRGrammar.Construction;

/**
 * Represents an occurrence of a construction in a text
 * @author shadeMe
 */
public class Occurrence extends AbstractConstructionData
{
    private final int				startIdx;
    private final int				endIdx;
    private final String			text;
    
    public Occurrence(Construction type, int start, int end)
    {
	this(type, start, end, "");
    }
    
    public Occurrence(Construction type, int start, int end, String text)
    {
	super(type);
	this.startIdx = start;
	this.endIdx = end;
	this.text = text;
    }
    
    public int getStart() {
	return startIdx;
    }
    
    public int getEnd() {
	return endIdx;
    }
    
    public String getText() {
	return text;
    }
    
    public boolean equals(Occurrence rhs)
    {
	return super.equals(rhs) && 
	       startIdx == rhs.startIdx &&
	       endIdx == rhs.endIdx;
    }
}
