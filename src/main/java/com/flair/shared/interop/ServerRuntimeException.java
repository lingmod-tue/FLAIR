package com.flair.shared.interop;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Indicates a non-fatal but potentially serious server error
 */
public class ServerRuntimeException extends RuntimeException implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2024964285747429072L;

	public ServerRuntimeException() {
		;//
	}

	public ServerRuntimeException(String arg0) {
		super(arg0);
	}

	public ServerRuntimeException(Throwable cause) {
		super(cause);
	}

	public ServerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerRuntimeException(String message, Throwable cause, boolean enableSuppression,
	                              boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
