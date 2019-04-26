package com.flair.shared.interop.messages.server;

import com.flair.shared.interop.messages.BaseMessage;

public class SmError extends BaseMessage {
	private String message = "";
	private Throwable exception = null;
	private boolean fatal = false;

	public SmError() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SmError{" + identifier() + "}[");
		sb.append("message=" + message).append(" | ");
		if (exception != null)
			sb.append("exception=").append(exception.toString()).append(" | ");
		sb.append("fatal=" + fatal);
		return sb.append("]").toString();
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public boolean isFatal() {
		return fatal;
	}
	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}
}
