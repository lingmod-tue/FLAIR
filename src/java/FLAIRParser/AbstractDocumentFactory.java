/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Interface used to create AbstractDocument objects
 * @author shadeMe
 */
public interface AbstractDocumentFactory
{
   public AbstractDocument	create(AbstractDocumentSource source);
}
