/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;


/**
 * Base strategy for the StanfordDocumentParser class
 * @author shadeMe
 */
public abstract class BasicStanfordDocumentParserStrategy implements AbstractParsingStrategy
{
    protected StanfordCoreNLP		    pipeline;
    
    public BasicStanfordDocumentParserStrategy()
    {
	pipeline = null;
    }
    
    public void setPipeline(StanfordCoreNLP pipeline)
    {
	assert pipeline != null;
	this.pipeline = pipeline;
    }
}
