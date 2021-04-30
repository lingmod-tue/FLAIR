package com.flair.server.pipelines.exgen;

import java.util.ArrayList;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.HtmlManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.DownloadedResource;
import com.flair.server.scheduler.AsyncTask;
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
        ResourceDownloader resourceDownloader = new ResourceDownloader(downloadResources);
		HtmlManager htmlManager = new HtmlManager();
        Element doc = htmlManager.prepareHtml(url, downloadResources, resourceDownloader);
        
		Result result = new Result(doc, url, resourceDownloader.getDownloadedResources());
		ServerLogger.get().trace("Downloaded web page");
		return result;
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
