/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

/**
 * Interface used to create AbstractDocument objects
 * @author shadeMe
 */
public interface AbstractDocumentFactory
{
   public AbstractDocument	create(AbstractDocumentSource source);
}