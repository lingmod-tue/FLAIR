package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

public class RelativeClausePosition {
	private String value;
	private boolean isPronoun = false;
	private boolean isCommonReferent = false;
	private boolean isLastCommonReferent = false;
	private ArrayList<String> alternatives = new ArrayList<>();

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

	public ArrayList<String> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(ArrayList<String> alternatives) {
		this.alternatives = alternatives;
	}

	public boolean isLastCommonReferent() {
		return isLastCommonReferent;
	}

	public void setLastCommonReferent(boolean isLastCommonReferent) {
		this.isLastCommonReferent = isLastCommonReferent;
	}
	
}
