/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.GrammaticalConstruction;
import com.flair.utilities.JSONWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of related documents represeting a corpus
 * @author shadeMe
 */
public class DocumentCollection implements Iterable<AbstractDocument>
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
    
    public DocumentCollectionConstructionData getConstructionData(GrammaticalConstruction construction) {
	return (DocumentCollectionConstructionData)constructionData.getData(construction);
    }
    
    public synchronized int size() {
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
	    String outfile = path + "/";
	    writer.writeObject(dataStore.get(i).getSerializable(0), String.format("%03d", i), outfile);
	}
    }

    @Override
    public Iterator<AbstractDocument> iterator() {
	return dataStore.iterator();
    }
    
    public synchronized void sort() {
	Collections.sort(dataStore);
    }
    
    public synchronized AbstractDocument get(int i) 
    {
	if (i >= dataStore.size() || i < 0)
	    throw new IllegalArgumentException("Index must be 0 < " + i + " < " + dataStore.size());
	
	return dataStore.get(i);
    }
}