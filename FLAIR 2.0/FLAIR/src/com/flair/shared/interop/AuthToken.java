package com.flair.shared.interop;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Token used to uniquely identify the client and transmit basic session state
 * Validated by the server for every request
 */
public interface AuthToken extends IsSerializable
{
	// ### TODO does GWT need the concrete type to correctly serialize objects?
	public enum Status
	{
		VALID,							// working state
		INVALID_SESSION_EXISTS,			// another session exists for the client
		INVALID_SERVER_ERROR			// internal server error
	}
	
	public Status			getStatus();
}
