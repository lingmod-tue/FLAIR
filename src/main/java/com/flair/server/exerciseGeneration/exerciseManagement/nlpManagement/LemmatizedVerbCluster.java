package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.ArrayList;

public class LemmatizedVerbCluster {
	private String lemmatizedCluster = null;
	private ArrayList<String> nonLemmatizedComponents = new ArrayList<>();
	private String modal = null;
	private String mainLemma = null;
	
	public String getLemmatizedCluster() { return lemmatizedCluster; }
	public ArrayList<String> getNonLemmatizedComponents() { return nonLemmatizedComponents; }
	public String getModal() { return modal; }	
	public String getMainLemma() { return mainLemma; }
	
	public LemmatizedVerbCluster(String lemmatizedCluster, ArrayList<String> nonLemmatizedComponents, String modal, String mainLemma) {
		this.lemmatizedCluster = lemmatizedCluster;
		this.nonLemmatizedComponents = nonLemmatizedComponents;
		this.modal = modal;
		this.mainLemma = mainLemma;
	}
	
}
