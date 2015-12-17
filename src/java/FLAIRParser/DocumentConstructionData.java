/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import java.util.ArrayList;

/**
 * Represents basic properties of a construction for a specific document
 * @author shadeMe
 */
public class DocumentConstructionData extends AbstractConstructionData
{
    private final Document			    parentDocument;
    private final ArrayList<Occurrence>		    occurrences;
    
    private boolean hasOccurence(int start, int end)
    {
	for (Occurrence itr : occurrences)
	{
	    if (itr.getStart() == start && itr.getEnd() == end)
		return true;
	}
	
	return false;
    }
    
    public DocumentConstructionData(Construction type, Document parent)
    {
	super(type);

	assert parent != null;
	parentDocument = parent;
	occurrences = new ArrayList<>();
    }
    
    public boolean hasConstruction() {
	return getFrequency() != 0;
    }
    
    public int getFrequency() {
	return occurrences.size();
    }
    
    public double getWeightedFrequency() {
	return 1.0 + Math.log(getFrequency());
    }
    
    public double getRelativeFrequency() {
	return (double)getFrequency() / (double)parentDocument.getLength();
    }
    
    public double getRelativeWeightedFrequency() {
	return getWeightedFrequency() / parentDocument.getFancyLength(true);
    }
    
    
    public boolean addOccurence(int start, int end, String expression)
    {
	// especially in case of dependencies (gov/dep): make sure start is smaller than end
	if (start > end)
	{
	    int tmp = end;
            end = start;
            start = tmp;
	}
	
	// "instance"(example) might be different: root(ROOT, appeared) VS. nsubj(appeared,she)
        // but the indices & construction name will match => don't duplicate!
	if (hasOccurence(start, end) == true)
	    return false;
	
	occurrences.add(new Occurrence(getParentConstruction(), start, end, expression));
	return true;
    }
}

class DocumentConstructionDataFactory extends AbstractConstructionDataFactory
{
    private final Document			    parent;

    public DocumentConstructionDataFactory(Document parent)
    {
	this.parent = parent;
    }

    @Override
    public AbstractConstructionData create(Construction type) {
	return new DocumentConstructionData(type, parent);
    }
}