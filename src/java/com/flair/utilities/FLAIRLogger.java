/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Thread-safe logger
 * @author shadeMe
 */
public class FLAIRLogger
{
    static final Logger LOGGER = LogManager.getLogger(FLAIRLogger.class.getName());
    
    public static void trace(String message)
    {
	LOGGER.error(message);
    }
}
