package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Map.Entry;

import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Blank;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Fragment;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.ExerciseComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;

public abstract class BracketsFromTextGenerator extends BracketsGenerator {
	
	public BracketsFromTextGenerator(NlpManager nlpManager, ArrayList<BracketsProperties> bracketsProperties) {
		this.nlpManager = nlpManager;
		this.bracketsProperties = bracketsProperties;
	}
	
	protected final NlpManager nlpManager;
	protected final ArrayList<BracketsProperties> bracketsProperties;
	protected ArrayList<String> lemmas = new ArrayList<>();
    protected ArrayList<Integer> constructionsToRemove = new ArrayList<>();

    @Override
	public void generateBrackets(ExerciseSettings exerciseSettings, ExerciseComponents exerciseComponents) {
		if(exerciseSettings.getContentType().equals(ExerciseType.FIB)){
	        ArrayList<String> lemmas = new ArrayList<>();
	
	        for(Entry<Integer, TargetConstruction> targetConstruction : exerciseComponents.getTargetConstructions().entrySet()) {
                ArrayList<String> brackets = 
                		generateBracketsContent(targetConstruction.getKey(), targetConstruction.getValue());
	            
	            targetConstruction.getValue().setBracketsComponents(brackets);
	        }
	        
	        for(int constructionToRemove : constructionsToRemove) {
            	exerciseComponents.getTargetConstructions().remove(constructionToRemove);
            }  	
	        
	        if(exerciseSettings.getBrackets().contains(BracketsProperties.DISTRACTOR_LEMMA)) {
		        for(Entry<Integer, TargetConstruction> targetConstruction : exerciseComponents.getTargetConstructions().entrySet()) {
		        	
	        		if(targetConstruction.getValue().getBracketsComponents() != null &&
	        				targetConstruction.getValue().getBracketsComponents().size() > 0) {
	                	String distractorLemma = "";
	        			for(int i = 0; i < targetConstruction.getValue().getBracketsComponents().size(); i++) {
	        				String bracketsComponent = targetConstruction.getValue().getBracketsComponents().get(i);
	        				if(bracketsComponent.equals(distractorLemma + "|<distractor_placeholder>") ||
	        						bracketsComponent.equals("<distractor_placeholder>|" + distractorLemma)){
				        		if(lemmas.size() > 1) {
		        					Collections.shuffle(lemmas);
				        			distractorLemma = lemmas.get(0);
				        			targetConstruction.getValue().getBracketsComponents()
				        					.set(i, bracketsComponent.replace("<distractor_placeholder>", distractorLemma));

				        			break;
		        				} else {
				        			targetConstruction.getValue().getBracketsComponents()
		        							.set(i, bracketsComponent.replace("|<distractor_placeholder>", "").replace("<distractor_placeholder>|", ""));
				        		}
		        			} 
		        		} 
	        		}
	        	}
        	}
        }
	}
	
	/**
	 * Generates a brackets element for lemma and distractor lemma with a placeholder element for a distractor lemma.
	 * The placeholder needs to be replaced with the real distractor lemma later.
	 * We might lose some of the lemmas later, but it doesn't matter if we still use them as distractor lemmas.
	 * @param lemma	The lemma of the construction
	 * @return	The lemma and the placeholder for a distractor lemma in random order, separated by a pipe (|)
	 */
	protected String generateDistractorLemma(String lemma) {
    	int order = new Random().nextInt(2);
		if(order == 0) {
			return "<distractor_placeholder>|" + lemma;
		} else {
			return lemma + "|<distractor_placeholder>";
		}
    }
	
}
