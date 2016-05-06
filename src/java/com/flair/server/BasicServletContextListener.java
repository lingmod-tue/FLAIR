/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server;

import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Tracks the startup and shutdown of the web app
 * @author shadeMe
 */
public class BasicServletContextListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce) 
    {
	FLAIRLogger.get().info("FLAIR Context initializing...");
	FLAIRLogger.get().indent();
	
	MasterJobPipeline.get();
	SessionManager.get();
	
	FLAIRLogger.get().exdent();
	FLAIRLogger.get().info("FLAIR Context initialized!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
	FLAIRLogger.get().info("FLAIR Context deinitializing...");
	FLAIRLogger.get().indent();
	
	MasterJobPipeline.dispose();
	SessionManager.dispose();
	
	FLAIRLogger.get().exdent();
	FLAIRLogger.get().info("FLAIR Context deinitialized!");
    }
}