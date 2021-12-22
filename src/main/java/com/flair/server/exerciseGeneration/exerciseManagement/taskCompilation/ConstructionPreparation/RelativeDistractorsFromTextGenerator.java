package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ComparisonSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.TenseSettings;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class RelativeDistractorsFromTextGenerator extends DistractorsFromTextGenerator {
	
	public RelativeDistractorsFromTextGenerator(NlpManager nlpManager, ArrayList<Construction> constructions,
			String plainText) {
		super(nlpManager);
		this.constructions = new HashSet<>();
		for(Construction c : constructions) {
        	String text = plainText.substring(c.getConstructionIndices().first, c.getConstructionIndices().second);
        	this.constructions.add(text);
        }
	}
	
	private final HashSet<String> constructions;
	
	@Override
	protected Pair<HashSet<String>, HashSet<String>> generateSCDistractors(Construction construction, ExerciseType exerciseType, 
			ArrayList<DistractorProperties> distractorProperties, String plainText) {
        HashSet<String> incorrectFormOptions = new HashSet<>();

        // We make sure to only include those pronouns as distractors which actually occur in the text       
        return new Pair<>(constructions, incorrectFormOptions);
	}
	
}
