/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
