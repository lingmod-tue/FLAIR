package com.flair.server.exerciseGeneration.downloadManagement;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.flair.server.utilities.ServerLogger;

import edu.stanford.nlp.util.Pair;

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
     * @param url                   The url of the website
     * @param resourceDownloader    The Resource downloader
     * @return                      The html document and the downloaded resources
     */
    public Pair<Element, ArrayList<DownloadedResource>> getHtml(String url, ResourceDownloader resourceDownloader, Element plainText) {
    	if(url.length() == 0) {
    		return new Pair<>(plainText, new ArrayList<DownloadedResource>());
    	} else {
	    	Pair<Element, ArrayList<DownloadedResource>> res = prepareHtml(url, resourceDownloader);
	    	return new Pair<>(res.first, res.second);
    	}
    }

    /**
     * Downloads the html document, makes resource urls (including in style tags) absolute and removes script tags
     * @param url                   The url of the website
     * @param resourceDownloader    The Resource downloader
     * @return                      The html document and the downloaded resources
     */
    public Pair<Element, ArrayList<DownloadedResource>> prepareHtml(String url, ResourceDownloader resourceDownloader) {
        WebBasicHtmlDownloader downloader = new WebBasicHtmlDownloader();
        Document doc = downloader.download(url);
        
        ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();

        if(doc != null) {
        	for(Element element : doc.select("base")) {
        		url = getAttributeValue(element, "href");
        	}
        	for(Element element : doc.select("script")) {
        		// Java script causes fatal errors if anything is wrong
                // We therefore remove any javascript components
                element.remove();
        	}        	
        	// we have to do this before the link elements, otherwise we treat replaced stylesheets twice!
        	for(Element element : doc.select("style")) {
        		downloadedResources.addAll(handleCssContent(url, element, element.html(), resourceDownloader, null));
        	}
        	for(Element element : doc.select("link")) {
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
                	downloadedResources.addAll(handleCssResource(element, url, resourceDownloader));
                } 
            }
        	for(Element element : doc.select("iframe")) {
        		String content = getAttributeValue(element, "srcdoc");
                // srcdoc overrides src, so we only check src if there is no srcdoc attribute
                if(content != null) {
                    ArrayList<DownloadedResource> resources = handleExternalHtml(element, url, resourceDownloader);
                    if(resources != null) {
                    	downloadedResources.addAll(resources);
                    }
                }        	
            }
        	for(Element element : doc.select("portal,frame")) {
        		ArrayList<DownloadedResource> resources = handleExternalHtml(element, url, resourceDownloader);
        		if(resources != null) {
                    element.tagName("iframe");
                    downloadedResources.addAll(resources);
                }     	
            }
        }

        return new Pair<>(doc, downloadedResources);
    }
    
    /**
     * Determines the relevant resource attribute of a HTML tag.
     * @param tag	The HTML tag
     * @return		The name of the resource attribute
     */
    private String getAttributeName(String tag) {
    	if(tag.equals("blockquote") || tag.equals("q") || tag.equals("del") || tag.equals("ins")) {
    		return "cite";
    	}
    	if(tag.equals("a") || tag.equals("area")) {
    		return "href";
    	}
    	if(tag.equals("button") || tag.equals("input")) {
    		return "formaction";
    	}
    	if(tag.equals("form")) {
    		return "action";
    	}
    	return null;
    }
    
    public ArrayList<DownloadedResource> extractResources(String url, ResourceDownloader resourceDownloader, Element doc) {
    	ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();
    	
    	// Don't try to download anything for corpus upload files
    	if(url.length() == 0) {
    		return downloadedResources;
    	}
    	
        if(doc != null) {
        	for(Element element : doc.select("base")) {
        		if (Thread.currentThread().isInterrupted()) {
    	        	return null;
    	        }
        		
        		url = getAttributeValue(element, "href");
        	}
        	
        	for (Element element : doc.getAllElements()) {
        		if (Thread.currentThread().isInterrupted()) {
    	        	return null;
    	        }
                String tag = element.tagName().toLowerCase();

                if(tag.equalsIgnoreCase("blockquote") || tag.equalsIgnoreCase("q") || tag.equalsIgnoreCase("del") || 
                		tag.equalsIgnoreCase("ins") || tag.equalsIgnoreCase("a") || tag.equalsIgnoreCase("area") || 
                		tag.equalsIgnoreCase("button") || tag.equalsIgnoreCase("input") || tag.equalsIgnoreCase("form")){
                	DownloadedResource resource = handleResourceAttribute(element, url, getAttributeName(tag), resourceDownloader, true);
            		if(resource != null) {
            			downloadedResources.add(resource);
            		}
                } else if(tag.equalsIgnoreCase("audio") || tag.equalsIgnoreCase("track") || tag.equalsIgnoreCase("video") || 
                		tag.equalsIgnoreCase("embed")){
                	DownloadedResource resource = handleResourceAttribute(element, url, "src", resourceDownloader, true);
            		if(resource != null) {
            			downloadedResources.add(resource);
            		}
                } else if(tag.equalsIgnoreCase("object")){
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
                            DownloadedResource downloadedResource = resourceDownloader.downloadFile(absoluteUrl, null);
                            downloadedResources.add(downloadedResource);
                            newResources.add(downloadedResource.getFileName());
                        }
                        String newValue = String.join(" ", newResources);
                        if(!newValue.equals(archiveValue)) {
                            element.attr("archive", newValue);
                        }
                    }

                    DownloadedResource resource = handleResourceAttribute(element, baseUrl, "data", resourceDownloader, false);
            		if(resource != null) {
            			downloadedResources.add(resource);
            		}
                } else if(tag.equalsIgnoreCase("img") || tag.equalsIgnoreCase("source")){
                	DownloadedResource resource = handleResourceAttribute(element, url, "src", resourceDownloader, false);
            		if(resource != null) {
            			downloadedResources.add(resource);
            		}
                    downloadedResources.addAll(handleSrcset(element, url, resourceDownloader, "srcset"));
                } else if(tag.equalsIgnoreCase("link")){
                	DownloadedResource resource = handleResourceAttribute(element, url, "href", resourceDownloader, false);
            		if(resource != null) {
            			downloadedResources.add(resource);
            		}
            		downloadedResources.addAll(handleSrcset(element, url, resourceDownloader, "imagesrcset"));
                } else if(tag.equalsIgnoreCase("iframe")){
                	String content = getAttributeValue(element, "srcdoc");
                    if(content != null) {
                    	try {
    	                	downloadedResources.addAll(extractResources(getAttributeValue(element, "src"), resourceDownloader, Jsoup.parse(content)));
                    	}
                    	catch(Exception e) {}
                    }
                } 
        	}
        }   
        
        return downloadedResources;
    }
    
    /**
     * Removes text elements which were removed along with any enclosing elements which don't contain a not removed text element.
     */
    public static void removeNotDisplayedElements(Element doc) {
    	Elements elements = doc.select("span[data-remove]");
    	while(elements.size() > 0) {
    		Element element = elements.get(0);
    		Element parent = element.parent();
    		Element currentElementToRemove = element;
    		while(parent != null) {
    			String parentText = parent.outerHtml();
    			if(!parentText.contains("<span data-plaintextplaceholder>")) {
    				currentElementToRemove = parent;
    				parent = parent.parent();
    			} else {
    				parent = null;
    			}
    		}
    		if(currentElementToRemove.parent() != null) {
    			currentElementToRemove.remove();
    		}
    		elements = doc.select("span[data-remove]");
    	}    	
    }
    
    /**
     * Removes any elements before and after the FLAIR plain text.
     * @param pair The first and last element of the plain text
     */
    public static void removeNonText(com.flair.shared.exerciseGeneration.Pair<Node,Node> pair) {
        removeSiblings(pair.first, true);
        removeSiblings(pair.second, false);
    }

    /**
     * Removes any previous or succeeding siblings of the boundary node, dependingon the isStart flag.
     * @param boundaryNode	The start or end node representing the first or last element contained in the plain text
     * @param isStart <c>true</c> if previous elements need to be deleted; <c>false</c> if succeeding elements are deleted
     */
    private static void removeSiblings(Node boundaryNode, boolean isStart) {
        if(boundaryNode != null) {
            // Get the node's ancestors
            ArrayList<Node> ancestors = new ArrayList<>();
            Node parentNode = boundaryNode.parent();
            while (parentNode != null) {
                ancestors.add(parentNode);
                parentNode = parentNode.parent();
            }

            // Check for relevant siblings in boundary node and ancestors
            Node parent = boundaryNode;
            while (parent != null) {
                // Remove relevant siblings
                Node sibling = isStart ? parent.previousSibling() : parent.nextSibling();
                while (sibling != null) {
                    // Check if sibling's parent can also be removed
                    Node siblingParent = sibling.parent();
                    while (siblingParent != null && !ancestors.contains(siblingParent)) {
                        sibling = siblingParent;
                        siblingParent = siblingParent.parent();
                    }

                    Node nextRelevantSibling = isStart ? sibling.previousSibling() : sibling.nextSibling();
                    sibling.remove();
                    sibling = nextRelevantSibling;
                }
                parent = parent.parent();
            }
        }
    }
    
    /**
     * Replace anchor elements with spans.
     * Needed for Mark the Words exercises where words cannot be links.
     * @param doc	The HTML document
     */
    public static void removeLinks(Element doc) {
    	for(Element link : doc.select("a")) {
    		String content = link.html();
			if(content.contains("<span data-plaintextplaceholder>")) {
				link.tagName("span");
			}
    	}
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
			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
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
    private ArrayList<DownloadedResource> handleSrcset(Element element, String url, ResourceDownloader resourceDownloader, String srcSetName){
        String srcSetValue = getAttributeValue(element, srcSetName);
        ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();
        if(srcSetValue != null){
            String [] resources = srcSetValue.split(",");
            ArrayList<String> newResources = new ArrayList<>();
            for(String resource : resources){
                String[] resourceSpecifics = resource.trim().split(" ");
                URL absoluteUrl = UrlManager.getUrl(resourceSpecifics[0], url);
                DownloadedResource downloadedResource = resourceDownloader.downloadFile(absoluteUrl, null);
                resourceSpecifics[0] = downloadedResource.getFileName();
                downloadedResources.add(downloadedResource);
                newResources.add(String.join(" ", resourceSpecifics));
            }
            String newValue = String.join(", ", newResources);
            if(!newValue.equals(srcSetValue)) {
                element.attr(srcSetName, newValue);
            }
        }
        
        return downloadedResources;
    }

    /**
     * Recursively downloads the content of a html page embedded in the current page.
     * Replaces the old element with an iframe element containing the downloaded content
     * @param element               The HTML element in which the html reference was found (iframe, frame or portal)
     * @param siteUrl               The URL of the base site
     * @param resourceDownloader    The resource downloader
     * @return true if a reference to an external html page was found and the content downloaded; otherwise false
     */
    private ArrayList<DownloadedResource> handleExternalHtml(Element element, String siteUrl, ResourceDownloader resourceDownloader) {
        String src = getAttributeValue(element, "src");
        if (src != null) {
            String absoluteUrl = UrlManager.getUrl(src, siteUrl).toString();
            HtmlManager iframeHtmlManager = new HtmlManager();
            Pair<Element,ArrayList<DownloadedResource>> res = iframeHtmlManager.prepareHtml(absoluteUrl, resourceDownloader);
            String iframeContent = res.first.toString().replaceAll("\\s", " ");
            element.attr("srcdoc", iframeContent.replace("\"", "'"));
            element.attr("src", absoluteUrl);

            return res.second;
        }
        return null;
    }

    /**
     * Downloads the content of a css file specified as resource and adds it as a style element instead
     * @param element               The HTML element in which the CSS resource file was specified
     * @param siteUrl               The URL of the base site
     * @param resourceDownloader    The HtmlManagement.ResourceDownloader
     * @return 
     */
    private ArrayList<DownloadedResource> handleCssResource(Element element, String siteUrl, ResourceDownloader resourceDownloader) {
        String src = getAttributeValue(element, "href");
        if(src != null){
            URL url = UrlManager.getUrl(src, siteUrl);
            String content = ResourceDownloader.downloadFileContent(url);

            if(content != null) {
                content = content.replaceAll("/\\*.*?\\*/", "");
                return handleCssContent(siteUrl, element, content, resourceDownloader, url.toString());
            }
        }
        
        return new ArrayList<>();
    }

    /**
     * Handles URLs in CSS content and the styles with html and body selectors
     * @param siteUrl               The URL of the base site
     * @param element               The HTML <script> element in which the css is contained
     * @param cssContent            The css string to be processed
     * @param resourceDownloader    The resource downloader
     * @param parentUrl             The URL of the CSS file if the URL was extracted from a CSS resource file; otherwise null
     */
    private static ArrayList<DownloadedResource> handleCssContent(String siteUrl, Element element, String cssContent,
                                         ResourceDownloader resourceDownloader, String parentUrl) {
    	CssManager cssManager = new CssManager();
    	Pair<String, ArrayList<DownloadedResource>> res = cssManager.handleUrls(siteUrl, cssContent, resourceDownloader, parentUrl);
        cssContent = res.first;
        cssContent = cssManager.replaceNonEmbeddableTagSelectors(cssContent);

        String replacement = "<style>" + cssContent + "</style>";
        Document d = Jsoup.parse(replacement);
        Element el = d.select("style").first();
        element.replaceWith(el);
        
        
        return res.second;
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
    private DownloadedResource handleResourceAttribute(Element element, String siteUrl, String srcAttribute,
                                                ResourceDownloader resourceDownloader,
                                                boolean downloadWebpages) {
        String src = getAttributeValue(element, srcAttribute);
        
        DownloadedResource downloadedResource = null;
        if (src != null) {
            boolean downloadResource = src.contains(".");
            URL absoluteUrl = UrlManager.getUrl(src, siteUrl);

            if(!downloadWebpages){
                downloadResource = !checkIsWebpage(absoluteUrl);
            }

            String outputName;
            if(downloadResource) {
            	downloadedResource = resourceDownloader.downloadFile(absoluteUrl, null);
                outputName = downloadedResource.getFileName();
            } else {
                outputName = absoluteUrl.toString();
            }

            if(!src.equals(outputName)) {
                element.attr(srcAttribute, outputName);
            }
        }
        
        return downloadedResource;
    }
}