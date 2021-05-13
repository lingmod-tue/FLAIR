package com.flair.server.exerciseGeneration.downloadManagement;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.flair.server.utilities.ServerLogger;

/**
 * Downloads the DOM of a web page consisting of the initial HTML
 */
public class WebBasicHtmlDownloader implements WebDownloader{

	/**
	 * Trusts all certificates.
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	 public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
	        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        });
	 
	        SSLContext context = SSLContext.getInstance("TLS");
	        context.init(null, new X509TrustManager[]{new X509TrustManager() {
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }
	 
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }
	 
	            public X509Certificate[] getAcceptedIssuers() {
	                return new X509Certificate[0];
	            }
	        }}, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	    }
	 
    @Override
    public Document download(String url) {
        try {
        	enableSSLSocket();
        	 
        	return Jsoup.connect(url).get();
            /*return Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .execute().parse();   */  
        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
			ServerLogger.get().error(e, "Document could not be downloaded. Exception: " + e.toString());
		} 

        return null;
    }
}