package com.flair.client.utilities;

import com.google.gwt.dom.client.Element;

public class JSUtility {

    /**
     * Exports the HTML/RTF of the given element to a word document.
     *
     * @param elem     The element whose HTML will be exported.
     * @param filename The filename.
     * @param appendPrePostHtml Whether to append pre and post html tags and definitions (<html> and </html>).
     */
    public static native boolean exportToWord(Element elem, String filename, boolean appendPrePostHtml)/*-{
        return @com.flair.client.utilities.JSUtility::exportToWord(Ljava/lang/String;Ljava/lang/String;Z)(elem.innerHTML, filename, appendPrePostHtml);

    }-*/;

    /**
     * Exports the given HTML/RTF to a word document.
     *
     * @param html     The HTML to export.
     * @param filename The filename.
     */
    public static native boolean exportToWord(String html, String filename, boolean appendPrePostHtml)/*-{

        // TODO: title setzen
        var preHtml = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns='http://www.w3.org/TR/REC-html40'><head><meta charset='utf-8'><title>Export HTML To Doc</title></head><body>";
        var postHtml = "</body></html>";
        var doc = appendPrePostHtml? preHtml+html+postHtml : html;
        var blob = new Blob(['\ufeff', doc], {
            type: 'application/msword'
        });

        // Specify link url
        var url = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(doc);
        // octet-stream as MIME type forces a "save as" dialog in FF.

        // Specify file name
        filename = filename ? filename + '.doc' : 'document.doc';

        // Create download link element
        var downloadLink = $doc.createElement("a");
        downloadLink.id = "downloadLink_" + filename;

        $doc.body.appendChild(downloadLink);

        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveOrOpenBlob(blob, filename);
        } else {
            // Create a link to the file
            downloadLink.href = url;

            // Setting the file name
            downloadLink.download = filename;

            //triggering the function
            downloadLink.click();
            // $doc.execCommand('SaveAs',encodeURIComponent(doc),filename)
        }

        $doc.body.removeChild(downloadLink);

        return true;

    }-*/;

    /**
     * Copies the given HTML / RTF to the clipboard.
     * NOTE: This works for Chrome, Firefox, and Edge. It does NOT work with Safari or Internet Explorer.
     *
     * @param str The HTML/RTF to copy to clipboard.
     */
    public static native boolean copyHTMLToClipboard(String str)/*-{

        // Make sure that the formatted text is copied.
        // This works for Chrome, FF, Edge.
        // TODO: does not work with IE.
        // TODO: does not work with Safari.
        function listener(e) {
            e.clipboardData.setData("text/html", str);
            e.clipboardData.setData("text/plain", str);
            e.preventDefault();
        }

        $doc.addEventListener("copy", listener);
        var success = $doc.execCommand("copy");
        $doc.removeEventListener("copy", listener);
        return success;

    }-*/;

    /**
     * Selects the text of the element with the given ID.
     *
     * @param id ID of the element to select.
     */
    public static native void selectElementById(String id)/*-{
        var elem = $doc.getElementById(id);
        @com.flair.client.utilities.JSUtility::selectElement(Lcom/google/gwt/dom/client/Element;)(elem);
    }-*/;

    /**
     * Selects the text of the element with the given ID.
     *
     * @param elem The HTML element.
     */
    public static native void selectElement(Element elem)/*-{
        if ($doc.selection) {
            var div = $doc.body.createTextRange();
            div.moveToElementText(elem);
            div.select();
        } else {
            var range = $doc.createRange();
            range.selectNode(elem);

            // div.setStartBefore($doc.getElementById(id));
            // div.setEndAfter($doc.getElementById(id)) ;
            var selection = $wnd.getSelection();
            selection.removeAllRanges();
            selection.addRange(range);
        }

    }-*/;


    public static native void getCurrentSelectionRange()/*-{
        return window.getSelection().getRangeAt(0);
    }-*/;


    /**
     * Selects the given element and copies its content to the clipboard.
     *
     * @param id            The id of the element to select and copy to clipboard.
     * @param undoSelection Whether the selection should be reset.
     * @return Whether the element was successfully copied to clipboard.
     */
    public static native boolean selectAndCopyElementToClipboard(String id, boolean undoSelection)/*-{

        return @com.flair.client.utilities.JSUtility::selectAndCopyElementToClipboard(Lcom/google/gwt/dom/client/Element;Z)($doc.getElementById(id), undoSelection);

    }-*/;

    /**
     * Selects the given element and copies its content to the clipboard.
     *
     * @param elem          The HTML element.
     * @param undoSelection Whether the selection should be reset.
     * @return Whether the element was successfully copied to clipboard.
     */
    public static native boolean selectAndCopyElementToClipboard(Element elem, boolean undoSelection)/*-{

        // Get the previously selected range.
        var previousSelection = $wnd.getSelection && $wnd.getSelection();
        var previousSelectionRange = null;
        if (previousSelection && previousSelection.rangeCount > 0) {
            previousSelectionRange = previousSelection.getRangeAt(0);
        }
        // Select the element.
        @com.flair.client.utilities.JSUtility::selectElement(*)(elem);

        // Copy selection to clipboard.
        var success = @com.flair.client.utilities.JSUtility::copyToClipboard()();

        // Undo the selection if requested.
        if (undoSelection) {
            $wnd.getSelection().removeAllRanges();
            if (previousSelectionRange) {
                $wnd.getSelection().addRange(previousSelectionRange);
            }
        }

        return success;

    }-*/;


    /**
     * Copies the text of an InputElement to the clipboard.
     * IMPORTANT: only works for input elements!
     *
     * @param inputElement The input element.
     * @return Whether copying the text to the clipboard was successful.
     */
    public static native boolean copyTextToClip(Element inputElement)/*-{
        inputElement.focus();
        inputElement.select();
        var success = $doc.execCommand('copy');
        return success;
    }-*/;

    /**
     * Selects and copies the text of the element of the given id to the clipboard.
     * NOTE that only plaintext is copied.
     *
     * @param id The id of the element to copy the text.
     * @return Whether copying was successful.
     */
    public static native boolean selectAndCopyPlaintextToClipboardById(String id)/*-{
        var txtElem = $doc.getElementById(id);
        return @com.flair.client.utilities.JSUtility::selectAndCopyPlaintextToClipboard(*)(txtElem.textContent);
    }-*/;


    /**
     * Copies the plaintext to the clipboard using a ghost input element.
     * @param txt The text to copy to the clipboard.
     * @return Whether the text was successfully copied to the clipboard.
     */
    public static native boolean selectAndCopyPlaintextToClipboard(String txt)/*-{
        // create "ghost" input element that can be selected.
        var ghostInput = $doc.createElement("input");
        ghostInput.id = "ghostInput";
        $doc.body.appendChild(ghostInput);
        ghostInput.value = txt;
        ghostInput.select();
        var success = $doc.execCommand('copy');
        // ghostInput.remove();
        return success;
    }-*/;

    public static native boolean copyToClipboard() /*-{
        return $doc.execCommand('copy');
    }-*/;


}
