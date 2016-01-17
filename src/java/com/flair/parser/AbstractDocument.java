/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.GrammaticalConstruction;
import com.flair.grammar.Language;
import java.io.Serializable;

/**
 * The interface all parsable documents must implement
 * @author shadeMe
 */
public interface AbstractDocument
{
    public Language					getLanguage();
    public String					getText();
    public String					getDescription();
    public DocumentConstructionData			getConstructionData(GrammaticalConstruction type);
    
    public double					getReadabilityScore();
    public String					getReadabilityLevel();
    
    public int						getNumCharacters();
    public int						getNumSentences();
    public int						getNumDependencies();
    
    public double					getAvgWordLength();
    public double					getAvgSentenceLength();
    public double					getAvgTreeDepth();
    
    public void						setNumCharacters(int value);
    public void						setNumSentences(int value);
    public void						setNumDependencies(int value);
    
    public void						setAvgWordLength(double value);
    public void						setAvgSentenceLength(double value);
    public void						setAvgTreeDepth(double value);
    
    public int						getLength();
    public void						setLength(int value);
    public double					getFancyLength();
    
    public boolean					isParsed();
    public void						flagAsParsed();
    
    public Serializable					getSerializable();
}

