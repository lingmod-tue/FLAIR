/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Abstract factory class for the document parser
 * @author shadeMe
 */
public interface AbstractDocumentParserFactory
{
    public AbstractDocumentParser		    create();
}
