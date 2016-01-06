/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.GrammaticalConstruction;
import java.util.EnumMap;

/**
 * A table of construction data mapped to constructions
 * @author shadeMe
 */
class ConstructionDataCollection
{
    private final EnumMap<GrammaticalConstruction, AbstractConstructionData>    dataStore;
    
    public ConstructionDataCollection(AbstractConstructionDataFactory prototypeFactory)
    {
	dataStore = new EnumMap<>(GrammaticalConstruction.class);
	
	for (GrammaticalConstruction itr : GrammaticalConstruction.values())
	    dataStore.put(itr, prototypeFactory.create(itr));
    }
    
    public AbstractConstructionData getData(GrammaticalConstruction type)
    {
	return dataStore.get(type);
    }
}
