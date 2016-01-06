/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Represents an abstract parsing logic
 * @author shadeMe
 */
public interface AbstractParsingStrategy
{
    boolean	isLanguageSupported(Language lang);
    boolean	apply(AbstractDocument docToParse);	    // returns true if successful, false otherwise
}