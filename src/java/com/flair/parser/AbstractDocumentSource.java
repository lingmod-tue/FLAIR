/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Represents the source of a document object
 * @author shadeMe
 */
public interface AbstractDocumentSource
{
    public Language	    getLanguage();
    public String	    getSourceText();
    public String	    getDescription();
}
