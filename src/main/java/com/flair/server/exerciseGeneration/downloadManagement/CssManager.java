package com.flair.server.exerciseGeneration.downloadManagement;

import com.steadystate.css.parser.CSSOMParser;

import edu.stanford.nlp.util.Pair;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CssManager {

    /**
     * Finds url() components in CSS strings.
     * Downloads the resource if possible and sets the url to the name of the downloaded file;
     * otherwise, the URL is set to an absolute URL
     * @param siteUrl The URL of the base site
     * @param cssContent The CSS string
     * @param resourceDownloader The resource downloader
     * @param parentUrl The URL of the css file from which the URL was extracted
     * @return The new CSS string with replaced URLs
     */
    public static String handleUrls(String siteUrl, String cssContent, ResourceDownloader resourceDownloader, String parentUrl) {
        Matcher m = Pattern.compile("(?<=url\\()(.*?)(?=\\))").matcher(cssContent);
        while (m.find()) {
            String url = m.group();
            int dotIndex = url.lastIndexOf(".");
            if(dotIndex != -1) {
                URL absoluteUrl = UrlManager.getUrl(url, siteUrl);
                URL absoluteParentUrl = null;
                if(parentUrl != null){
                    absoluteParentUrl = UrlManager.getUrl(url, parentUrl);
                }
                if(absoluteUrl != null) {
                    String outputName = resourceDownloader.downloadFile(absoluteUrl, absoluteParentUrl);
                    cssContent = cssContent.replace("url(" + url + ")", "url(" + outputName + ")");
                }
            }
        }

        return cssContent;
    }

    /**
     * Parses a CSS string and replaces the html and body selectors
     * with class selectors of the classes added to the elements with which html and body are replaced
     * @param cssContent The CSS string
     * @return The modified CSS string
     */
    public static String replaceNonEmbeddableTagSelectors(String cssContent) {    	
        CSSOMParser parser = PARSER.get();

        // Overwrite the default error handler to suppress console output
        parser.setErrorHandler(new CssParseErrorHandler());

        CSSStyleSheet stylesheet = null;
        try(InputStream is = new ByteArrayInputStream(cssContent.getBytes())) {
            InputSource source = new InputSource(new InputStreamReader(is));
            stylesheet = parser.parseStyleSheet(source, null, null);
        } catch (IOException | EmptyStackException e) {
            e.printStackTrace();
        }

        if(stylesheet != null) {
            ArrayList<Pair<String, String>> replacements = new ArrayList<>();
            CSSRuleList ruleList = stylesheet.getCssRules();

            for (int i = 0; i < ruleList.getLength(); i++) {
                CSSRule rule = ruleList.item(i);
                if (rule instanceof CSSStyleRule) {
                    CSSStyleRule styleRule = (CSSStyleRule) rule;
                    String oldRule = styleRule.toString();
                    String selectorText = styleRule.getSelectorText();
                    selectorText = selectorText.replaceAll("(?<!.)(body)(?!.)", ".bodyreplacement");
                    selectorText = selectorText.replaceAll("(?<!.)(body)(?<=.)", ".bodyreplacement");
                    selectorText = selectorText.replaceAll("(?<!.)(html)(?!.)", ".htmlreplacement");
                    selectorText = selectorText.replaceAll("(?<!.)(html)(?<=.)", ".htmlreplacement");

                    if (!styleRule.getSelectorText().equals(selectorText)) {
                        styleRule.setSelectorText(selectorText);
                        replacements.add(new Pair<>(oldRule, styleRule.toString()));
                    }
                }
            }

            // We might not find all the occurrences if the formatting isn't nice, but otherwise we lose those rules which the parser couldn't parse
            for(Pair<String, String> replacement : replacements) {
                cssContent = cssContent.replace(replacement.first, replacement.second);
            }
        }

        return cssContent;
    }
    
    /**
     * Get thread-safe parser instance.
     */
    private static final ThreadLocal<CSSOMParser> PARSER  = ThreadLocal.withInitial(new Supplier<CSSOMParser>() {
	    @Override
	    public CSSOMParser get() {
	        return new CSSOMParser();
	    }
	});
}