
package com.flair.server.utilities;

import com.flair.shared.utilities.AbstractDebugLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Thread-safe logger for server code
 */
public final class ServerLogger extends AbstractDebugLogger {
	private static final ServerLogger SINGLETON = new ServerLogger();

	private final Logger pipeline;

	private ServerLogger() {
		super("FLAIR-ServerLogger");
		this.pipeline = LoggerFactory.getLogger(loggerName);
	}

	public static ServerLogger get() {
		return SINGLETON;
	}

	private String prettyPrintCaller() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		// the calling method would be the fifth element in the array (the first would be the call to getStackTrace())
		StackTraceElement caller = stackTrace[4];
		return "{" + caller.getClassName().substring(10) + "." + caller.getMethodName() + "()}";
	}

	@Override
	protected void print(Channel channel, String message) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < indentLevel; i++)
			builder.append("\t");

		builder.append(prettyPrintCaller());
		builder.append(" ");
		builder.append(message);

		switch (channel) {
		case TRACE:
			pipeline.trace(builder.toString());
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
	public void error(Throwable ex, String message) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		error(message);
		error("Stacktrace: " + sw.toString());
	}
}
