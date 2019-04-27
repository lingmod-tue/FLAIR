package com.flair.shared.interop.messaging.server;

import com.flair.shared.interop.messaging.Message;

public class SmError implements Message.Payload {
	private String message = "";
	private Throwable exception = null;
	private boolean fatal = false;

	public SmError() {}

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
	@Override
	public String name() {
		return "SmError";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("message=" + message).append(" | ");
		if (exception != null)
			sb.append("exception=").append(exception.toString()).append(" | ");
		sb.append("fatal=" + fatal);
		return sb.toString();
	}
}
