package com.flair.client.localization.resources;

import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/*
 * Provides an interface to retrieve external locale data
 */
public class LocalizedResources {
    private static final LocalizedResources INSTANCE = new LocalizedResources();

    public static LocalizedResources get() {
        return INSTANCE;
    }

    interface EnglishStrings extends ClientBundle {
        @Source("strings-en-constructions.tsv")
        public TextResource getGramConstructionStrings();

        @Source("strings-en-general.tsv")
        public TextResource getGeneralStrings();

    }

    interface GermanStrings extends ClientBundle {
        @Source("strings-de-constructions.tsv")
        public TextResource getGramConstructionStrings();

        @Source("strings-de-general.tsv")
        public TextResource getGeneralStrings();




    }
    interface CommonStrings extends ClientBundle{
        @Source("document-output-construction-table-template.html")
        public TextResource getConstructionTableTemplate();

        @Source("document-output-template.html")
        public TextResource getExportTemplate();
    }

    private final EnglishStrings englishStrings;
    private final GermanStrings germanStrings;
    private final CommonStrings commonStrings;

    private LocalizedResources() {
        englishStrings = GWT.create(EnglishStrings.class);
        germanStrings = GWT.create(GermanStrings.class);
        commonStrings = GWT.create(CommonStrings.class);
    }

    public TextResource getGramConstructionStrings(Language lang) {
        switch (lang) {
            case ENGLISH:
                return englishStrings.getGramConstructionStrings();
            case GERMAN:
                return germanStrings.getGramConstructionStrings();
            default:
                throw new RuntimeException("No localized gramconst strings for language " + lang);
        }
    }

    public TextResource getGeneralStrings(Language lang) {
        switch (lang) {
            case ENGLISH:
                return englishStrings.getGeneralStrings();
            case GERMAN:
                return germanStrings.getGeneralStrings();
            default:
                throw new RuntimeException("No localized general strings for language " + lang);
        }
    }

    /**
     * Gets the HTML template for the export of the preview document.
     *
     * @return The HTML template for exporting the preview document.
     */
    public TextResource getExportTemplate() {
        return commonStrings.getExportTemplate();
    }

    /**
     * Gets the HTML template for the construction table of the preview document.
     *
     * @return The HTML template for the construction table of the preview document.
     */
    public TextResource getConstructionTableTemplate() {
        return commonStrings.getConstructionTableTemplate();
    }
}

