package com.flair.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;

public class FLAIR implements EntryPoint {
	@Override
	public void onModuleLoad() {
		//		GWT.setUncaughtExceptionHandler(e -> {
		//			ClientLogger.get().error(e, "Uncaught exception!");
		//			ClientEndPoint.get().fatalServerError();
		//		});
		Scheduler.get().scheduleDeferred(() -> ClientEndPoint.get().init());
	}
}
