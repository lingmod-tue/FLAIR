package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

public class TenseSettings implements ParameterSettings {

    /**
     * The lemma
     */
    private String lemma;
    /**
     * <c>true</c> if the generated form is to be a question; otherwise <c>false</c>
     */
    private boolean interrogative;
    /**
     * <c>true</c> if the generated form is to be negated; otherwise <c>false</c>
     */
    private boolean negated;
    /**
     * <c>true</c> if the generated form is to be in 3rd person singular
     */
    private boolean thirdSingular;
    /**
     * The subject used in a question
     */
    private String subject;
    /**
     * The tense of the form to be generated
     */
    private String tense;
    /**
     * <c>true</c> if the generated form is to be progressive
     */
    private boolean progressive;
    /**
     * <c>true</c> if the generated form is to be perfect
     */
    private boolean perfect;

    private String modal = null;

    public TenseSettings(String lemma, boolean interrogative, boolean negated, boolean thirdSingular, String subject,
                         String tense, boolean progressive, boolean perfect, String modal) {
        if(subject == null) {
            subject = thirdSingular ? "he" : "they";
        }

        this.lemma = lemma;
        this.interrogative = interrogative;
        this.negated = negated;
        this.thirdSingular = thirdSingular;
        this.subject = subject;
        this.tense = tense;
        this.progressive = progressive;
        this.perfect = perfect;
        this.modal = modal;
    }

    public TenseSettings(String lemma, boolean interrogative, boolean negated, boolean thirdSingular, String subject,
                         String tense, boolean progressive, boolean perfect) {
        this(lemma, interrogative, negated, thirdSingular, subject, tense, progressive, perfect, null);
    }

    public String getLemma() {
        return lemma;
    }
    public boolean isInterrogative() {
        return interrogative;
    }
    public boolean isNegated() {
        return negated;
    }
    public boolean isThirdSingular() {
        return thirdSingular;
    }
    public String getSubject() {
        return subject;
    }
    public String getTense() {
        return tense;
    }
    public boolean isProgressive() {
        return progressive;
    }
    public boolean isPerfect() {
        return perfect;
    }
    public String getModal() {
        return modal;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setTense(String tense) {
        this.tense = tense;
    }
    public void setProgressive(boolean progressive) {
        this.progressive = progressive;
    }
    public void setPerfect(boolean perfect) {
        this.perfect = perfect;
    }
    public void setModal(String modal) {
        this.modal = modal;
    }
}
