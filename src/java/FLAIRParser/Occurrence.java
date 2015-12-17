/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Represents an occurrence of a construction in a text
 * @author shadeMe
 */
public class Occurrence extends AbstractConstructionData
{
    private final int				startIdx;
    private final int				endIdx;
    private final String			parentString;	    // string in which it occurs, in syntactic tree notation
    
    public Occurrence(Construction type, int start, int end)
    {
	this(type, start, end, "");
    }
    
    public Occurrence(Construction type, int start, int end, String parent)
    {
	super(type);
	this.startIdx = start;
	this.endIdx = end;
	this.parentString = parent;
    }
    
    public int getStart() {
	return startIdx;
    }
    
    public int getEnd() {
	return endIdx;
    }
    
    public String getParent() {
	return parentString;
    }
    
    public boolean equals(Occurrence rhs)
    {
	return super.equals(rhs) && 
	       startIdx == rhs.startIdx &&
	       endIdx == rhs.endIdx;
    }
}
