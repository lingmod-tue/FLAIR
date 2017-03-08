package com.flair.client;


import com.google.gwt.core.client.EntryPoint;



public class FLAIR implements EntryPoint
{
	public void onModuleLoad() {
		ClientEndPoint.get().init();
	}
}
