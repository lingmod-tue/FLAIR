/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Factory class for the Stanford CoreNLP parser
 * @author shadeMe
 */
public class StanfordDocumentParserFactory implements AbstractDocumentParserFactory
{
    private final AbstractDocumentFactory	docFactory;
    
    public StanfordDocumentParserFactory(AbstractDocumentFactory factory)
    {
	docFactory = factory;
    }
    
    @Override
    public AbstractDocumentParser create() {
	return new StanfordDocumentParser(docFactory);
    }
}
