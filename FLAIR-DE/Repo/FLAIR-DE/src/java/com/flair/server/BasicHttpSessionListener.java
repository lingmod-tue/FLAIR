/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
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
