/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.GrammaticalConstruction;

/**
 * Basic interface for construction specific data
 * @author shadeMe
 */
abstract class AbstractConstructionData
{
    private final GrammaticalConstruction		    parentConstruction;
    
    public AbstractConstructionData(GrammaticalConstruction parent) {
	parentConstruction = parent;
    }
    
    public GrammaticalConstruction getParentConstruction() {
	return parentConstruction;
    }
    
    public boolean equals(AbstractConstructionData rhs) {
	return parentConstruction == rhs.parentConstruction;
    }
}

abstract class AbstractConstructionDataFactory
{
    public abstract AbstractConstructionData create(GrammaticalConstruction type);
}
