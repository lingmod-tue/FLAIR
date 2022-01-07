package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseTopic;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

import edu.stanford.nlp.util.Pair;

public class InstructionGenerator {

	public void generateInstructions(ExerciseData data, ExerciseType type) {
		ExerciseTopic topic = data.getTopic();
		if(type == ExerciseType.CATEGORIZE) {
			if(topic == ExerciseTopic.CONDITIONALS) {
				data.setInstructions("Read the sentences and decide if they are type 1 or type 2.");
			} 
		} else if(type == ExerciseType.FIB) {
			if(topic == ExerciseTopic.COMPARISON) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String formType = determineComparisonFormType(constructionTypes);
                String pos = determineComparisonPos(constructionTypes);
            	boolean addLemmas = determineAddLemmas(data);
            	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
                String target = !addLemmas ?
                		(hasLemmasInBrackets ? ("the " + pos + " in brackets") : ("the " + pos)) :
                        "one of the following " + pos + ". Each word may only be used once";
                
				data.setInstructions("Insert the correct " + formType + " forms of " + target + ".");
			} else if(topic == ExerciseTopic.CONDITIONALS) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String conditionalType = determineConditionalType(constructionTypes);
            	boolean addLemmas = determineAddLemmas(data);
            	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
                String target = !addLemmas ?
                        (hasLemmasInBrackets ? "the verbs in brackets" : "the verbs") :
                        "one of the following verbs. Each word may be used only once";
				data.setInstructions("Insert the correct forms of " + target + " to form " + conditionalType + "sentences.");

	            
	            
				String instructions;
				if(data.getBracketsProperties().contains(BracketsProperties.DISTRACTOR_LEMMA) || 
						data.getInstructionProperties().contains(InstructionsProperties.LEMMA)) {
					instructions = "Read each sentence. Which verb fits? Fill in the gap with the correct form of the verb.";
				} else if(data.getBracketsProperties().contains(BracketsProperties.ACTIVE_SENTENCE)) {
					instructions = "Use the verbs in brackets to form a if-clause.";
				} else {
					instructions = "Fill in the gap to form a correct sentence.";
				}
				
				data.setInstructions(instructions);
			} else if(topic == ExerciseTopic.PASSIVE) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
                String voice = determineVoice(constructionTypes);

                String instructions = "Insert the correct " + voice + " forms of the verbs in brackets.";

                if (data.getInstructionProperties().contains(InstructionsProperties.TENSE)) {
                    String tense = determineTenses(constructionTypes, false);
                    instructions += " Use the correct tense (" + tense + ").";
                }

