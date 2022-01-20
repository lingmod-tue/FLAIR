package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class RelativeClausePosition {
	private String value;
	private boolean isPronoun = false;
	private boolean isCommonReferent = false;

	public RelativeClausePosition(String value) {
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
