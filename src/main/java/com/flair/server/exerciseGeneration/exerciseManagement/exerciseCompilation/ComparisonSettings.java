package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;

public class ComparisonSettings implements ParameterSettings {

    /**
     * <c>true</c> if synthetic forms are to be generated; <c>false</c> if analytic forms are to be generated
     */
    private boolean synthetic;
    /**
     * <c>true</c> if comparative formas are to be generated, <c>false</c> if superlative forms are to be generated
     */
    private boolean comparative;

    /**
     * The lemma of the adjective or adverb
     */
    private String lemma;
    /**
     * The correct form
     */
    private String correctForm;

    /**
     *      <c>true</c> if the lemma is an adjective; <c>false</c> if it is an adverb
     */
    private boolean isAdjective;

    public ComparisonSettings(boolean synthetic, boolean comparative, String lemma, String correctForm) {
        this.synthetic = synthetic;
        this.comparative = comparative;
        this.lemma = lemma;
        this.correctForm = correctForm;
    }

    public ComparisonSettings(boolean synthetic, boolean comparative, String lemma, boolean isAdjective) {
        this.synthetic = synthetic;
        this.comparative = comparative;
        this.lemma = lemma;
        this.isAdjective = isAdjective;
    }

    public boolean isSynthetic() {
        return synthetic;
    }

    public boolean isComparative() {
        return comparative;
    }

    public String getLemma() {
        return lemma;
    }

    public String getCorrectForm() {
        return correctForm;
    }

    public boolean isAdjective() {
        return isAdjective;
    }
}
