/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import FLAIRGrammar.Construction;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of related documents represeting a corpus
 * @author shadeMe
 */
public class DocumentCollection
{
    private final List<AbstractDocument>		dataStore;
    private final ConstructionDataCollection		constructionData;
    
    public DocumentCollection()
    {
	dataStore = new ArrayList<>();
	constructionData = new ConstructionDataCollection(new DocumentCollectionConstructionDataFactory(this));
    }
    
    private void refreshConstructionData()
    {
	for (Construction itr : Construction.values())
	{
	    DocumentCollectionConstructionData data = (DocumentCollectionConstructionData)constructionData.getData(itr);
	    int occurrences = 0, docFreq = 0;
	    
	    for (AbstractDocument doc : dataStore)
	    {
		DocumentConstructionData docData = doc.getConstructionData(itr);
		if (docData.hasConstruction())
		{
		    occurrences += docData.getFrequency();
		    docFreq++;
		}
	    }
	    
	    data.calculateData(dataStore.size(), occurrences, docFreq);
	}
    }
    
    public synchronized int getCount() {
	return dataStore.size();
    }
    
    public synchronized void add(AbstractDocument doc, boolean recalcConstructionData)
    {
	dataStore.add(doc);
	if (recalcConstructionData)
	    refreshConstructionData();
    }
}