package com.flair.server.pipelines.exgen;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.HtmlManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.DownloadedResource;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

import edu.stanford.nlp.util.Pair;

public class WebsiteDownloadTask implements AsyncTask<WebsiteDownloadTask.Result> {
	public static WebsiteDownloadTask factory(String url, ResourceDownloader resourceDownloader) {
		return new WebsiteDownloadTask(url, resourceDownloader);
	}

	private final String url;
	ResourceDownloader resourceDownloader;

	private WebsiteDownloadTask(String url, ResourceDownloader resourceDownloader) {
		this.url = url;
		this.resourceDownloader = resourceDownloader;
	}


	@Override
	public Result run() {		
		long startTime = 0;
		boolean error = false;
		Element doc = null;
		ArrayList<DownloadedResource> downloadedResources = new ArrayList<DownloadedResource>();
		
		try {
			startTime = System.currentTimeMillis();
			
			HtmlManager htmlManager = new HtmlManager();
			Pair<Element, ArrayList<DownloadedResource>> output = ThreadPool.get().invokeAndWait(new FutureTask<>(() -> {
		        return htmlManager.getHtml(url, resourceDownloader);		        
			}), Constants.DOWNLOAD_TASK_TIMEOUT, Constants.TIMEOUT_UNIT);
			if(output != null) {
				doc = output.first;
				if(output.second != null) {
					downloadedResources = output.second;
				}
			}
		} catch (TimeoutException ex) {
			ServerLogger.get().error("Webpage download task timed-out for " + url + ".");
			error = true;
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Webpage download task encountered an error. Exception: " + ex.toString());
			error = true;
		}

		long endTime = System.currentTimeMillis();
		if (!error)
			ServerLogger.get().info("Webpage " + url + " downloaded in " + (endTime - startTime) + " ms");
		
		return new Result(doc, url, downloadedResources);
	}

	static final class Result {
		final Element document;
		final String url;
		final ArrayList<DownloadedResource> resources;

		Result(Element document, String url, ArrayList<DownloadedResource> resources) {
			this.document = document;
			this.url = url;
			this.resources = resources;
		}
	}
}
