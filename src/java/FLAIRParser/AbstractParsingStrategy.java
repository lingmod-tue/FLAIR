/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Represents an abstract parsing logic
 * @author shadeMe
 */
public interface AbstractParsingStrategy
{
    boolean	apply(AbstractDocument docToParse);	    // returns true if successful, false otherwise
}