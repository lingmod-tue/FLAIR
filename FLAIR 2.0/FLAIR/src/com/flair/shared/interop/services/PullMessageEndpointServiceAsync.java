package com.flair.shared.interop.services;

import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerMessage;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PullMessageEndpointServiceAsync
{
	void 		dequeueMessages(AuthToken token, AsyncCallback<ServerMessage[]> callback);
}
