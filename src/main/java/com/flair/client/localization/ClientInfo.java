package com.flair.client.localization;

import com.google.gwt.user.client.Window;

/**
 * Information about the client (browser).
 */
public class ClientInfo {

    public static final Browser CURRENT_BROWSER = getBrowser();

    public static Browser getBrowser() {

        return (Browser.valueOf(getBrowserNative().toUpperCase()));
    }

    protected static native boolean isChrome()/*-{

        var isChromium = window.chrome;
        var winNav = window.navigator;
        var vendorName = winNav.vendor;
        var isOpera = typeof window.opr !== "undefined";
        var isIEedge = winNav.userAgent.indexOf("Edge") > -1;
        var isIOSChrome = winNav.userAgent.match("CriOS");

        return (isIOSChrome | (isChromium !== null &&
            typeof isChromium !== "undefined" &&
            vendorName === "Google Inc." &&
            isOpera === false &&
            isIEedge === false))
    }-*/;


    protected static native String getBrowserNative()/*-{
        // please note,
// that IE11 now returns undefined again for window.chrome
// and new Opera 30 outputs true for window.chrome
// but needs to check if window.opr is not undefined
// and new IE Edge outputs to true now for window.chrome
// and if not iOS Chrome check
// so use the below updated condition
        var isChromium = window.chrome;
        var winNav = window.navigator;
        var vendorName = winNav.vendor;
        var isOpera = typeof window.opr !== "undefined";
        var isIEedge = winNav.userAgent.indexOf("Edge") > -1;
        var isIOSChrome = winNav.userAgent.match("CriOS");
        var isFireFox = winNav.userAgent.indexOf("Firefox") > -1;
        var isIE = winNav.userAgent.indexOf('rv:') > -1 | winNav.userAgent.indexOf("MSIE") > -1;
        var isSafari = winNav.userAgent.indexOf("Safari") > -1 && winNav.userAgent.indexOf("chrome") == -1;
        if (isIOSChrome || (
            isChromium !== null &&
            typeof isChromium !== "undefined" &&
            vendorName === "Google Inc." &&
            !isOpera  &&
            !isIEedge  && !isSafari)) {
            return "chrome";
        } else if (isIEedge) {
            return "edge";
        } else if (isOpera) {
            return "opera";
        } else if (isFireFox) {
            return "firefox";
        } else if (isIE) {
            return "ie";
        } else if (isSafari) {
            return "safari";
        } else {
            return "unknown";
        }

    }-*/;

    public enum Browser {
        UNKNOWN("unknown"),
        CHROME("chrome"),
        FIREFOX("firefox"),
        IE("ie"),
        EDGE("edge"),
        SAFARI("safari"),
        OPERA("opera"),
        COUNT("count");

        public final String name;

        Browser(String name) {
            this.name = name;
        }
    }
}
