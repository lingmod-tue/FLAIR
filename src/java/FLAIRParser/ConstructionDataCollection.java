/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import java.util.EnumMap;

/**
 * A table of construction data mapped to constructions
 * @author shadeMe
 */
class ConstructionDataCollection
{
    private final EnumMap<Construction, AbstractConstructionData>    dataStore;
    
    public ConstructionDataCollection(AbstractConstructionDataFactory prototypeFactory)
    {
	dataStore = new EnumMap<>(Construction.class);
	
	for (Construction itr : Construction.values())
	    dataStore.put(itr, prototypeFactory.create(itr));
    }
    
    public AbstractConstructionData getData(Construction type)
    {
	return dataStore.get(type);
    }
}
