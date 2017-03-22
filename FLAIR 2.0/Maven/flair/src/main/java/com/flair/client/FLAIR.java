package com.flair.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;

public class FLAIR implements EntryPoint
{
	public void onModuleLoad() {
		Scheduler.get().scheduleDeferred(() -> ClientEndPoint.get().init());
	}
}
