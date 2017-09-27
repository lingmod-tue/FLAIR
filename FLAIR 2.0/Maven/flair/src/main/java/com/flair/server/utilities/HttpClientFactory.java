package com.flair.server.utilities;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/*
 * Generates HttpClient instances for crawlers
 */
public class HttpClientFactory
{
	private static final HttpClientFactory			INSTANCE = new HttpClientFactory();
	
	public static HttpClientFactory get() {
		return INSTANCE;
	}
	
	/*
	 * Hack to allow all SSL connections
	 * NOTE: ONLY USE THIS ON ENTIRELY TRUSTED NETWORKS
	 */
	private static class FullTrustSSLSocketFactory extends SSLSocketFactory {
	    SSLContext sslContext = SSLContext.getInstance("TLS");

	    public FullTrustSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
	        super(truststore);

	        TrustManager tm = new X509TrustManager() {
	            @Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            @Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            @Override
				public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };

	        sslContext.init(null, new TrustManager[] { tm }, null);
	    }

	    @Override
	    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
	        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	    }

	    @Override
	    public Socket createSocket() throws IOException {
	        return sslContext.getSocketFactory().createSocket();
	    }
	}
	
	private enum Type
	{
		DEFAULT,
		FULL_TRUST
	}
	
	private final Type			type;
	
	private HttpClientFactory()
	{
		// HACK!
		type = Type.FULL_TRUST;
	}
	
	private HttpClient getDefaultHttpClient()
	{
		HttpClient httpClient = HttpClients.custom()
		        .setDefaultRequestConfig(RequestConfig.custom()
		            .setCookieSpec(CookieSpecs.STANDARD).build())
		        .build();
		
		return httpClient;
	}
	
	public HttpClient create()
	{
		switch (type)
		{
		case FULL_TRUST:
			{
				try
				{
			        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			        trustStore.load(null, null);

			        FullTrustSSLSocketFactory sf = new FullTrustSSLSocketFactory(trustStore);
			        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			        HttpParams params = new BasicHttpParams();
			        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			        SchemeRegistry registry = new SchemeRegistry();
			        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			        registry.register(new Scheme("https", sf, 443));

			        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			        return new DefaultHttpClient(ccm, params);
			       }
				catch (Throwable ex)
				{
			    	ServerLogger.get().error(ex, "Couldn't create custom HttpClient. Falling back to defaualt");
			    	return getDefaultHttpClient();
			    }
			}
		case DEFAULT:
		default:
			return getDefaultHttpClient();
		}
	}
}
