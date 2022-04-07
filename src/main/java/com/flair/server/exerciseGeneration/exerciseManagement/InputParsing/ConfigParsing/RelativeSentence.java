package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

public class RelativeSentence {
	private ArrayList<String> chunks = null;
	private boolean pronounIsOptional = false;
	private boolean useToGenerateExercise = true;
	public ArrayList<String> getChunks() {
		return chunks;
	}
	public void setChunks(ArrayList<String> sentence) {
		this.chunks = sentence;
	}
	public boolean isPronounIsOptional() {
		return pronounIsOptional;
	}
	public void setPronounIsOptional(boolean pronounIsOptional) {
		this.pronounIsOptional = pronounIsOptional;
	}
	public boolean isUseToGenerateExercise() {
		return useToGenerateExercise;
	}
	public void setUseToGenerateExercise(boolean useToGenerateExercise) {
		this.useToGenerateExercise = useToGenerateExercise;
	}
	
	
}
