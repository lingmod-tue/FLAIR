package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.Random;

import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class ClozeManager {

	/**
	 * Modifies construction indices in-place if necessary and generates brackets texts for Fib tasks.
	 * @param exerciseSettings	The exercise settings
	 * @param parser			The Stanford CoreNLP parser
	 * @param generator			The Simple NLG generator
	 */
    public void prepareBlanks(ExerciseSettings exerciseSettings, NlpManager nlpManager) {
    	ArrayList<Construction> constructionsToAdd = new ArrayList<>();
        ArrayList<Construction> constructionsToRemove = new ArrayList<>();
        for(Construction construction : exerciseSettings.getConstructions()) {
            if(construction.getConstruction().toString().startsWith("COND")) {
                Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> clauses = nlpManager.getConditionalClauses(construction.getConstructionIndices());
                if(clauses != null) {
                    int r = 0;
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.EITHER_CLAUSE)) {
                        r = new Random().nextInt(2) + 1;
                    }
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.MAIN_CLAUSE) || r == 1) {
                        Pair<Integer, Integer> mainClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.first);
                        if(mainClauseConstructionIndices != null) {
                            Construction newConstruction = new Construction(construction.getConstruction(), mainClauseConstructionIndices);
                            if (exerciseSettings.getContentType().equals("FiB")) {
                                ArrayList<String> brackets = new ArrayList<>();
                                addBracketsToConditionals(exerciseSettings, newConstruction, true, brackets, nlpManager);
                                newConstruction.setBracketsText("(" + String.join(", ", brackets) + ")");
                            }
                            constructionsToAdd.add(newConstruction);
                        }
                    }
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.IF_CLAUSE) || r == 2) {
                        Pair<Integer, Integer> ifClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.second);
                        if(ifClauseConstructionIndices != null) {
                            Construction newConstruction = new Construction(construction.getConstruction(), ifClauseConstructionIndices);
                            if (exerciseSettings.getContentType().equals("FiB")) {
                                ArrayList<String> brackets = new ArrayList<>();
                                addBracketsToConditionals(exerciseSettings, newConstruction, false, brackets, nlpManager);
                                newConstruction.setBracketsText("(" + String.join(", ", brackets) + ")");
                            }
                            constructionsToAdd.add(newConstruction);
                        }
                    }
                }
                constructionsToRemove.add(construction);
            } else if(construction.getConstruction().toString().startsWith("ADJ") ||
                        construction.getConstruction().toString().startsWith("ADV")) {
                if (exerciseSettings.getContentType().equals("Mark")) {
                    //TODO: if we decide to allow the client to specify whether to split synthetic forms for mark, we have to do that here
                } else if(exerciseSettings.getContentType().equals("FiB")){
                    ArrayList<String> brackets = new ArrayList<>();
                    if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                        String lemma = nlpManager.getLemmaOfComparison(construction.getConstructionIndices());
                        if(lemma != null) {
                            brackets.add(lemma);
                        } else {
                            constructionsToRemove.add(construction);
                            break;
                        }
                    }
                    if(exerciseSettings.getBrackets().contains(BracketsProperties.POS)) {
                        String pos = construction.getConstruction().toString().startsWith("ADJ") ? "adjective" : "adverb";
                        brackets.add(pos);
                    }

                    if(exerciseSettings.getBrackets().contains(BracketsProperties.COMPARISON_FORM)) {
                        String form = construction.getConstruction().toString().contains("_COMP_") ? "comparative" : "superlative";
                        brackets.add(form);
                    }
                    construction.setBracketsText("(" + String.join(", ", brackets) + ")");
                }
            } else if(construction.getConstruction().toString().startsWith("PASSIVE") ||
                    construction.getConstruction().toString().startsWith("ACTIVE")) {
                if(exerciseSettings.getContentType().equals("FiB")) {
                    ArrayList<String> brackets = new ArrayList<>();
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
                        Pair<String, Pair<Integer, Integer>> activeSentence = nlpManager.getActiveSentence(construction.getConstructionIndices(), exerciseSettings.getPlainText(), construction.getConstruction());
                        if (activeSentence != null) {
                            construction.setConstructionIndices(activeSentence.second);
                            brackets.add(activeSentence.first);
                        } else {
                            constructionsToRemove.add(construction);
                        }
                    } else {
                        String lemma = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices());
                        if (lemma != null) {
                            brackets.add(lemma);
                        } else {
                            constructionsToRemove.add(construction);
                        }
                    }
                    construction.setBracketsText("(" + String.join(", ", brackets) + ")");
                } else if(exerciseSettings.getContentType().endsWith("Drag") &&
                        exerciseSettings.getBrackets().contains(BracketsProperties.VERB_SPLITTING)) {
                    ArrayList<Pair<Integer, Integer>> parts = nlpManager.splitParticiple(construction.getConstructionIndices());
                    if(parts != null) { // if the splitting wasn't successful, we keep it as entire cluster
                        for (Pair<Integer, Integer> part : parts) {
                            constructionsToAdd.add(new Construction(construction.getConstruction(), part));
                        }
                        constructionsToRemove.add(construction);
                    }
                }
            } else if((construction.getConstruction().toString().startsWith("QUEST") ||
                            construction.getConstruction().toString().startsWith("STMT") ||
                            construction.getConstruction().toString().startsWith("PAST") ||
                            construction.getConstruction().toString().startsWith("PRES")) &&
                    exerciseSettings.getContentType().equals("FiB")) {
                ArrayList<String> brackets = new ArrayList<>();
                if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                    String lemma = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices());
                    if(lemma != null) {
                        brackets.add(lemma);
                    } else {
                        constructionsToRemove.add(construction);
                        break;
                    }
                }
                if(construction.getConstruction().toString().contains("QUEST")) {
                    brackets.add("interrog");
                }
                if(construction.getConstruction().toString().contains("_NEG_")) {
                    brackets.add("neg");
                }
                if(construction.getConstruction().toString().startsWith("PAST") &&
                        exerciseSettings.getBrackets().contains(BracketsProperties.TENSE)) {
                    String tense = construction.getConstruction().toString().startsWith("PASTSMP") ? "simple past" :
                            construction.getConstruction().toString().startsWith("PRESPERF") ? "present perfect" : "past perfect";
                    brackets.add(tense);
                }
                construction.setBracketsText("(" + String.join(", ", brackets) + ")");
            }
        }

        for(Construction construction : constructionsToRemove) {
            exerciseSettings.getConstructions().remove(construction);
        }

        for(Construction construction : constructionsToAdd) {
            exerciseSettings.getConstructions().add(construction);
        }
    }

    /**
     * compiles the text for brackets of conditional exercises.
     * @param exerciseSettings  The exercise settings
     * @param construction      The construction of the blank
     * @param isMain            <c>true</c> if the blank is in a main clause; otherwise <c>false</c>
     * @param brackets          The array into which to write the brackets components
     * @param nlpManager		The NLP manager
     */
    private void addBracketsToConditionals(ExerciseSettings exerciseSettings, Construction construction, boolean isMain, 
    		ArrayList<String> brackets, NlpManager nlpManager) {
        if (exerciseSettings.getContentType().equals("FiB")) {
            if (exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                brackets.add(nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices()));
            }
            if (exerciseSettings.getBrackets().contains(BracketsProperties.CONDITIONAL_TYPE)) {
                brackets.add(construction.getConstruction() == DetailedConstruction.CONDREAL ? "real" : "unreal");
            }
            if (isMain && exerciseSettings.getBrackets().contains(BracketsProperties.WILL)) {
                brackets.add(construction.getConstruction() == DetailedConstruction.CONDREAL ? "real" : "unreal");
            }
        }
    }

}
