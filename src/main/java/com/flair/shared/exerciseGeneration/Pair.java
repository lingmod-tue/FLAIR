package com.flair.shared.exerciseGeneration;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Pair<T1, T2> implements IsSerializable {
    T1 key;
    T2 value;
    
    public Pair() {}
    
    public Pair(T1 key, T2 value) {  
    	this.key = key;  
    	this.value = value;
    }
    
    public T1 getKey()  { return this.key; }
    public T2 getValue()  { return this.value; }
}