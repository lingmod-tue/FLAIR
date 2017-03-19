package com.flair.shared.interop.services;

import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerMessage;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionManagementServiceAsync
{
	void 		beginSession(AsyncCallback<AuthToken> callback);
	void		endSession(AuthToken token, AsyncCallback<Void> callback);
}
