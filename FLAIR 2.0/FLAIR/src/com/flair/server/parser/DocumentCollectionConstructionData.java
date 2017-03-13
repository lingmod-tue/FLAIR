/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.parser;

import com.flair.shared.grammar.GrammaticalConstruction;


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
    
    public DocumentCollectionConstructionData(GrammaticalConstruction type, DocumentCollection parent)
    {
	super(type);
	assert parent != null;
	parentDocumentCollection = parent;
	
	averageCount = totalCount = 0;
	invertedDocFrequency = docFrequency = 0;
    }
    
   public int getTotalCount() {
       return totalCount;
   }
   
   public double getAverageCount() {
       return averageCount;
   } 
   
   public int getDocFrequency() {
       return docFrequency;
   }
   
   public double getInvertedDocFrequency() {
       return invertedDocFrequency;
   }
   
   public void calculateData(int totalDocCount, int occurrencesInCollection, int numDocsWithOccurrences)
   {
       totalCount = occurrencesInCollection;
       docFrequency = numDocsWithOccurrences;
       
       if (totalDocCount != 0)
	   averageCount = (double)totalCount / (double)totalDocCount;
       else
	   averageCount = 0;
       
       if (docFrequency != 0)
	   invertedDocFrequency = Math.log10((1 + totalDocCount) / docFrequency);
       else
	   invertedDocFrequency = 0;
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
    public AbstractConstructionData create(GrammaticalConstruction type) {
	return new DocumentCollectionConstructionData(type, parent);
    }
}