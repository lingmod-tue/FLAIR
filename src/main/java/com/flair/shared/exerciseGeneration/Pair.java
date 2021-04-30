package com.flair.shared.exerciseGeneration;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Pair<T1, T2> implements IsSerializable {
    
    public Pair() {}
    
    public Pair(T1 first, T2 second) {  
    	this.first = first;  
    	this.second = second;
    }
    
    public T1 first;
    public T2 second;
}