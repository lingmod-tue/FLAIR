/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

/**
 * Task manager specific constants
 * @author shadeMe
 */
public class Constants
{
    public static final int			PARSER_THREADPOOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    public static final int			PARSER_INSTANCEPOOL_SIZE = PARSER_THREADPOOL_SIZE;
    
    public static final int			TEXTFETCHER_THREADPOOL_SIZE = 10;
}
