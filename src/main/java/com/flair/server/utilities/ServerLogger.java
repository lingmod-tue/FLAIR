
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
	private static ServerLogger SINGLETON = null;

	public static ServerLogger get() {
		if (SINGLETON == null) {
			synchronized (ServerLogger.class) {
				if (SINGLETON == null)
					SINGLETON = new ServerLogger();
			}
		}

		return SINGLETON;
	}

	private final class State {
		int indentLevel;
		StringBuilder accumulator;

		State() {
			indentLevel = 0;
			accumulator = new StringBuilder();
		}

		void print(Channel channel, String message) {
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < indentLevel; i++)
				builder.append("\t");

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

	    void indent() {
			++indentLevel;
		}

		void exdent() {
			if (indentLevel-- < 0)
				indentLevel = 0;
		}
	}

	private final Logger pipeline;
	private final ThreadLocal<State> state;

	private ServerLogger() {
		super("FLAIR-Log");
		this.pipeline = LoggerFactory.getLogger(loggerName);
		this.state = ThreadLocal.withInitial(State::new);
	}

	@Override
	protected void print(Channel channel, String message) {
		state.get().print(channel, message);
	}

	@Override
	public AbstractDebugLogger error(Throwable ex, String message) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		error(message);
		error("Stacktrace: " + sw.toString());
		return this;
	}

	public AbstractDebugLogger indent() {
		state.get().indent();
		return this;
	}
	public AbstractDebugLogger exdent() {
		state.get().exdent();
		return this;
	}
}
