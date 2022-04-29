package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class RelativeSentenceAlternative {
	private ArrayList<RelativeSentenceChunk> chunks = new ArrayList<>();
	private boolean useToGenerateExercise = true;
	private boolean pronounIsOptional = false;
	private int pronounIndex = 0;
	private int promptEndIndex = 0;

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

	public int getPronounIndex() {
		return pronounIndex;
	}

	public void setPronounIndex(int pronounIndex) {
		this.pronounIndex = pronounIndex;
	}

	public int getPromptEndIndex() {
		return promptEndIndex;
	}

	public void setPromptEndIndex(int promptEndIndex) {
		this.promptEndIndex = promptEndIndex;
	}

}
