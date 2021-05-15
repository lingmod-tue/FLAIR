package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Blank;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Fragment;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

import edu.stanford.nlp.util.Pair;

public class InstructionsManager {

	public static String componseTaskDescription(ExerciseSettings settings, NlpManager nlpManager, ArrayList<Fragment> fragments) {
        HashSet<DetailedConstruction> constructions = new HashSet<>();
        ArrayList<Construction> usedConstructions = new ArrayList<>();
        for(Fragment fragment : fragments) {
        	for(Blank blank : fragment.getBlanksBoundaries()) {
        		if(blank.getConstruction() != null) {
                    constructions.add(blank.getConstruction().getConstruction());
                    usedConstructions.add(blank.getConstruction());
        		}
        	}
        }

        String instructions = "";
        boolean addLemmas = false;
        boolean isVerbLemma = true;
        if(constructions.stream().anyMatch((construction) -> construction.toString().startsWith("COND"))) {
            // Conditionals

            boolean hasUnrealConditionals = constructions.stream().anyMatch((construction) -> construction == DetailedConstruction.CONDUNREAL);
            boolean hasRealConditionals = constructions.stream().anyMatch((construction) -> construction == DetailedConstruction.CONDREAL);
            String conditionalType = (hasUnrealConditionals && hasRealConditionals) ?
                    "conditional" :
                    hasRealConditionals ? "real conditional" : "unreal conditional";

            if(settings.getContentType().equals("FiB")) {
            	addLemmas = !settings.getBrackets().contains(BracketsProperties.LEMMA);
                String target = !addLemmas ?
                        "the verbs in brackets" :
                        "one of the following verbs. Each word may be used only once";

                instructions = "Insert the correct forms of " + target + " to form " + conditionalType + "sentences.";
            } else if(settings.getContentType().equals("Select")) {
                instructions = "Select the correct verb forms from the dropdowns to form " + conditionalType + "sentences.";
            } else if(settings.getContentType().endsWith("Drag")) {
                instructions = "Drag the verbs into the empty slots to form " + conditionalType + "sentences.";
            }
        } else if(constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ADJ") ||
                construction.toString().startsWith("ADV"))) {
            // Comparison

            boolean hasComparatives = constructions.stream().anyMatch((construction) -> construction.toString().contains("_COMP_"));
            boolean hasSuperlatives = constructions.stream().anyMatch((construction) -> construction.toString().contains("_SUP_"));
            String formType = (hasComparatives && hasSuperlatives) ?
                    "comparison" :
                    hasComparatives ? "comparative" : "superlative";

            if(settings.getContentType().equals("FiB")) {
                boolean hasAdjectives = constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ADJ"));
                boolean hasAdverbs = constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ADV"));

                String pos = (hasAdjectives && hasAdverbs) ?
                        "adjectives and adverbs" :
                        hasAdjectives ? "adjectives" : "adverbs";

                addLemmas = !settings.getBrackets().contains(BracketsProperties.LEMMA);
            	isVerbLemma = false;
                String target = !addLemmas ?
                        "the " + pos + " in brackets" :
                        "one of the following " + pos + ". Each word may only be used once";

                instructions = "Insert the correct " + formType + " forms of " + target + ".";
            } else if(settings.getContentType().equals("Select")) {
                instructions = "Select the correct " + formType + " forms from the dropdowns.";
            } else if(settings.getContentType().equals("Mark")) {
                instructions = "Click all " + formType + " forms in the text.";
            } else if(settings.getContentType().endsWith("Drag")) {
                instructions = "Drag the " + formType + " forms into the empty slots to form correct sentences.";
            }
        } else if(constructions.stream().anyMatch((construction) -> construction.toString().startsWith("PASSIVE") ||
                construction.toString().startsWith("ACTIVE"))) {
            // Passive
            if(settings.getContentType().equals("FiB")) {
                if(settings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
                    instructions = "Transform the active sentences given in brackets into passive sentences.";
                } else {
                    boolean containsActive = constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ACTIVE"));
                    String voice = containsActive ? "passive or active" : "passive";

                    instructions = "Insert the correct " + voice + " forms of the verbs in brackets.";

                    if (!settings.getBrackets().contains(BracketsProperties.TENSE)) {
                        ArrayList<String> containedTenses = new ArrayList<>();
                        ArrayList<Pair<String, String>> pastTenses = new ArrayList<>();
                        pastTenses.add(new Pair<>("simple past", "PASTSMP"));
                        pastTenses.add(new Pair<>("present perfect", "PRESPERF"));
                        pastTenses.add(new Pair<>("past perfect", "PASTPERF"));
                        pastTenses.add(new Pair<>("simple present", "PRESSMP"));
                        pastTenses.add(new Pair<>("future simple", "FUTSMP"));
                        pastTenses.add(new Pair<>("present progressive", "PRESPRG"));
                        pastTenses.add(new Pair<>("past progressive", "PASTPRG"));
                        pastTenses.add(new Pair<>("future progressive", "FUTPRG"));
                        pastTenses.add(new Pair<>("future perfect", "FUTPERF"));
                        pastTenses.add(new Pair<>("present perfect progressive", "PRESPERFPRG"));
                        pastTenses.add(new Pair<>("past perfect progressive", "PASTPERFPRG"));
                        pastTenses.add(new Pair<>("future perfect progressive", "FUTPERFPRG"));


                        for(Pair<String, String> pastTense : pastTenses) {
                            if(constructions.stream().anyMatch((construction) -> construction.toString().endsWith(pastTense.second))) {
                                containedTenses.add(pastTense.first);
                            }
                        }

                        String tense = String.join(", ", containedTenses);
                        int lastCommaIndex = tense.lastIndexOf(",");
                        if(lastCommaIndex != -1) {
                            tense = tense.substring(0, lastCommaIndex) + " or" + tense.substring(lastCommaIndex + 1);
                        }

                        instructions = " Use the correct tense (" + tense + ").";
                    }
                }
            } else if(settings.getContentType().endsWith("Drag")) {
                instructions = "Drag the words into the empty slots to form correct sentences.";
            }
        } else if(constructions.stream().anyMatch((construction) -> construction.toString().startsWith("QUEST") ||
                construction.toString().startsWith("STMT"))) {
            // Present
            String tense = "simple present";

            if(settings.getContentType().equals("FiB")) {
            	addLemmas = !settings.getBrackets().contains(BracketsProperties.LEMMA);
                String target = !addLemmas ?
                        "the verbs in brackets" :
                        "one of the following verbs. Each word may only be used once";

                instructions = "Insert the correct " + tense + " forms of " + target + ".";
            } else if(settings.getContentType().equals("Select")) {
                instructions = "Select the correct " + tense + " forms from the dropdowns.";
            } else if(settings.getContentType().equals("Mark")) {
                instructions = "Click all " + tense + " forms in the text.";
            }
        } else if(constructions.stream().anyMatch((construction) ->
                construction.toString().startsWith("PAST") || construction.toString().startsWith("PRES"))) {
            // Past
            ArrayList<String> containedTenses = new ArrayList<>();
            ArrayList<Pair<String, String>> pastTenses = new ArrayList<>();
            pastTenses.add(new Pair<>("simple past", "PASTSMP"));
            pastTenses.add(new Pair<>("present perfect", "PRESPERF"));
            pastTenses.add(new Pair<>("past perfect", "PASTPERF"));
            for(Pair<String, String> pastTense : pastTenses) {
                if(constructions.stream().anyMatch((construction) -> construction.toString().startsWith(pastTense.second))) {
                    containedTenses.add(pastTense.first);
                }
            }

            String tense = containedTenses.size() == 3 ?
                    "past tense" :
                    containedTenses.size() == 2 ?
                            containedTenses.get(0) + " or " + containedTenses.get(1) :
                            containedTenses.get(0);

            if(settings.getContentType().equals("FiB")) {
            	addLemmas = !settings.getBrackets().contains(BracketsProperties.LEMMA);
                String target = !addLemmas ?
                        "the verbs in brackets" :
                        "one of the following verbs. Each word may only be used once";

                instructions = "Insert the correct " + tense + " forms of " + target + ".";
            } else if(settings.getContentType().equals("Select")) {
                instructions = "Select the correct " + tense + " forms from the dropdowns.";
            } else if(settings.getContentType().equals("Mark")) {
                instructions = "Click all " + tense.replace(" or ", " and ") + " forms in the text.";
            } else if(settings.getContentType().endsWith("Drag")) {
                instructions = "Drag the " + tense.replace(" or ", " and ") + " forms into the empty slots to form correct sentences.";
            }
        } else if(constructions.stream().anyMatch((construction) ->
                construction == DetailedConstruction.WHO || construction == DetailedConstruction.WHICH ||
                        construction == DetailedConstruction.THAT || construction == DetailedConstruction.OTHERPRN)) {
            // Relative pronouns

            String specification = "";
            if(!constructions.stream().anyMatch((construction) -> construction == DetailedConstruction.OTHERPRN)) {
                ArrayList<String> containedPronouns = new ArrayList<>();
                ArrayList<Pair<String, String>> pronouns = new ArrayList<>();
                pronouns.add(new Pair<>("who", "WHO"));
                pronouns.add(new Pair<>("which", "WHICH"));
                pronouns.add(new Pair<>("that", "THAT"));

                for(Pair<String, String> pronoun : pronouns) {
                    if(constructions.stream().anyMatch((construction) -> construction.toString().equals(pronoun.second))) {
                        containedPronouns.add(pronoun.first);
                    }
                }

                specification = String.join(", ", containedPronouns);
                int lastCommaIndex = specification.lastIndexOf(",");
                if(lastCommaIndex != -1) {
                    specification = specification.substring(0, lastCommaIndex) + " or" + specification.substring(lastCommaIndex + 1);
                }
                specification = " (" + specification + ")";
            }

            if(settings.getContentType().equals("FiB")) {
                instructions = "Insert the correct relative pronouns" + specification + ".";
            } else if(settings.getContentType().equals("Select")) {
                instructions = "Select the correct relative pronouns from the dropdowns.";
            } else if(settings.getContentType().equals("Mark")) {
                instructions = "Click all relative pronouns in the text.";
            } else if(settings.getContentType().equals("MultiDrag")) {
                instructions = "Drag the words into the empty slots to form correct sentences.";
            } else if(settings.getContentType().equals("SingleDrag")) {
                instructions = "Drag the relative pronouns into the empty slots to form correct sentences.";
            }
        }
        
