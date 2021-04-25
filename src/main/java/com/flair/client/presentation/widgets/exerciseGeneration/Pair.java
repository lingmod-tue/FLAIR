package com.flair.client.presentation.widgets.exerciseGeneration;

public class Pair<T1, T2>
{
    T1 key;
    T2 value;
    
    Pair(T1 key, T2 value) {  
    	this.key = key;  
    	this.value = value;
    }
    
    public T1 getKey()  { return this.key; }
    public T2 getValue()  { return this.value; }
}