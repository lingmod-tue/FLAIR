/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.interop;

import com.flair.server.interop.session.SessionManager;
import com.flair.server.taskmanager.MasterJobPipeline;
import com.flair.server.utilities.ServerLogger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Tracks the startup and shutdown of the web app
 * 
 * @author shadeMe
 */
public class BasicServletContextListener implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent sce) 
	{
		ServerLogger.get().info("FLAIR Context initializing...");
		ServerLogger.get().indent();

		MasterJobPipeline.get();
		SessionManager.get();

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable thrwbl) {
				ServerLogger.get().error(thrwbl,
						"Uncaught exception in thread " + thread.getName() + ": " + thrwbl.toString());
			}
		});

		ServerLogger.get().exdent();
		ServerLogger.get().info("FLAIR Context initialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) 
	{
		ServerLogger.get().info("FLAIR Context deinitializing...");
		ServerLogger.get().indent();

		MasterJobPipeline.dispose();
		SessionManager.dispose();

		ServerLogger.get().exdent();
		ServerLogger.get().info("FLAIR Context deinitialized!");
	}
}