        if(addLemmas) {
        	ArrayList<String> lemmas = new ArrayList<>();
        	for(Construction construction : usedConstructions) {
            	String lemma = null;
                if(isVerbLemma) {
                	LemmatizedVerbCluster verbCluster = nlpManager.getLemmatizedVerbConstruction(construction.getOriginalConstructionIndices(), false, false);
                	if(verbCluster != null) {
                		if(verbCluster.getMainLemma() == null) {
                			if(verbCluster.getModal() == null) {
                				if(verbCluster.getLemmatizedCluster() == null) {
                            		lemma = construction.getBracketsText();
                				} else {
                					lemma = verbCluster.getLemmatizedCluster();
                				}
                			} else {
                				lemma = verbCluster.getModal();
                			}
                		} else {
                			lemma = verbCluster.getMainLemma();
                		}
                	}
                } else {
                	lemma = nlpManager.getLemmaOfComparison(construction.getOriginalConstructionIndices());
                }
                
            	if(lemma == null) {
            		lemmas.add(settings.getPlainText().substring(construction.getOriginalConstructionIndices().first, construction.getOriginalConstructionIndices().second));
            	} else {
            		lemmas.add(lemma);
            	}
            }
        	
        	if(lemmas.size() > 0) {
            	instructions += "</br><em>" + String.join("</em>, <em>", lemmas) + "</em>";
        	}
        }
                
        return instructions;
    }
}
