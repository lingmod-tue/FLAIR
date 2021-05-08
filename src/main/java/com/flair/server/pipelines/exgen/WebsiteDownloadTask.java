package com.flair.server.pipelines.exgen;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.HtmlManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.DownloadedResource;
import com.flair.server.scheduler.AsyncTask;
import com.flair.server.scheduler.ThreadPool;
import com.flair.server.utilities.ServerLogger;

public class WebsiteDownloadTask implements AsyncTask<WebsiteDownloadTask.Result> {
	public static WebsiteDownloadTask factory(String url) {
		return new WebsiteDownloadTask(url);
	}

	private final String url;
	private final boolean downloadResources = false;	// for debugging, we don't download resources

	private WebsiteDownloadTask(String url) {
		this.url = url;
	}


	@Override
	public Result run() {		
		Element doc;
		long startTime = 0;
		boolean error = false;
		ResourceDownloader resourceDownloader = new ResourceDownloader(downloadResources);

		try {
			startTime = System.currentTimeMillis();
			
			HtmlManager htmlManager = new HtmlManager();
			doc = ThreadPool.get().invokeAndWait(new FutureTask<>(() -> {
		        return htmlManager.prepareHtml(url, downloadResources, resourceDownloader);		        
			}), Constants.DOWNLOAD_TASK_TIMEOUT, Constants.TIMEOUT_UNIT);
		} catch (TimeoutException ex) {
			ServerLogger.get().error("Webpage download task timed-out for " + url + ".");
			doc = null;
			error = true;
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Webpage download task encountered an error. Exception: " + ex.toString());
			doc = null;
			error = true;
		}

		long endTime = System.currentTimeMillis();
		if (!error)
			ServerLogger.get().info("Webpage downloaded in " + (endTime - startTime) + " ms");
		
		return new Result(doc, url, resourceDownloader.getDownloadedResources());
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
