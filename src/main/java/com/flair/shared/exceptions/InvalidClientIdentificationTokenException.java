package com.flair.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Indicates a fatal client-server interop error
 */
public class InvalidClientIdentificationTokenException extends RuntimeException implements IsSerializable {
	public InvalidClientIdentificationTokenException() {
		;//
	}

	public InvalidClientIdentificationTokenException(String arg0) {
		super(arg0);
	}

	public InvalidClientIdentificationTokenException(Throwable cause) {
		super(cause);
	}

	public InvalidClientIdentificationTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidClientIdentificationTokenException(String message, Throwable cause, boolean enableSuppression,
	                                                 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
