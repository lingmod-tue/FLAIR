package com.flair.client.utilities;

import com.flair.shared.utilities.AbstractDebugLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Simple client-side logger
 */
public final class ClientLogger extends AbstractDebugLogger {
	private static final ClientLogger SINGLETON = new ClientLogger();

	private final Logger pipeline;
	private int indentLevel;

	public ClientLogger() {
		super("FLAIR-ClientLogger");
		this.pipeline = Logger.getLogger(loggerName);
		this.indentLevel = 0;
	}

	public static ClientLogger get() {
		return SINGLETON;
	}

	@Override
	protected void print(Channel channel, String message) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < indentLevel; i++)
			builder.append("\t");

		builder.append(" ");
		builder.append(message);

		switch (channel) {
		case TRACE:
			pipeline.log(Level.FINE, builder.toString());
			break;
		case ERROR:
			pipeline.log(Level.SEVERE, builder.toString());
			break;
		case WARN:
			pipeline.log(Level.WARNING, builder.toString());
			break;
		case INFO:
			pipeline.log(Level.INFO, builder.toString());
		}
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
