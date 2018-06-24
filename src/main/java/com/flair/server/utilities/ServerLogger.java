/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flair.shared.utilities.AbstractDebugLogger;

/**
 * Thread-safe logger for server code
 * 
 * @author shadeMe
 */
public final class ServerLogger extends AbstractDebugLogger
{
	private static final ServerLogger SINGLETON = new ServerLogger();

	private final Logger	pipeline;

	private ServerLogger()
	{
		super("FLAIR-ServerLogger");
		this.pipeline = LoggerFactory.getLogger(loggerName);
	}

	public static ServerLogger get() {
		return SINGLETON;
	}
	
	private String prettyPrintCaller() 
	{
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		// the calling method would be the fifth element in the array (the first would be the call to getStackTrace())
		StackTraceElement caller = stackTrace[4];
		return "{" + caller.getClassName().substring(10) + "." + caller.getMethodName() + "()}";
	}

	@Override
	protected void print(Channel channel, String message)
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
	
	@Override
	public void error(Throwable ex, String message) 
	{
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		error(message);
		error("Stacktrace: " + sw.toString());
	}
}
