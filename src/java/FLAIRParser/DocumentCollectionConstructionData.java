/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

/**
 * Represent the properties of a construction in the larger domain of a set of (related) documents
 * @author shadeMe
 */
public class DocumentCollectionConstructionData extends AbstractConstructionData
{
    private final DocumentCollection		    parentDocumentCollection;
    
    private int					    totalCount;		    // total no of occurrences of this construction in the collection
    private double				    averageCount;	    // total count / no of docs
    private int					    docFrequency;	    // no of docs in which this construction occurs
    private double				    invertedDocFrequency;   // log10(1 + N/df) (N=no of docs)
    
    public DocumentCollectionConstructionData(Construction type, DocumentCollection parent)
    {
	super(type);
	assert parent != null;
	parentDocumentCollection = parent;
	
	averageCount = totalCount = 0;
	invertedDocFrequency = docFrequency = 0;
    }
    
   
}


class DocumentCollectionConstructionDataFactory extends AbstractConstructionDataFactory
{
    private final DocumentCollection	    parent;

    public DocumentCollectionConstructionDataFactory(DocumentCollection parent)
    {
	this.parent = parent;
    }

    @Override
    public AbstractConstructionData create(Construction type) {
	return new DocumentCollectionConstructionData(type, parent);
    }
}