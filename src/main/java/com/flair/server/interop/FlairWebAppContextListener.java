
package com.flair.server.interop;

import com.flair.server.interop.messaging.ServerMessagingSwitchboard;
import com.flair.server.pipelines.exgen.ExerciseGenerationPipeline;
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
		ServerMessagingSwitchboard.get();
		ClientSessionManager.get();
		GramParsingPipeline.get();
		ExerciseGenerationPipeline.get();
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
		ExerciseGenerationPipeline.dispose();
		ClientSessionManager.dispose();
		ServerMessagingSwitchboard.dispose();
		ThreadPool.dispose();

		ServerLogger.get().exdent();
		ServerLogger.get().info("FLAIR Context deinitialized!");
	}
}
