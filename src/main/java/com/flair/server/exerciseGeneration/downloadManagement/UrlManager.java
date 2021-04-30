package com.flair.server.exerciseGeneration.downloadManagement;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import edu.stanford.nlp.util.Pair;

public class UrlManager {

    /**
     * Puts together an absolute URL for a relative URL and the URL of the base site
     * @param src       The relative URL
     * @param siteUrl   The URL of the base site
     * @return          The absolute URL for the source
     */
    public static URL getUrl(String src, String siteUrl) {
        URL url = null;

        src = removeEnclosingQuotes(src);

        try {
            boolean includePath = false;

            if(new URL(siteUrl).getPath().contains(".")){
                Pair<String, String> urls = adjustUrlsForSiteFiles(siteUrl, src);
                siteUrl = urls.first;
                src = urls.second;

                includePath = true;
            }

            UrlComponents urlComponents = new UrlComponents();

            if (!(src.startsWith("http"))) {
                if(src.startsWith("//")){
                    handleDoubleSlashedPaths(urlComponents, src, new URL(siteUrl));
                } else {
                    handleRelativePaths(urlComponents, new URL(siteUrl), src, includePath);
                }

                src = urlComponents.getProtocol() + "://" + urlComponents.getHost() + urlComponents.getPath();
            } else if (src.startsWith("file://") || src.startsWith("data://")) {
                //TODO: find out how we need to deal with this kind of path
                //src = src.replace("file://", siteUrl);
                System.out.println(src);
            }

            url = getValidUrlFromString(src);
        } catch (MalformedURLException | URISyntaxException e) {
            System.out.println("The detected resource path is not a valid url.");
        }

        return url;
    }

    /**
     * Handles cases where the siteUrl is a file.
     * Removes the file name and treat backward navigation in the resource path.
     * @param siteUrl   The url of the base site
     * @param src       The url of the resource
     * @return          A tuple of the modified siteUrl and src
     */
    private static Pair<String, String> adjustUrlsForSiteFiles(String siteUrl, String src){
        // Remove file name
        siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));

        // Remove directories if src navigates back
        while(src.startsWith("..")){
            siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
            src = src.substring(src.indexOf("/") + 1);
        }

        return new Pair<>(siteUrl, src);
    }

    /**
     * Removes leading dots in the resource path if any remain.
     * These would otherwise result in an invalid url.
     * @param src   The resource url
     * @return      The resource url without leading dots
     */
    private static String removeLeadingDots(String src){
        while(src.startsWith(".")){
            src = src.substring(src.indexOf("/") + 1);
        }

        return src;
    }

    /**
     * Generates a valid URL from a string URL.
     * Escapes e.g. spaces.
     * @param src                       The resource URL as string
     * @return                          The url of the string url
     * @throws MalformedURLException    Exception if the URL does not have a valid URL format
     * @throws URISyntaxException       Exception if the URL is not a valid URI
     */
    private static URL getValidUrlFromString(String src) throws MalformedURLException, URISyntaxException {
        URL url = new URL(src);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        return uri.toURL();
    }

    /**
     * Gets the url components for resources specified with leading //.
     * @param urlComponents The object bundling the url components
     * @param src           The resource url string
     * @param siteUrl       The base site url string
     */
    private static void handleDoubleSlashedPaths(UrlComponents urlComponents, String src, URL siteUrl){
        urlComponents.setProtocol(siteUrl.getProtocol());
        urlComponents.setHost(src.substring(2));
    }

    /**
     * Gets the url components for resources specified with http(s).
     * @param urlComponents The object bundling the url components
     * @param siteUrl       The base site url string
     * @param src           The resource url string
     * @param includePath   Specifies whether to include the path of the base site (only if the base site was a file)
     */
    private static void handleRelativePaths(UrlComponents urlComponents, URL siteUrl, String src, boolean includePath){
        urlComponents.setProtocol(siteUrl.getProtocol());
        urlComponents.setHost(siteUrl.getHost());
        if(includePath){
            String path = siteUrl.getPath();
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            urlComponents.setPath(path);
        }

        src = removeLeadingDots(src);
        if(!src.startsWith("/")){
            src = "/" + src;
        }

        urlComponents.setPath(urlComponents.getPath() + src);
    }

    /**
     * Removes quotation marks in which a resource url string might be embedded.
     * @param src   The url string of the resource
     * @return      The url string without leading and trailing quotation marks
     */
    private static String removeEnclosingQuotes(String src){
        if(src.startsWith("'") || src.startsWith("\"")){
            src = src.substring(1);
        }
        if(src.endsWith("'") || src.endsWith("\"")){
            src = src.substring(0, src.length() - 1);
        }

        return src;
    }
}