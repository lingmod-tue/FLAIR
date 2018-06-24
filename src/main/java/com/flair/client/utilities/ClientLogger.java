package com.flair.client.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.flair.shared.utilities.AbstractDebugLogger;

/*
 * Simple client-side logger
 */
public final class ClientLogger extends AbstractDebugLogger
{
	private static final ClientLogger SINGLETON = new ClientLogger();

	private final Logger	pipeline;

	public ClientLogger()
	{
		super("FLAIR-ClientLogger");
		this.pipeline = Logger.getLogger(loggerName);
	}

	public static ClientLogger get() {
		return SINGLETON;
	}

	@Override
	protected void print(Channel channel, String message) 
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < indentLevel; i++)
			builder.append("\t");

		builder.append(" ");
		builder.append(message);

		switch (channel)
		{
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
}
