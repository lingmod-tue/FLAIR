/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Basic interface for construction specific data
 * @author shadeMe
 */
public abstract class AbstractConstructionData
{
    private final Construction		    parentConstruction;
    
    public AbstractConstructionData(Construction parent)
    {
	parentConstruction = parent;
    }
    
    public Construction getParentConstruction() {
	return parentConstruction;
    }
    
    public boolean equals(AbstractConstructionData rhs)
    {
	return parentConstruction == rhs.parentConstruction;
    }
}

abstract class AbstractConstructionDataFactory
{
    public abstract AbstractConstructionData create(Construction type);
}
