package com.flair.shared.interop.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PullMessageEndpointServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.PullMessageEndpointService
     */
    void dequeueMessages( com.flair.shared.interop.AuthToken token, AsyncCallback<com.flair.shared.interop.ServerMessage[]> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static PullMessageEndpointServiceAsync instance;

        public static final PullMessageEndpointServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (PullMessageEndpointServiceAsync) GWT.create( PullMessageEndpointService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instantiated
        }
    }
}
