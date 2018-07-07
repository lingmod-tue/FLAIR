/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.crawler.impl.azure;

import com.flair.server.crawler.impl.AbstractSearchAgentImpl;
import com.flair.server.crawler.impl.AbstractSearchAgentImplResult;
import com.flair.server.utilities.HttpClientFactory;
import com.flair.server.utilities.ServerLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic web search implementation that uses the Azure Search API
 *
 * @author shadeMe
 */
public class AzureWebSearch implements AbstractSearchAgentImpl {
	private static final String AZURESEARCH_SCHEME = "https";
	private static final String AZURESEARCH_HOSTNAME = "api.cognitive.microsoft.com";
	private static final String AZURESEARCH_PATH = "/bing/v5.0/search";

	private static final String RESPONSE_FILTER = "Webpages";
	private static final int RESULTS_PER_PAGE = 10;

	private final List<AzureWebSearchResult> results;
	private final Gson deserializer;

	private HttpResponse responsePost;
	private HttpEntity resEntity;

	private String apiKey;
	private String query;
	private String market;
	private int perPage;
	private int skip;

	public AzureWebSearch() {
		this.results = new ArrayList<>();
		this.deserializer = new GsonBuilder().setPrettyPrinting().create();

		apiKey = query = market = "";
		perPage = RESULTS_PER_PAGE;
		skip = 0;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
		if (this.skip < 0)
			this.skip = 0;
	}

	public void nextPage() {
		setSkip(getSkip() + getPerPage());
	}

	public void setPage(int page) {
		setSkip(getPerPage() * (page - 1));
	}

	public List<AzureWebSearchResult> getResults() {
		return results;
	}

	private String getUrlQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("q=");
		sb.append(getQuery());

		sb.append("&count=");
		sb.append(getPerPage());
		sb.append("&offset=");
		sb.append(getSkip());

		if (market.isEmpty() == false) {
			sb.append("&mkt=");
			sb.append(getMarket());
		}

		sb.append("&responseFilter=");
		sb.append(RESPONSE_FILTER);

		return sb.toString();
	}

	private void loadResults(InputStream stream) {
		results.clear();
		AzureWebSearchResponse response = deserializer.fromJson(new InputStreamReader(stream),
				AzureWebSearchResponse.class);
		if (response != null) {
			WebAnswer answer = response.webPages;
			if (answer != null && answer.value.length != 0) {
				// ### presumably we don't need to rerank the results according to the RankingResponse as we aren't mixing result types
				for (WebAnswer.Webpage itr : answer.value) {
					AzureWebSearchResult newResult = new AzureWebSearchResult(itr.name, itr.url, itr.displayUrl,
							itr.snippet);
					results.add(newResult);
				}
			}
		}
	}

	private void doQuery() {
		try {
			String full_query = getUrlQuery();
			URI uri = new URI(AZURESEARCH_SCHEME, AZURESEARCH_HOSTNAME, AZURESEARCH_PATH, full_query, null);
			// Bing and java URI disagree about how to represent + in query
			// parameters. This is what we have to do instead...
			uri = new URI(uri.getScheme() + "://" + uri.getAuthority() + uri.getPath() + "?"
					+ uri.getRawQuery().replace("+", "%2b"));

			HttpGet get = new HttpGet(uri);
			get.addHeader("Accept", "application/json");
			get.addHeader("Ocp-Apim-Subscription-Key", getApiKey());

			HttpClient client = HttpClientFactory.get().create();
			responsePost = client.execute(get);
			resEntity = responsePost.getEntity();

			loadResults(resEntity.getContent());
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Couldn't fetch search results from Azure. Exception: " + ex.toString());
		}
	}

	@Override
	public List<? extends AbstractSearchAgentImplResult> performSearch() {
		doQuery();
		return getResults();
	}
}