package com.flair.shared.interop.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WebRankerServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.WebRankerService
     */
    void beginWebSearch( com.flair.shared.interop.AuthToken token, com.flair.shared.grammar.Language lang, java.lang.String query, int numResults, java.util.ArrayList<java.lang.String> keywords, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.WebRankerService
     */
    void beginCorpusUpload( com.flair.shared.interop.AuthToken token, com.flair.shared.grammar.Language lang, java.util.ArrayList<java.lang.String> keywords, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.WebRankerService
     */
    void endCorpusUpload( com.flair.shared.interop.AuthToken token, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.flair.shared.interop.services.WebRankerService
     */
    void cancelCurrentOperation( com.flair.shared.interop.AuthToken token, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static WebRankerServiceAsync instance;

        public static final WebRankerServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (WebRankerServiceAsync) GWT.create( WebRankerService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instantiated
        }
    }
}
