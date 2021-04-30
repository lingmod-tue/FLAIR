package com.flair.server.exerciseGeneration.downloadManagement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HtmlManager {

    /**
     * Replaces tags which cannot be embedded in an existing html page
     * @param doc   The HTML document
     * @return      The modified HTML document as string
     */
    public static Element makeHtmlEmbeddable(Element doc){
        //remove anything outside the html tag
        Element e = doc.selectFirst("html");

        //replace html, body and head tags with div tags
        e.tagName("article");
        e.addClass("htmlreplacement");
        Elements elements = e.select("body");
        elements.addClass("bodyreplacement");
        elements.tagName("section");
        elements = e.select("head");
        if(elements != null && elements.size() > 0) {
            e.insertChildren(0, elements.first().children());
            elements.remove();
        }

        return e;
    }

    /**
     * Downloads the html document, makes resource urls (including in style tags) absolute and removes script tags
     * Extracts question elements from the html document
     * @param url                   The url of the website
     * @param downloadResources     The downloaded resources of all HTML pages
     * @param resourceDownloader    The Resource downloader
     * @return                      The html document
     */
    public Element prepareHtml(String url, boolean downloadResources, ResourceDownloader resourceDownloader) {
        Document doc;

        // This parser is not working out. We get really ugly results and it's never better than with the basic loader.
        /*
        try {
            // We first try to download the page after executing javascript and ajax contents
            WebLoadedHtmlDownloader downlaoder = new WebLoadedHtmlDownloader();
            doc = downlaoder.download(url);

            // this gives us weird results for noscript tags (it escapes all the html characters)
            for(Element noscriptElement : doc.select("noscript")) {
                noscriptElement.remove();
            }
        } catch (Exception e) {
            // If the first approach fails, we just download the html content
            WebBasicHtmlDownloader downloader = new WebBasicHtmlDownloader();
            doc = downloader.download(url);
        }

         */

        WebBasicHtmlDownloader downloader = new WebBasicHtmlDownloader();
        doc = downloader.download(url);

        if(doc != null) {
            for (Element element : doc.getAllElements()) {
                String tag = element.tagName().toLowerCase();

                if(tag.equalsIgnoreCase("base")){
                    url = getAttributeValue(element, "href");
                } else if (tag.equalsIgnoreCase("script")) {
                    // Java script causes fatal errors if anything is wrong
                    // We therefore remove any javascript components
                    element.remove();
                } else if(tag.equalsIgnoreCase("blockquote") || tag.equalsIgnoreCase("q") || tag.equalsIgnoreCase("del") || tag.equalsIgnoreCase("ins")) {
                    handleResourceAttribute(element, url, "cite", null, false, true);
                } else if(tag.equalsIgnoreCase("a") || tag.equalsIgnoreCase("area")) {
                    handleResourceAttribute(element, url, "href", null, false, true);
                } else if(tag.equalsIgnoreCase("button") || tag.equalsIgnoreCase("input")) {
                    handleResourceAttribute(element, url, "formaction", null, false, true);
                } else if(tag.equalsIgnoreCase("form")) {
                    handleResourceAttribute(element, url, "action", null, false, true);
                } else if(tag.equalsIgnoreCase("audio") || tag.equalsIgnoreCase("track") || tag.equalsIgnoreCase("video") || tag.equalsIgnoreCase("embed")) {
                    handleResourceAttribute(element, url, "src", resourceDownloader, true, true);
                } else if(tag.equalsIgnoreCase("iframe")) {
                    String content = getAttributeValue(element, "srcdoc");
                    // srcdoc overrides src, so we only check src if there is no srcdoc attribute
                    if(content != null) {
                        handleExternalHtml(element, url, downloadResources, resourceDownloader);
                    }
                } else if(tag.equalsIgnoreCase("portal") || tag.equalsIgnoreCase("frame")) {
                    if(handleExternalHtml(element, url, downloadResources, resourceDownloader)) {
                        element.tagName("iframe");
                    }
                } else if(tag.equalsIgnoreCase("object")) {
                    String baseUrl = getAttributeValue(element, "codebase");
                    if(baseUrl == null){
                        baseUrl = url;
                    }

                    String archiveValue = getAttributeValue(element, "archive");
                    if(archiveValue != null){
                        String[] resources = archiveValue.trim().split(" ");
                        ArrayList<String> newResources = new ArrayList<>();
                        for(String resource : resources) {
                            URL absoluteUrl = UrlManager.getUrl(resource, baseUrl);
                            String newResource = resourceDownloader.downloadFile(absoluteUrl, null);
                            newResources.add(newResource);
                        }
                        String newValue = String.join(" ", newResources);
                        if(!newValue.equals(archiveValue)) {
                            element.attr("archive", newValue);
                        }
                    }

                    handleResourceAttribute(element, baseUrl, "data", resourceDownloader, true, false);
                } else if(tag.equalsIgnoreCase("img") || tag.equalsIgnoreCase("source")) {
                    handleResourceAttribute(element, url, "src", resourceDownloader, true, false);

                    handleSrcset(element, url, resourceDownloader, "srcset");
                } else if (tag.equalsIgnoreCase("link")) {
                    String relValue = getAttributeValue(element, "rel");
                    boolean isStylesheet = false;
                    if(relValue != null){
                        String[] parts = relValue.split(" ");
                        for(String part : parts){
                            if(part.equalsIgnoreCase("stylesheet")){
                                isStylesheet = true;
                            }
                        }
                    }
                    if(!isStylesheet) {
                        String asValue = getAttributeValue(element, "as");
                        if(asValue != null && asValue.equals("style")){
                            isStylesheet = true;
                        }
                    }

                    if (isStylesheet) {
                        handleCssResource(element, url, resourceDownloader);
                    } else{
                        handleResourceAttribute(element, url, "href", resourceDownloader, true, false);

                        handleSrcset(element, url, resourceDownloader, "imagesrcset");
                    }
                } else if (tag.equalsIgnoreCase("style")) {
                    handleCssContent(url, element, element.html(), resourceDownloader, null);
                }
            }
        }

        return doc;
    }

    /**
     * Checks whether the resource at the provided URL is a webpage
     * @param url The URL of the resource
     * @return true if it is a webpage; false otherwise
     */
    private static boolean checkIsWebpage(URL url){
        URLConnection connection;
        try {
            connection = url.openConnection();
            String contentType = connection.getContentType();
            if(contentType != null && contentType.contains("text/html")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Checks the resource paths of all resources in a resource set, downloads them and adjusts the resource path to the new resource name
     * @param element               The html element in which the resource set attribute was found
     * @param url                   The url of the base site
     * @param resourceDownloader    The HtmlManagement.ResourceDownloader
     * @param srcSetName            The name of the resource set attribute
     */
    private void handleSrcset(Element element, String url, ResourceDownloader resourceDownloader, String srcSetName){
        String srcSetValue = getAttributeValue(element, srcSetName);
        if(srcSetValue != null){
            String [] resources = srcSetValue.split(",");
            ArrayList<String> newResources = new ArrayList<>();
            for(String resource : resources){
                String[] resourceSpecifics = resource.trim().split(" ");
                URL absoluteUrl = UrlManager.getUrl(resourceSpecifics[0], url);
                String newResourceName = resourceDownloader.downloadFile(absoluteUrl, null);
                resourceSpecifics[0] = newResourceName;
                newResources.add(String.join(" ", resourceSpecifics));
            }
            String newValue = String.join(", ", newResources);
            if(!newValue.equals(srcSetValue)) {
                element.attr(srcSetName, newValue);
            }
        }
    }

    /**
     * Recursively downloads the content of a html page embedded in the current page.
     * Replaces the old element with an iframe element containing the downloaded content
     * @param element               The HTML element in which the html reference was found (iframe, frame or portal)
     * @param siteUrl               The URL of the base site
     * @param downloadResources     The already downloaded resources
     * @param resourceDownloader    The resource downloader
     * @return true if a reference to an external html page was found and the content downloaded; otherwise false
     */
    private boolean handleExternalHtml(Element element, String siteUrl, boolean downloadResources, ResourceDownloader resourceDownloader) {
        String src = getAttributeValue(element, "src");
        if (src != null) {
            String absoluteUrl = UrlManager.getUrl(src, siteUrl).toString();
            HtmlManager iframeHtmlManager = new HtmlManager();
            String iframeContent = HtmlManager.makeHtmlEmbeddable(iframeHtmlManager.prepareHtml(absoluteUrl, downloadResources, resourceDownloader)).toString().replaceAll("\\s+", " ");
            element.attr("srcdoc", iframeContent);
            element.removeAttr("src");

            return true;
        }
        return false;
    }

    /**
     * Downloads the content of a css file specified as resource and adds it as a style element instead
     * @param element               The HTML element in which the CSS resource file was specified
     * @param siteUrl               The URL of the base site
     * @param resourceDownloader    The HtmlManagement.ResourceDownloader
     */
    private void handleCssResource(Element element, String siteUrl, ResourceDownloader resourceDownloader) {
        String src = getAttributeValue(element, "href");
        if(src != null){
            URL url = UrlManager.getUrl(src, siteUrl);
            String content = resourceDownloader.downloadFileContent(url);

            if(content != null) {
                content = content.replaceAll("/\\*.*?\\*/", "");
                handleCssContent(siteUrl, element, content, resourceDownloader, url.toString());
            }
        }
    }

    /**
     * Handles URLs in CSS content and the styles with html and body selectors
     * @param siteUrl               The URL of the base site
     * @param element               The HTML <script> element in which the css is contained
     * @param cssContent            The css string to be processed
     * @param resourceDownloader    The resource downloader
     * @param parentUrl             The URL of the CSS file if the URL was extracted from a CSS resource file; otherwise null
     */
    private static void handleCssContent(String siteUrl, Element element, String cssContent,
                                         ResourceDownloader resourceDownloader, String parentUrl) {
        cssContent = CssManager.handleUrls(siteUrl, cssContent, resourceDownloader, parentUrl);
        cssContent = CssManager.replaceNonEmbeddableTagSelectors(cssContent);

        String replacement = "<style>" + cssContent + "</style>";
        Document d = Jsoup.parse(replacement);
        Element el = d.select("style").first();
        element.replaceWith(el);
    }

    /**
     * Extracts the value of an attribute
     * @param element       The HTML element with the specified attribute
     * @param attributeName The name of the attribute whose value needs to be extracted
     * @return              The value of the specified attribute if the element contains the attribute; otherwise null
     */
    private static String getAttributeValue(Element element, String attributeName){
        for (Attribute attribute : element.attributes()) {
            if (attribute.getKey().toLowerCase().equals(attributeName)) {
                return attribute.getValue();
            }
        }
        return null;
    }

    /**
     * Downloads a resource if the downloadResource flag is set.
     * Replaces the resource's path in the HTML element with the new name or with the absolute path if it wasn't downloaded
     * @param element               The HTML element in which the resource is specified
     * @param siteUrl               The URL of the base site
     * @param srcAttribute          The name of the attribute in which the resource is specified
     * @param resourceDownloader    The HtmlManagement.ResourceDownloader
     * @param downloadResource      Indicates whether to download the resource
     * @param downloadWebpages      <code>true</code> if the resource should be downloaded if it is a web page; otherwise <code>false</code>
     */
    private static void handleResourceAttribute(Element element, String siteUrl, String srcAttribute,
                                                ResourceDownloader resourceDownloader, boolean downloadResource,
                                                boolean downloadWebpages) {
        String src = getAttributeValue(element, srcAttribute);
        if (src != null) {
            URL absoluteUrl = UrlManager.getUrl(src, siteUrl);

            if(!downloadWebpages){
                downloadResource = !checkIsWebpage(absoluteUrl);
            }

            String outputName;
            if(downloadResource) {
                outputName = resourceDownloader.downloadFile(absoluteUrl, null);
            } else {
                outputName = absoluteUrl.toString();
            }

            if(!src.equals(outputName)) {
                element.attr(srcAttribute, outputName);
            }
        }
    }
}