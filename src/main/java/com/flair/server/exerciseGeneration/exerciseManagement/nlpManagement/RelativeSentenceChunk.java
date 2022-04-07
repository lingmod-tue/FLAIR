package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

public class RelativeSentenceChunk {
	private String value;
    private boolean isPronoun = false;
    private boolean isCommonReferent = false;

    public RelativeSentenceChunk(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isPronoun() {
        return isPronoun;
    }

    public boolean isCommonReferent() {
        return isCommonReferent;
    }

    public void setPronoun(boolean isPronoun) {
        this.isPronoun = isPronoun;
    }

    public void setCommonReferent(boolean isCommonReferent) {
        this.isCommonReferent = isCommonReferent;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
