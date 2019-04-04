package com.flair.shared.utilities;

/*
 * Base class for the client and server loggers
 */
public abstract class AbstractDebugLogger {
	protected final String loggerName;
	protected int indentLevel;

	protected AbstractDebugLogger(String name) {
		this.loggerName = name;
		this.indentLevel = 0;
	}

	protected enum Channel {
		TRACE, ERROR, WARN, INFO,
	}

	protected abstract void print(Channel channel, String message);

	public AbstractDebugLogger error(String message) {
		print(Channel.ERROR, message);
		return this;
	}

	public AbstractDebugLogger error(Throwable ex, String message) {
		error(message + ". Exception: " + ex.toString());
		return this;
	}

	public AbstractDebugLogger info(String message) {
		print(Channel.INFO, message);
		return this;
	}

	public AbstractDebugLogger trace(String message) {
		print(Channel.TRACE, message);
		return this;
	}

	public AbstractDebugLogger warn(String message) {
		print(Channel.WARN, message);
		return this;
	}

	public AbstractDebugLogger indent() {
		indentLevel++;
		return this;
	}

	public AbstractDebugLogger exdent() {
		indentLevel--;
		if (indentLevel < 0)
			indentLevel = 0;

		return this;
	}
}
