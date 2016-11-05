/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.grammar.Language;

/**
 * Represents an abstract document parser
 * @author shadeMe
 */
public abstract class AbstractDocumentParser
{
    protected final AbstractDocumentFactory	    docFactory;
    
    public AbstractDocumentParser(AbstractDocumentFactory factory)
    {
	assert factory != null;
	docFactory = factory;
    }
    
    public abstract boolean		    isLanguageSupported(Language lang);
    public abstract AbstractDocument	    parse(AbstractDocumentSource source, AbstractParsingStrategy strategy);
}
