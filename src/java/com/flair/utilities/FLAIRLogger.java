/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe logger
 * @author shadeMe
 */
public final class FLAIRLogger
{
    private static final FLAIRLogger	    SINGLETON = new FLAIRLogger();
    
    private final Logger	    pipeline;
    private int			    indentLevel;

    public FLAIRLogger() 
    {
	this.pipeline = LoggerFactory.getLogger("FLogger");
	this.indentLevel = 0;
    }
    
    public static FLAIRLogger get() {
	return SINGLETON;
    }
    
    private String prettyPrintCaller()
    {
	StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	
	// the calling method would be the fifth element in the array (the first would be the call to getStackTrace())
	assert stackTrace.length > 4;
	StackTraceElement caller = stackTrace[4];
	
	return "{" + caller.getClassName() + "." + caller.getMethodName() + "()}";
    }
    
    private enum Channel
    {
	TRACE,
	ERROR,
	WARN,
	INFO,
    }
    
    private void print(Channel channel, String message)
    {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < indentLevel; i++)
	    builder.append("\t");
	
	builder.append(prettyPrintCaller());
	builder.append(" ");
	builder.append(message);
	
	switch (channel)
	{
	    case TRACE:
		pipeline.info(builder.toString());
		break;
	    case ERROR:
		pipeline.error(builder.toString());
		break;
	    case WARN:
		pipeline.warn(builder.toString());
		break;
	    case INFO:
	    default:
		pipeline.info(builder.toString());
	}
	
    }
    
    public void error(String message) {
	print(Channel.ERROR, message);
    }
    
    public void info(String message) {
	print(Channel.INFO, message);
    }
    
    public void trace(String message) {
	print(Channel.TRACE, message);
    }
    
    public void warn(String message) {
	print(Channel.WARN, message);
    }
    
    public void indent() {
	indentLevel++;
    }
    
    public void exdent() 
    {
	indentLevel--;
	if (indentLevel < 0)
	    indentLevel = 0;
    }
}