				data.setInstructions(instructions);
			} else if(topic == ExerciseTopic.PRESENT) {
				boolean addLemmas = determineAddLemmas(data);
            	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
                String target = !addLemmas ?
                		(hasLemmasInBrackets ? "the verbs in brackets" : "the verbs") :
                        "one of the following verbs. Each word may only be used once";
	            
				data.setInstructions("Insert the correct simple present forms of " + target + ".");
			} else if(topic == ExerciseTopic.PAST) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
	            boolean addLemmas = determineAddLemmas(data);
            	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
                String target = !addLemmas ?
                        (hasLemmasInBrackets ? "the verbs in brackets" : "the verbs") :
                        "one of the following verbs. Each word may only be used once";                           
                String progressiveString = data.getInstructionProperties().contains(InstructionsProperties.PROGRESSIVE) ? 
                		determineProgressive(constructionTypes) : 
                			"";
                                
                String instructions = "Insert the correct " + progressiveString + "forms of " + target + ".";
             
                if (data.getInstructionProperties().contains(InstructionsProperties.TENSE)) {
    	            String tense = determineTenses(constructionTypes, true);
                    instructions += " Use the correct tense (" + tense + ").";
                }               

				data.setInstructions(instructions);
			} else if(topic == ExerciseTopic.RELATIVES) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
                String specification = determinePronounList(constructionTypes);
                if(specification.length() > 0) {
                	specification = "(" + specification + ")";
                }

				data.setInstructions("Insert the correct relative pronouns" + specification + ".");
			}
		} else if(type == ExerciseType.SINGLE_CHOICE) {
			if(topic == ExerciseTopic.COMPARISON) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String formType = determineComparisonFormType(constructionTypes);
				data.setInstructions("Pick the correct " + formType + " form for each gap.");			
			} else if(topic == ExerciseTopic.CONDITIONALS) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String conditionalType = determineConditionalType(constructionTypes);
				data.setInstructions("Pick the correct verb form for each gap to form " + conditionalType + "sentences.");

	            
	            
				data.setInstructions("Pick the correct answer for each gap.");
			} else if(topic == ExerciseTopic.PRESENT) {
				data.setInstructions("Pick the correct simple present form for each gap.");
			} else if(topic == ExerciseTopic.PAST) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
	            String tense = determineTenses(constructionTypes, true);	 	            	            
				data.setInstructions("Pick the correct " + tense + " form for each gap.");
			} else if(topic == ExerciseTopic.RELATIVES) {
				data.setInstructions("Pick the correct relative pronoun for each gap.");
			}
		} else if(type == ExerciseType.DRAG_SINGLE) {
			if(topic == ExerciseTopic.CONDITIONALS) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String conditionalType = determineConditionalType(constructionTypes);
				data.setInstructions("Drag the items into the correct gap to form " + conditionalType + "sentences.");
				
				data.setInstructions("Drag the items into the correct gap.");
			} else if(topic == ExerciseTopic.PASSIVE) {
				data.setInstructions("Drag the items into the correct gap to form correct sentences.");
			} else if(topic == ExerciseTopic.PAST) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
	            String tense = determineTenses(constructionTypes, true);	            
				data.setInstructions("Drag the " + tense.replace(" or ", " and ") + " forms into the empty slots to form correct sentences.");
			} else if(topic == ExerciseTopic.RELATIVES) {				
				data.setInstructions("Drag the relative pronouns into the correct gap to form correct sentences.");
			}
		} else if(type == ExerciseType.DRAG_MULTI) {
			if(topic == ExerciseTopic.COMPARISON) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String formType = determineComparisonFormType(constructionTypes);
				data.setInstructions("Drag the " + formType + " forms into the correct gap to form correct sentences.");
			} else if(topic == ExerciseTopic.CONDITIONALS) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String conditionalType = determineConditionalType(constructionTypes);
				data.setInstructions("Drag the items into the correct gap to form " + conditionalType + "sentences.");
			} else if(topic == ExerciseTopic.PASSIVE) {
				data.setInstructions("Drag the words into the correct gap to form correct sentences.");
			} else if(topic == ExerciseTopic.PAST) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
	            String tense = determineTenses(constructionTypes, true);
				data.setInstructions("Drag the " + tense.replace(" or ", " and ") + " forms into the empty slots to form correct sentences.");
			} else if(topic == ExerciseTopic.RELATIVES) {
				data.setInstructions("Drag the words into the correct gap to form correct sentences.");
			}
		} else if(type == ExerciseType.JUMBLED_SENTENCES) {
			data.setInstructions("Put the parts of a sentence into a correct order.");
		} else if(type == ExerciseType.MARK) {
			if(topic == ExerciseTopic.COMPARISON) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String formType = determineComparisonFormType(constructionTypes);

                String instructions = "Click all " + formType + " forms in the text.";
                if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
                	instructions += " The text contains " + determineNConstructions(data) + " " + formType + " forms.";
                }

				data.setInstructions(instructions);				
			} else if(topic == ExerciseTopic.CONDITIONALS) {
				data.setInstructions("Underline the verb in the if clause.");
			} else if(topic == ExerciseTopic.PRESENT) {
	            String instructions = "Click all simple present forms in the text.";
	            if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
                	instructions += " The text contains " + determineNConstructions(data) + " simple present forms.";
                }

				data.setInstructions(instructions);	
			} else if(topic == ExerciseTopic.PAST) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
	            String tense = determineTenses(constructionTypes, true);

	            String instructions = "Click all " + tense.replace(" or ", " and ") + " forms in the text.";
	            if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
                	instructions += " The text contains " + determineNConstructions(data) + " " + " forms to find.";
                }
	            
				data.setInstructions(instructions);
			} else if(topic == ExerciseTopic.RELATIVES) {
                String instructions = "Click all relative pronouns in the text.";
                if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
                	instructions += " The text contains " + determineNConstructions(data) + " relative pronouns.";
                }
				
				data.setInstructions(instructions);
			}
		} else if(type == ExerciseType.MEMORY) {
			if(topic == ExerciseTopic.COMPARISON) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
				String formType = determineComparisonFormType(constructionTypes);
				data.setInstructions("Find the matching " + formType + " and base forms.");
			} else if(topic == ExerciseTopic.CONDITIONALS) {
				data.setInstructions("Find pairs.");
			} else if(topic == ExerciseTopic.PRESENT) {
				data.setInstructions("Find the matching simple present and base forms.");
			} else if(topic == ExerciseTopic.PAST) {
				ArrayList<DetailedConstruction> constructionTypes = determineConstructionTypes(data);
	            String tense = determineTenses(constructionTypes, true);
				data.setInstructions("Find the matching " + tense + " and base forms.");
			}
		}
	}
	
	private String determineConditionalType(ArrayList<DetailedConstruction> constructions) {
		boolean hasUnrealConditionals = constructions.stream().anyMatch((construction) -> 
				construction == DetailedConstruction.CONDUNREAL || construction == DetailedConstruction.CONDUNREAL_MAIN ||
				construction == DetailedConstruction.CONDUNREAL_IF);
        boolean hasRealConditionals = constructions.stream().anyMatch((construction) -> 
        		construction == DetailedConstruction.CONDREAL || construction == DetailedConstruction.CONDREAL_MAIN ||
        		construction == DetailedConstruction.CONDREAL_IF);
        return (hasUnrealConditionals && hasRealConditionals) ?
                "conditional" :
                hasRealConditionals ? "real conditional" : "unreal conditional";
	}
	
	private String determineComparisonFormType(ArrayList<DetailedConstruction> constructions) {
		boolean hasComparatives = constructions.stream().anyMatch((construction) -> construction.toString().contains("_COMP_"));
        boolean hasSuperlatives = constructions.stream().anyMatch((construction) -> construction.toString().contains("_SUP_"));
        return (hasComparatives && hasSuperlatives) ?
                "comparison" :
                hasComparatives ? "comparative" : "superlative";
	}
	
	private String determineComparisonPos(ArrayList<DetailedConstruction> constructions) {
		boolean hasAdjectives = constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ADJ"));
        boolean hasAdverbs = constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ADV"));

        return (hasAdjectives && hasAdverbs) ?
                "adjectives and adverbs" :
                hasAdjectives ? "adjectives" : "adverbs";
	}
	
	private String determineVoice(ArrayList<DetailedConstruction> constructions) {
		boolean containsActive = constructions.stream().anyMatch((construction) -> construction.toString().startsWith("ACTIVE"));
        return containsActive ? "passive or active" : "passive";
	}
	
	private String determineTenses(ArrayList<DetailedConstruction> constructions, boolean onlyPast) {
        ArrayList<Pair<String, String>> tenses = new ArrayList<>();
        tenses.add(new Pair<>("simple past", "PASTSMP"));
        tenses.add(new Pair<>("present perfect", "PRESPERF"));
        tenses.add(new Pair<>("past perfect", "PASTPERF"));
        
        if(!onlyPast) {
	        tenses.add(new Pair<>("simple present", "PRESSMP"));
	        tenses.add(new Pair<>("future simple", "FUTSMP"));
	        tenses.add(new Pair<>("present progressive", "PRESPRG"));
	        tenses.add(new Pair<>("past progressive", "PASTPRG"));
	        tenses.add(new Pair<>("future progressive", "FUTPRG"));
	        tenses.add(new Pair<>("future perfect", "FUTPERF"));
	        tenses.add(new Pair<>("present perfect progressive", "PRESPERFPRG"));
	        tenses.add(new Pair<>("past perfect progressive", "PASTPERFPRG"));
	        tenses.add(new Pair<>("future perfect progressive", "FUTPERFPRG"));
	    }

		ArrayList<String> containedTenses = new ArrayList<>();
        for(Pair<String, String> tense : tenses) {
            if(constructions.stream().anyMatch((construction) -> construction.toString().endsWith(tense.second))) {
                containedTenses.add(tense.first);
            }
        }

        String tense = String.join(", ", containedTenses);
        int lastCommaIndex = tense.lastIndexOf(",");
        if(lastCommaIndex != -1) {
            tense = tense.substring(0, lastCommaIndex) + " or" + tense.substring(lastCommaIndex + 1);
        }
        
        return tense;
	}
	
	private String determineProgressive(ArrayList<DetailedConstruction> constructions) {
		boolean hasProgressive = constructions.stream().anyMatch((construction) -> construction.toString().contains("PRG_"));
    	boolean hasSimple = constructions.stream().anyMatch((construction) -> !construction.toString().contains("PRG_"));

        return hasProgressive && hasSimple ? "simple or progressive " : 
        	hasProgressive ? "progressive " : "simple tense ";
	}
	
	private String determinePronounList(ArrayList<DetailedConstruction> constructions) {
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

            String ret = String.join(", ", containedPronouns);
            int lastCommaIndex = ret.lastIndexOf(",");
            if(lastCommaIndex != -1) {
            	ret = ret.substring(0, lastCommaIndex) + " or" + ret.substring(lastCommaIndex + 1);
            }
            
            return ret;
        }
		
		return "";
	}
		
	private boolean determineAddLemmas(ExerciseData data) {
		return data.getInstructionLemmas().size() > 0;
	}
	
	private boolean determineHasLemmasInBrackets(ExerciseData data) {
		return data.getBracketsProperties().contains(BracketsProperties.LEMMA);
	}
	
	private ArrayList<DetailedConstruction> determineConstructionTypes(ExerciseData data) {
		HashSet<DetailedConstruction> types = new HashSet<>();
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				types.add(((ConstructionTextPart)part).getConstructionType());
			}
		}
		
		return new ArrayList<>(types);
	}
	
	private int determineNConstructions(ExerciseData data) {
		int n = 0;
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				n++;
			}
		}
		
		return n;
	}
	
}
