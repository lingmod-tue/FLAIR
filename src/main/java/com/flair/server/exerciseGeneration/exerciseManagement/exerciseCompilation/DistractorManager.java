package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class DistractorManager {

	/**
     * Generates distractors for Single Choice exercises.
     * @param exerciseSettings  The exercise settings
     */
    public void generateDistractors(ExerciseSettings exerciseSettings, NlpManager nlpManager) {
        ArrayList<Construction> constructionsToRemove = new ArrayList<>();

        if(exerciseSettings.getContentType().equals("Select")) {
            for(Construction construction : exerciseSettings.getConstructions()) {
                HashSet<String> options = new HashSet<>();
                HashSet<String> incorrectFormOptions = new HashSet<>();

                String name = construction.getConstruction().toString();
                if(name.startsWith("COND")) {
                    boolean isMainClause = false;
                    TenseSettings mainClauseSettings = null;
                    TenseSettings ifClauseSettings = null;

                    Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> clauses = nlpManager.getConditionalClauses(construction.getConstructionIndices());
                    if(clauses != null) {   // should not be possible, we filtered out those which returned null in the brackets generation
                        Pair<Integer, Integer> mainClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.first);
                        if (mainClauseConstructionIndices != null) {
                            if(mainClauseConstructionIndices.first.equals(construction.getConstructionIndices().first)) {
                                isMainClause = true;
                            }
                            mainClauseSettings = nlpManager.getConditionalClauseSpecifics(mainClauseConstructionIndices);
                        }
                        Pair<Integer, Integer> ifClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.second);
                        if (ifClauseConstructionIndices != null) {
                            ifClauseSettings = nlpManager.getConditionalClauseSpecifics(ifClauseConstructionIndices);
                        }
                    }

                    if (exerciseSettings.getDistractors().contains(DistractorProperties.WRONG_CLAUSE)) {
                        if(ifClauseSettings != null && mainClauseSettings != null) {
                            TenseSettings clause1 = isMainClause ? mainClauseSettings : ifClauseSettings;
                            TenseSettings clause2 = isMainClause ? ifClauseSettings : mainClauseSettings;

                            options.add(nlpManager.generateCorrectForm(new TenseSettings(clause1.getLemma(), clause1.isInterrogative(),
                                    clause1.isNegated(), clause1.isThirdSingular(), clause1.getSubject(), clause2.getTense(),
                                    clause2.isProgressive(), clause2.isPerfect(), clause2.getModal())));
                        }
                    }
                    if (exerciseSettings.getDistractors().contains(DistractorProperties.WRONG_CONDITIONAL)) {
                        if(isMainClause && mainClauseSettings != null) {
                            if(construction.getConstruction() == DetailedConstruction.CONDREAL) {
                                if (mainClauseSettings.getModal() != null) {
                                    mainClauseSettings.setModal(nlpManager.getUnrealModal(mainClauseSettings.getModal()));
                                } else {
                                    mainClauseSettings.setModal("would");
                                    mainClauseSettings.setPerfect(!mainClauseSettings.getTense().equals("present"));
                                }
                            } else {
                                if(mainClauseSettings.getModal() != null) {
                                    mainClauseSettings.setModal(nlpManager.getRealModal(mainClauseSettings.getModal()));
                                } else {
                                    mainClauseSettings.setPerfect(false);
                                    if(!mainClauseSettings.getTense().equals("past")) {
                                        mainClauseSettings.setModal("will");
                                    }
                                }
                            }

                            options.add(nlpManager.generateCorrectForm(mainClauseSettings));
                        } else if(!isMainClause && ifClauseSettings != null) {
                            if(construction.getConstruction() == DetailedConstruction.CONDREAL) {
                                if (ifClauseSettings.getTense().equals("past")) {
                                    ifClauseSettings.setPerfect(true);
                                } else {
                                    ifClauseSettings.setTense("past");
                                    ifClauseSettings.setPerfect(false);
                                }
                            } else {
                                ifClauseSettings.setPerfect(false);
                                if(!(ifClauseSettings.getTense().equals("past") && ifClauseSettings.isPerfect())) {
                                    ifClauseSettings.setTense("present");
                                }
                            }

                            options.add(nlpManager.generateCorrectForm(ifClauseSettings));
                        }
                    }
                } else if(name.startsWith("ADJ") || name.startsWith("ADV")) {
                    boolean isComparative = construction.getConstruction().toString().contains("_COMP_");
                    boolean isSynthetic = construction.getConstruction().toString().endsWith("SYN");
                    boolean isAdjective = name.startsWith("ADJ");
                    String lemma = nlpManager.getLemmaOfComparison(construction.getConstructionIndices());

                    ArrayList<Pair<Boolean, Boolean>> parameterConstellations = new ArrayList<>();
                    parameterConstellations.add(new Pair<>(isSynthetic, isComparative));

                    if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_FORM)) {
                        for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
                            parameterConstellations.add(new Pair<>(parameterConstellation.first, !isComparative));
                        }
                    }
                    if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_VARIANT)) {
                        for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
                            parameterConstellations.add(new Pair<>(!isSynthetic, parameterConstellation.second));
                        }
                    }

                    for(int i = 0; i < parameterConstellations.size(); i++) {
                        Pair<Boolean, Boolean> parameterConstellation = parameterConstellations.get(i);
                        if(i != 0) {    // don't calculate the correct form, we already have it
                            options.add(nlpManager.generateCorrectForm(new ComparisonSettings(parameterConstellation.first,
                                    parameterConstellation.second, lemma, isAdjective)));
                        }
                        if(exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_FORMS)) {
                            incorrectFormOptions.addAll(nlpManager.generateIncorrectForms(new ComparisonSettings(parameterConstellation.first,
                                    parameterConstellation.second, lemma, construction.getConstructionText())));
                        }
                    }
                } else if(name.startsWith("QUEST") || name.startsWith("STMT")){
                    boolean is3Sg = construction.getConstruction().toString().endsWith("_3");
                    boolean isInterrogative = construction.getConstruction().toString().startsWith("QUEST");
                    boolean isNegated = construction.getConstruction().toString().contains("_NEG_");
                    Pair<String, String> lemma =
                            nlpManager.getVerbLemma(construction.getConstructionIndices(), isInterrogative);

                    if(lemma != null) {
	                    ArrayList<Boolean> parameterConstellations = new ArrayList<>();
	                    parameterConstellations.add(is3Sg);
	
	                    if(exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_SUFFIX)) {
	                        parameterConstellations.add(!is3Sg);
	                    }
	
	                    for(int i = 0; i < parameterConstellations.size(); i++) {
	                        boolean parameterConstellation = parameterConstellations.get(i);
	                        if(i != 0) {    // don't calculate the correct form, we already have it
	                            options.add(nlpManager.generateCorrectForm(new TenseSettings(lemma.first, isInterrogative,
	                                    isNegated, parameterConstellation, lemma.second, "present",
	                                    false, false)));
	                        }
	                        if(exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_FORMS)) {
	                            incorrectFormOptions.addAll(nlpManager.generateIncorrectForms(new TenseSettings(lemma.first, isInterrogative,
	                                    isNegated, parameterConstellation, lemma.second, "present",false, false)));
	                        }
	                    }
                    } else {
                    	//TODO: jsut for debugging
                    	lemma =
                                nlpManager.getVerbLemma(construction.getConstructionIndices(), isInterrogative);
                    }
                } else if(name.startsWith("PAST") || name.startsWith("PRES")) {
                    boolean isPerfect = construction.getConstruction().toString().contains("PERF_");
                    String tense = construction.getConstruction().toString().startsWith("PAST") ? "past" : "present";
                    boolean isInterrogative = construction.getConstruction().toString().contains("_QUEST_");
                    Pair<String, String> lemma =
                            nlpManager.getVerbLemma(construction.getConstructionIndices(), isInterrogative);
                    boolean isNegated = construction.getConstruction().toString().contains("_NEG_");

                    if(lemma != null) {
	                    ArrayList<Pair<Boolean, String>> parameterConstellations = new ArrayList<>();
	
	                    if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_PAST)) {
	                        parameterConstellations.add(new Pair<>(true, "present"));
	                        parameterConstellations.add(new Pair<>(false, "present"));
	                        parameterConstellations.add(new Pair<>(true, "past"));
	                    } else {
	                        parameterConstellations.add(new Pair<>(isPerfect, tense));
	                    }
	                    if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_TENSE)) {
	                        parameterConstellations.add(new Pair<>(true, "future"));
	                        parameterConstellations.add(new Pair<>(false, "future"));
	                        parameterConstellations.add(new Pair<>(false, "past"));
	                    }
	
	                    for (Pair<Boolean, String> parameterConstellation : parameterConstellations) {
	                        for (int j = 0; j <= 1; j++) {
	                            // We don't know if we have a 3rd pers. sing. form or not, so we calculate both forms also for the correct parameter settings
	                            options.add(nlpManager.generateCorrectForm(new TenseSettings(lemma.first, isInterrogative,
	                                    isNegated, j == 0, lemma.second, parameterConstellation.second,
	                                    false, parameterConstellation.first)));
	                            if (exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_FORMS)) {
	                                incorrectFormOptions.addAll(nlpManager.generateIncorrectForms(new TenseSettings(lemma.first, isInterrogative,
	                                        isNegated, j == 0, lemma.second, parameterConstellation.second,
	                                        false, parameterConstellation.first)));
	                            }
	                        }
	                    }
                    }
                } else if(construction.getConstruction() == DetailedConstruction.WHO ||
                        construction.getConstruction() == DetailedConstruction.WHICH ||
                        construction.getConstruction() == DetailedConstruction.THAT ||
                        construction.getConstruction() == DetailedConstruction.OTHERPRN) {
                    // We make sure to only include those pronouns as distractors which actually occur in the text
                    for(Construction c : exerciseSettings.getConstructions()) {
                        options.add(c.getConstructionText());
                    }
                }

                removeCorrectForm(construction.getConstructionText(), options);
                removeCorrectForm(construction.getConstructionText(), incorrectFormOptions);

                if(options.size() == 0 && incorrectFormOptions.size() == 0) {
                    constructionsToRemove.add(construction);
                } else {
                    ArrayList<String> incorrectOptions = new ArrayList<>(incorrectFormOptions);
                    Collections.shuffle(incorrectOptions);
                    if(options.size() > 0) {
                        // We don't want too many incorrect forms, so we take half as many as correctly formed variants and only pad if necessary
                        int incorrectFormsToAdd = options.size() / 2;
                        for(int i = 0; i < incorrectFormsToAdd && incorrectOptions.size() > 0; i++) {
                            int randomIndex = new Random().nextInt(incorrectOptions.size());
                            String randomForm = incorrectOptions.get(randomIndex);
                            int previousSize = options.size();
                            options.add(randomForm);
                            if(previousSize == options.size()) {    // the option had already been in the set
                                i--;
                            }
                            incorrectOptions.remove(randomForm);
                        }
                    }

                    // Add incorrect forms until we have the necessary distractor number or no more options to add
                    while(options.size() < exerciseSettings.getnDistractors() && incorrectOptions.size() > 0) {
                        int randomIndex = new Random().nextInt(incorrectOptions.size());
                        String randomForm = incorrectOptions.get(randomIndex);
                        options.add(randomForm);
                        incorrectOptions.remove(randomForm);
                    }

                    // Add the distractors to the settings
                    ArrayList<String> distractors = new ArrayList<>(options);
                    Collections.shuffle(distractors);
                    construction.setDistractors(distractors);
                }
            }
        }

        for(Construction constructionToRemove : constructionsToRemove) {
            exerciseSettings.getConstructions().remove(constructionToRemove);
        }
    }

    /**
     * Removes the correct form from the incorrect options
     * @param correctFormText   The text of the correct form
     * @param options           The incorrect options
     */
    private void removeCorrectForm(String correctFormText, HashSet<String> options) {
        String correctOption = null;
        for(String option : options) {
            if(option.equals(correctFormText)) {
                correctOption = option;
            }
        }
        if(correctOption != null) {
            options.remove(correctOption);
        }
    }
    
}
