package com.flair.client.presentation.widgets;

import com.flair.client.localization.*;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.localization.resources.LocalizedResources;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput;
import com.flair.client.utilities.ClientInfo;
import com.flair.client.utilities.GwtUtil;
import com.flair.client.utilities.JSUtility;
import com.flair.client.utilities.StringUtil;
import com.flair.shared.interop.dtos.RankableDocument;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.HtmlBuilderFactory;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.helper.ColorHelper;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.*;

import java.util.List;

public class ExportDocumentWidget extends LocalizedComposite {

    static enum LocalizationTags {
        TEXT_COPY_TO_CLIPBOARD_SUCCESSFUL,
        // TODO: Implementieren
        TEXT_COPY_TO_CLIPBOARD_FAILED,
        TEXT_EXPORT_TO_WORD_SUCCESSFUL,
        // TODO: Implementieren
        TEXT_EXPORT_TO_WORD_FAILED,
        TEXT_COPY_TEXT_FORMATTING,
        TEXT_COPY_TO_CLIPBOARD_COPYRIGHT_NOTICE,
        TEXT_EXPORT_COPYRIGHT_NOTICE,
    }

    enum WordDocumentTag {
        UNKNOWN("unknown"),
        TITLE("title"),
        LBL_LINK("lblLink"),
        LINK("link"),
        LABEL_READABILITY_LEVEL("lblReadabilityLevel"),
        LABEL_NUMBER_OF_SENTENCES("lblNumberOfSentences"),
        LABEL_NUMBER_OF_WORDS("lblNumberOfWords"),
        READABILITY_LEVEL("readabilityLevel"),
        NUMBER_OF_SENTENCES("numberOfSentences"),
        NUMBER_OF_WORDS("numberOfWords"),
        TEXT("text"),
        GRAMMATICAL_CONSTRUCTIONS_TABLE("tableGrammaticalConstructions"),
        CONSTRUCTION_DATA("constructionData"),
        FOOTER("footer"),
        LABEL_CONSTRUCTION("lblConstruction"),
        LABEL_SENTENCES("lblSentences"),
        LABEL_WORDS("lblWords"),
        LABEL_HITS("lblHits"),
        LABEL_WEIGHT("lblWeight"),
        LABEL_LINK_TO_TEXT("lblLink"),
        COUNT("count");

        public final String tag;

        WordDocumentTag(String tag) {
            this.tag = tag;
        }

        public String getHTMLTag() {
            return "{" + tag + "}";
        }
    }


    interface ExportDocumentWidgetUiBinder extends UiBinder<Widget, ExportDocumentWidget> {
    }

    private static ExportDocumentWidgetUiBinder ourUiBinder = GWT.create(ExportDocumentWidgetUiBinder.class);


    private static ExportDocumentWidget.ExportDocumentWidgetLocalizationBinder localeBinder = GWT.create(ExportDocumentWidget.ExportDocumentWidgetLocalizationBinder.class);

    interface ExportDocumentWidgetLocalizationBinder extends LocalizationBinder<ExportDocumentWidget> {
    }

    /**
     * The checkbox to whether copy the annotation.
     */
    @UiField
    @LocalizedField
    MaterialCheckBox chkCopyTextFormattingUI;

    /**
     * The info icon of the "copy text formatting" checkbox.
     */
    @UiField
    @LocalizedCommonField(tag = CommonLocalizationTags.COPY_TEXT_FORMATTING, type = LocalizedFieldType.TOOLTIP_MATERIAL)
    MaterialIcon icoInfoCopyFormattingUI;

    /**
     * The button for "copy to clipboard".
     */
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    @LocalizedCommonField(tag = CommonLocalizationTags.TEXT_COPY_TEXT_TO_CLIPBOARD, type = LocalizedFieldType.TOOLTIP_BASIC)
    MaterialButton btnCopyDocumentPreviewUI;

    /**
     * The button for "export as word document".
     */
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    @LocalizedCommonField(tag = CommonLocalizationTags.TEXT_SAVE_TEXT_AS_WORD_DOCUMENT, type = LocalizedFieldType.TOOLTIP_BASIC)
    MaterialButton btnExportDocumentPreviewToWordUI;

    /**
     * The copyright notice modal.
     */
    @UiField
    gwt.material.design.client.ui.MaterialDialog mdlCopyrightNoticeUI;

    /**
     * Checkbox whether the copyright notice should not be displayed anymore.
     */
    @UiField
    @LocalizedField
    MaterialCheckBox chkDontShowCopyrightNoticeUI;

    /**
     * The button to close the copyright notice.
     */
    @UiField
    MaterialButton btnCloseCopyrightNoticeUI;

