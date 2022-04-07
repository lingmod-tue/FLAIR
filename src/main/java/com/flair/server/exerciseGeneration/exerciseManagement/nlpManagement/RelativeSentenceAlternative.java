package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.ArrayList;

public class RelativeSentenceAlternative {
	private ArrayList<RelativeSentenceChunk> chunks = new ArrayList<>();
	private boolean useToGenerateExercise = true;
	private boolean pronounIsOptional = false;

	public ArrayList<RelativeSentenceChunk> getChunks() {
		return chunks;
	}

	public void setChunks(ArrayList<RelativeSentenceChunk> chunks) {
		this.chunks = chunks;
	}

	public boolean isUseToGenerateExercise() {
		return useToGenerateExercise;
	}

	public void setUseToGenerateExercise(boolean useToGenerateExercise) {
		this.useToGenerateExercise = useToGenerateExercise;
	}

	public boolean isPronounIsOptional() {
		return pronounIsOptional;
	}

	public void setPronounIsOptional(boolean pronounIsOptional) {
		this.pronounIsOptional = pronounIsOptional;
	}
}
