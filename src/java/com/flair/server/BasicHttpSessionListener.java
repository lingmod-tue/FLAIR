/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Handles HTTP session creation and deletion(in the servlet context)
 * @author shadeMe
 */
public class BasicHttpSessionListener implements HttpSessionListener
{
    @Override
    public void sessionCreated(HttpSessionEvent hse)
    {
	// nothing yet
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse)
    {
	// nothing yet
    }
}
