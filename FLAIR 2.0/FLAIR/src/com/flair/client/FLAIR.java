package com.flair.client;


import com.flair.client.utilities.ClientLogger;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;



public class FLAIR implements EntryPoint
{
	public void onModuleLoad() 
	{
		GWT.setUncaughtExceptionHandler(e -> {
			if (GWT.isProdMode())
				ClientLogger.get().error(e, "Uncaught Exception");
			else
				Window.alert("Uncaught Exception! Details: " + e.toString());
		});
		
	    Scheduler.get().scheduleDeferred(() -> ClientEndPoint.get().init());
	}
}