    /**
     * Title of the copyright notice modal.
     */
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_TITLE)
    MaterialTitle titleCopyrightNoticeUI;

    /**
     * The ID of the preview document that will be used in copy/export.
     */
    private static final String DOC_TEXT_PREVIEW_ID = "docTextPreview";

    /**
     * The document preview pane.
     */
    static DocumentPreviewPane documentPreviewPane = DocumentPreviewPane.getInstance();

    public ExportDocumentWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));
        this.initHandlers();
    }

    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
         /*
        Remove the mdlCopyrightNoticeUI from this panel and add it to the root.
         This is necessary so that the modal will be displayed "on top".
         There is a bug when MaterialModal/MaterialDialog has a parent with layout set to absolute or relative.
         In this case, the "black veil" is on top of the MaterialModal/MaterialDialog and elements within the
         MaterialModal/MaterialDialog are not clickable (a click anywhere on the screen causes the MaterialModal/MaterialDialog to dismiss though).
         To fix this, the MaterialModal needs to be added to the RootPanel.
         */
        mdlCopyrightNoticeUI.removeFromParent();
        RootPanel.get().add(mdlCopyrightNoticeUI);

        btnCloseCopyrightNoticeUI.addClickHandler(event -> {
            mdlCopyrightNoticeUI.close();
        });

        btnCopyDocumentPreviewUI.addClickHandler(event -> {
            Boolean success = copyDocumentPreviewToClipboard(chkCopyTextFormattingUI.getValue());
            MaterialToast.fireToast(getLocalizedString(success ? LocalizationTags.TEXT_COPY_TO_CLIPBOARD_SUCCESSFUL : LocalizationTags.TEXT_COPY_TO_CLIPBOARD_FAILED));
            // open the copyright prompt if the user has not already set to ignore it.
            if (success && !chkDontShowCopyrightNoticeUI.getValue()) {
                titleCopyrightNoticeUI.setDescription(getLocalizedString(LocalizationTags.TEXT_COPY_TO_CLIPBOARD_COPYRIGHT_NOTICE));
                mdlCopyrightNoticeUI.open();
            }
        });

        btnExportDocumentPreviewToWordUI.addClickHandler(event -> {
            Boolean success = exportDocumentPreviewToDoc();
            MaterialToast.fireToast(getLocalizedString(success ? LocalizationTags.TEXT_EXPORT_TO_WORD_SUCCESSFUL : LocalizationTags.TEXT_EXPORT_TO_WORD_FAILED));

            // open the copyright prompt if the user has not already set to ignore it.
            if (success && !chkDontShowCopyrightNoticeUI.getValue()) {
                titleCopyrightNoticeUI.setDescription(getLocalizedString(LocalizationTags.TEXT_EXPORT_COPYRIGHT_NOTICE));
                mdlCopyrightNoticeUI.open();
            }
        });
    }


    /**
     * Copies the content of the document preview to the clipboard.
     * So far, it does NOT copy the table of grammatical occurrences.
     *
     * @param copyTextFormatting Whether to copy the text formatting / annotation of the grammatical constructions.
     */
    public boolean copyDocumentPreviewToClipboard(boolean copyTextFormatting) {

        ScrollPanel pnlDocTextPreview = documentPreviewPane.getPnlDocTextPreviewUI();
        Element elem = pnlDocTextPreview.getElement();
        RankableDocument doc = documentPreviewPane.getCurrentlyPreviewedDocument().getDocument();

        String formattedText = copyTextFormatting ? elem.getInnerHTML() : StringUtil.removeAllTagsByTag(elem.getInnerHTML(), "span");
        // TODO: prepare a proper HTML style.
        String html = "<h1>" + doc.getTitle() + "</h1>" +
                formattedText;

        return GwtUtil.copyElementToClipboard(html, doc.getTitle(), DOC_TEXT_PREVIEW_ID);
    }

    /**
     * Exports the content of the document preview to a word document.
     */
    public boolean exportDocumentPreviewToDoc() {

        // Retrieve all necessary fields.
        DocumentPreviewPaneInput.Rankable rankable = documentPreviewPane.getCurrentlyPreviewedDocument();
        RankableDocument doc = documentPreviewPane.getCurrentlyPreviewedDocument().getDocument();
        Element elem = documentPreviewPane.getPnlDocTextPreviewUI().getElement();

        // Get the formatted HTML.
        String html = getFormattedHTML(doc, elem, rankable, chkCopyTextFormattingUI.getValue());

        // Export the content as .doc
        return JSUtility.exportToWord(html, doc.getTitle() + "_" + GWT.getModuleName() + "_export", false);
    }

    /**
     * Gets the formatted HTML of the document.
     *
     * @param doc              The rankable document.
     * @param elem             The HTML document of the document.
     * @param rankable         The rankable representation of the document (needed for grammatical constructions table).
     * @param exportFormatting The formatted HTML.
     * @return The HTML representation of the document for export.
     */
    protected String getFormattedHTML(RankableDocument doc, Element elem, DocumentPreviewPaneInput.Rankable rankable, boolean exportFormatting) {
        // If the text should be exported without formatting, remove all <span> tags.
        String formattedText = exportFormatting ? elem.getInnerHTML() :
                StringUtil.removeAllTagsByTag(elem.getInnerHTML(), "span");

        // Remove the surrounding <div> tags
        formattedText = StringUtil.removeAllTagsByTag(formattedText, "div");

        // Load the HTML template
        TextResource textResource = LocalizedResources.get().getExportTemplate();
        String htmlTemplate = textResource.getText();

        // Create the construction data table.
        String tbl = getConstructionTable(rankable, documentPreviewPane.getConstructionsDetailsTable().dataProvider);

        // Create the html based on the template.
        String html = htmlTemplate.replace(WordDocumentTag.TITLE.getHTMLTag(), doc.getTitle())
                .replace(WordDocumentTag.LINK.getHTMLTag(), doc.getUrl()) // TODO: corpus documents do not have links
                .replace(WordDocumentTag.NUMBER_OF_SENTENCES.getHTMLTag(), doc.getNumSentences() + "")
                .replace(WordDocumentTag.NUMBER_OF_WORDS.getHTMLTag(), doc.getNumWords() + "")
                .replace(WordDocumentTag.READABILITY_LEVEL.getHTMLTag(), doc.getReadabilityLevel().toString())
                .replace(WordDocumentTag.TEXT.getHTMLTag(), formattedText)
                .replace(WordDocumentTag.GRAMMATICAL_CONSTRUCTIONS_TABLE.getHTMLTag(),
                        exportFormatting & documentPreviewPane.getConstructionsDetailsTable().hasData() ?
                                tbl : "")
                .replace(WordDocumentTag.FOOTER.getHTMLTag(), CommonLocalizationTags.FOOTER_WORD.getString())
                .replace(WordDocumentTag.LABEL_READABILITY_LEVEL.getHTMLTag(), CommonLocalizationTags.READABILITY_LEVEL.getString())
                .replace(WordDocumentTag.LABEL_NUMBER_OF_SENTENCES.getHTMLTag(), CommonLocalizationTags.SENTENCES.getString())
                .replace(WordDocumentTag.LABEL_SENTENCES.getHTMLTag(), CommonLocalizationTags.SENTENCES.getString())
                .replace(WordDocumentTag.LABEL_WORDS.getHTMLTag(), CommonLocalizationTags.WORDS.getString())
                .replace(WordDocumentTag.LBL_LINK.getHTMLTag(), CommonLocalizationTags.LABEL_LINK_TO_TEXT.getString());


        return html;
    }


    protected String getConstructionTableRow(String name, Color color, int hits, double weight) {
        String rgb = ColorHelper.setupComputedBackgroundColor(color);
        return "<tr><td class='myTable' style='background-color:" + rgb + "'>" + name + "</td>"
                + "<td class='myTable'>" + hits + "</td>"
                + "<td class='myTable'>(" + weight + ")</td></tr>";
    }


    /**
     * Creates a HTML table of the construction data of the rankable.
     *
     * @param rankable
     * @return
     */
    private String getConstructionTable(DocumentPreviewPaneInput.Rankable rankable, List<DocumentPreviewPane.TableData> dataProvider) {
        // Create the construction data table.
        String tbl = LocalizedResources.get().getConstructionTableTemplate().getText();

        // Insert labels.
        tbl = tbl.replace(WordDocumentTag.LABEL_CONSTRUCTION.getHTMLTag(), CommonLocalizationTags.CONSTRUCTION.getString())
                .replace(WordDocumentTag.LABEL_HITS.getHTMLTag(), CommonLocalizationTags.HITS.getString())
                .replace(WordDocumentTag.LABEL_WEIGHT.getHTMLTag(), CommonLocalizationTags.WEIGHT.getString());

        // Create the construction data.
        StringBuilder constructionData = new StringBuilder();
        for (DocumentPreviewPane.TableData itr : dataProvider) {

            String name = itr.getLocalizedName(getCurrentLocale());
            int hits = itr.hits;
            double weight = itr.displayedWeight;
            Color color = rankable.getConstructionAnnotationColor(itr.gram);

            constructionData.append(
                    getConstructionTableRow(name, color, hits, weight));
        }

        tbl = tbl.replace(WordDocumentTag.CONSTRUCTION_DATA.getHTMLTag(), constructionData.toString());
        return tbl;
    }


}