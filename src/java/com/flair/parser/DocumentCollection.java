/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.GrammaticalConstruction;
import com.flair.utilities.JSONWriter;
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
	for (GrammaticalConstruction itr : GrammaticalConstruction.values())
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
    
    public synchronized void serialize(JSONWriter writer, String path)
    {
	if (dataStore.isEmpty())
	    return;
	    
	for (int i = 0; i < dataStore.size(); i++)
	{
	    String outfile = path + "/" + String.format("%03d", i);
	    writer.writeObject(dataStore.get(i).getSerializable(), String.format("%03d", i) + ".json", outfile);
	}
    }
}