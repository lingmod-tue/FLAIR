
package com.flair.server.interop;

import com.flair.server.interop.session.SessionManager;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.questgen.QuestionGenerationPipeline;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Tracks the startup and shutdown of the web app
 */
public class FlairWebAppContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServerLogger.get().info("FLAIR Context initializing...");
		ServerLogger.get().indent();

		ThreadPool.get();
		SessionManager.get();
		GramParsingPipeline.get();
		QuestionGenerationPipeline.get();

		Thread.setDefaultUncaughtExceptionHandler((thread, thrwbl) -> {
			ServerLogger.get().error(thrwbl,
					"Uncaught exception in thread " + thread.getName() + ": " + thrwbl.toString());
		});

		ServerLogger.get().exdent();
		ServerLogger.get().info("FLAIR Context initialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServerLogger.get().info("FLAIR Context deinitializing...");
		ServerLogger.get().indent();

		QuestionGenerationPipeline.dispose();
		GramParsingPipeline.dispose();
		SessionManager.dispose();
		ThreadPool.dispose();

		ServerLogger.get().exdent();
		ServerLogger.get().info("FLAIR Context deinitialized!");
	}
}
