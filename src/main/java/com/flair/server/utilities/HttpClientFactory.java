package com.flair.server.utilities;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

/*
 * Generates HttpClient instances for crawlers
 */
public class HttpClientFactory
{
	private static final int						CONNECT_TIMEOUT 			= 10 * 1000;
	private static final int						REQUEST_TIMEOUT 			= 10 * 1000;
	private static final int						SOCKET_TIMEOUT 				= 10 * 1000;
	
	private static final HttpClientFactory			INSTANCE = new HttpClientFactory();
	
	public static HttpClientFactory get() {
		return INSTANCE;
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
	
	private RequestConfig getDefaultRequestConfig()
	{
		RequestConfig config =  RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();
		
		return config;
	}
	
	private HttpClient getDefaultHttpClient()
	{
		HttpClient httpClient = HttpClients.custom()
		        .setDefaultRequestConfig(getDefaultRequestConfig())
		        .build();
		
		return httpClient;
	}
	
	public HttpClient create()
	{
		switch (type)
		{
		case FULL_TRUST:
			{
				/*
				 * Hack to ignore SSL self-signed certificate errors
				 * NOTE: ONLY USE THIS ON ENTIRELY TRUSTED NETWORKS
				 */
				try
				{
					HttpClientBuilder b = HttpClientBuilder.create();

					SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
					b.setSslcontext(sslContext);

					// or SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
					HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

					SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
					Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					        .register("http", PlainConnectionSocketFactory.getSocketFactory())
					        .register("https", sslSocketFactory)
					        .build();

					// allows multi-threaded use
					PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
					b.setConnectionManager( connMgr);
					
					b.setDefaultRequestConfig(getDefaultRequestConfig());
					return b.build();
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
