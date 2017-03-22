package com.flair.shared.interop.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionManagementServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.SessionManagementService
     */
    void beginSession( AsyncCallback<com.flair.shared.interop.AuthToken> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.SessionManagementService
     */
    void endSession( com.flair.shared.interop.AuthToken token, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static SessionManagementServiceAsync instance;

        public static final SessionManagementServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (SessionManagementServiceAsync) GWT.create( SessionManagementService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instantiated
        }
    }
}
