package com.flair.client;


import com.flair.client.model.ClientEndPoint;
import com.google.gwt.core.client.EntryPoint;



public class FLAIR implements EntryPoint
{
	public void onModuleLoad() {
		ClientEndPoint.get().init();
	}
}
