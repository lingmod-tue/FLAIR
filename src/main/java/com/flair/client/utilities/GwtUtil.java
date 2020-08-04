package com.flair.client.utilities;

import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.HtmlBuilderFactory;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/*
 * Utility class for GWT-related tasks
 */
public class GwtUtil {
    private static int SMALL_SCREEN_WIDTH = 450;

    public static boolean isScrolledIntoView(Widget widget, boolean noClip) {
        if (widget != null) {
            int docViewTop = Window.getScrollTop();
            int docViewBottom = docViewTop + Window.getClientHeight();
            int elemTop = widget.getAbsoluteTop();
            int elemBottom = elemTop + widget.getOffsetHeight();

            if (noClip)
                return (elemBottom <= docViewBottom) && (elemTop >= docViewTop);
            else
                return ((elemTop >= docViewTop) && (elemTop <= docViewBottom) ||
                        (elemBottom >= docViewTop) && (elemBottom <= docViewBottom));
        }

        return false;
    }

    public static boolean isSmallScreen() {
        return Window.getClientWidth() < SMALL_SCREEN_WIDTH;
    }

    public static native boolean isTouch() /*-{
        return (('ontouchstart' in $wnd) || ($wnd.navigator.msMaxTouchPoints > 0));
    }-*/;

    public static String formatString(final String format, final Object... args) {
        final RegExp regex = RegExp.compile("%[a-z]");
        final SplitResult split = regex.split(format);
        final StringBuffer msg = new StringBuffer();
        for (int pos = 0; pos < split.length() - 1; ++pos) {
            msg.append(split.get(pos));
            msg.append(args[pos].toString());
        }
        msg.append(split.get(split.length() - 1));
        return msg.toString();
    }

    /**
     * Copies the given html to the clipboard.
     *
     * @param html  The html content that should be copied to the clipboard.
     * @param ghostTitle The title of document. This is only necessary for IE and SAFARI browsers, when a "ghost" div element has to be created.
     * @param ghostID    The ID of the document. This is only necessary for IE and SAFARI browsers, when a "ghost" div element has to be created..
     * @return
     */
    public static boolean copyElementToClipboard(String html, String ghostTitle, String ghostID) {

        boolean success = false;
        // Depending on the browser, copy HTML directly to the clipboard might not be supported.
        switch (ClientInfo.CURRENT_BROWSER) {
            // So far, IE and Safari do not support the feature to directly copy HTML to the clipboard.
            case IE:
            case SAFARI: {

                // Create a ghost element, append it to the page, select and copy it, and then delete it.
                Element ghostElem = createDiv(ghostTitle, ghostID, html);

                // Append the div.
                Document.get().getBody().appendChild(ghostElem);
                // Copy to clipboard.
                success = JSUtility.selectAndCopyElementToClipboard(ghostElem.getId(), true);
                // Remove the div.
                Document.get().getBody().removeChild(ghostElem);
                break;
            }
            default: {
                success = JSUtility.copyHTMLToClipboard(html);
            }
        }
        return success;
    }

    /**
     * Creates a div with the given title, id, and innerHTML.
     *
     * @param title     The title of the div.
     * @param id        The id of the div.
     * @param innerHTML The innerHTML of the div.
     * @return The div element.
     */
    protected static Element createDiv(String title, String id, String innerHTML) {
        DivBuilder divBuilder = HtmlBuilderFactory.get().createDivBuilder();
        divBuilder.title(title);
        divBuilder.id(id);

        Element elem = divBuilder.finish();
        elem.setInnerHTML(innerHTML);
        return elem;
    }
}
