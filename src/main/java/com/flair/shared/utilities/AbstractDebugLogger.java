package com.flair.shared.utilities;

/*
 * Base class for the client and server loggers
 */
public abstract class AbstractDebugLogger
{
	protected final String	loggerName;
	protected int			indentLevel;

	protected AbstractDebugLogger(String name) 
	{
		this.loggerName = name;
		this.indentLevel = 0;
	}
	
	protected enum Channel
	{
		TRACE, ERROR, WARN, INFO,
	}

	protected abstract void print(Channel channel, String message);	
	
	public void error(String message) {
		print(Channel.ERROR, message);
	}

	public void error(Throwable ex, String message) {
		error(message + ". Exception: " + ex.toString());
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
