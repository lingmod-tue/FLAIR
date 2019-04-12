package com.flair.shared.interop;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Indicates a fatal client-server interop error
 */
public class InvalidAuthTokenException extends RuntimeException implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2024964285747429072L;

	public InvalidAuthTokenException() {
		;//
	}

	public InvalidAuthTokenException(String arg0) {
		super(arg0);
	}

	public InvalidAuthTokenException(Throwable cause) {
		super(cause);
	}

	public InvalidAuthTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAuthTokenException(String message, Throwable cause, boolean enableSuppression,
	                                 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
