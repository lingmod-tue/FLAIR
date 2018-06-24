/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.parser;

/**
 * Abstract factory class for the AbstractDocumentKeywordSearcher class
 * @author shadeMe
 */
public interface AbstractDocumentKeywordSearcherFactory
{
    public AbstractDocumentKeywordSearcher		    create();
}
