/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import FLAIRGrammar.Construction;

/**
 * The interface all parsable documents must implement
 * @author shadeMe
 */
public interface AbstractDocument
{
    public String					getText();
    public DocumentConstructionData			getConstructionData(Construction type);
    
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
}

