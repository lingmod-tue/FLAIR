/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Represents the source of a document object
 * @author shadeMe
 */
public interface AbstractDocumentSource extends Comparable<AbstractDocumentSource>
{
    public Language	    getLanguage();
    public String	    getSourceText();
    public String	    getDescription();
}
