/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server;

import com.flair.taskmanager.MasterJobPipeline;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Tracks the startup and shutdown of web app
 * @author shadeMe
 */
public class BasicServletContextListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce) 
    {
	MasterJobPipeline.get();
	SessionManager.get();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
	// nothing yet
    }
}
