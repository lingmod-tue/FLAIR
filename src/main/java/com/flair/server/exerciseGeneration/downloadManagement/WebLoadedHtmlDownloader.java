package com.flair.server.exerciseGeneration.downloadManagement;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Html downloader which executes JavaScript and Ajax scripts before downloading the resulting DOM
 */
public class WebLoadedHtmlDownloader implements WebDownloader {

    @Override
    public Document download(String url) {
        // Turn off console logging
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        Document document = null;
        try (WebClient webClient = new WebClient()){
            // Execute all scripts before downloading the DOM
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());

            HtmlPage page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(5000);

            ScriptResult scriptResult = page.executeJavaScript("document.documentElement.outerHTML;");

            document = Jsoup.parse(scriptResult.getJavaScriptResult().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

}