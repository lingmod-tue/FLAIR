package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

import edu.stanford.nlp.util.Pair;

public abstract class InstructionGenerator {

	public abstract void generateInstructions(ExerciseData data);
	
	protected String determineConditionalType(ArrayList<String> constructions) {
		boolean hasUnrealConditionals = constructions.stream().anyMatch((construction) -> 
				construction.equals(DetailedConstruction.CONDUNREAL) || construction.equals(DetailedConstruction.CONDUNREAL_MAIN) ||
				construction.equals(DetailedConstruction.CONDUNREAL_IF));
        boolean hasRealConditionals = constructions.stream().anyMatch((construction) -> 
        		construction.equals(DetailedConstruction.CONDREAL) || construction.equals(DetailedConstruction.CONDREAL_MAIN) ||
        		construction.equals(DetailedConstruction.CONDREAL_IF));
        return (hasUnrealConditionals && hasRealConditionals) ?
                "conditional" :
                hasRealConditionals ? "real conditional" : "unreal conditional";
	}
	
	protected String determineComparisonFormType(ArrayList<String> constructions) {
		boolean hasComparatives = constructions.stream().anyMatch((construction) -> construction.contains("_COMP_"));
        boolean hasSuperlatives = constructions.stream().anyMatch((construction) -> construction.contains("_SUP_"));
        return (hasComparatives && hasSuperlatives) ?
                "comparison" :
                hasComparatives ? "comparative" : "superlative";
	}
	
	protected String determineComparisonPos(ArrayList<String> constructions) {
		boolean hasAdjectives = constructions.stream().anyMatch((construction) -> construction.startsWith("ADJ"));
        boolean hasAdverbs = constructions.stream().anyMatch((construction) -> construction.startsWith("ADV"));

        return (hasAdjectives && hasAdverbs) ?
                "adjectives and adverbs" :
                hasAdjectives ? "adjectives" : "adverbs";
	}
	
	protected String determineVoice(ArrayList<String> constructions) {
		boolean containsActive = constructions.stream().anyMatch((construction) -> construction.startsWith("ACTIVE"));
        return containsActive ? "passive or active" : "passive";
	}
	
	protected String determineTenses(ArrayList<String> constructions, boolean onlyPast) {
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
            if(constructions.stream().anyMatch((construction) -> construction.endsWith(tense.second))) {
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
	
	protected String determineProgressive(ArrayList<String> constructions) {
		boolean hasProgressive = constructions.stream().anyMatch((construction) -> construction.contains("PRG_"));
    	boolean hasSimple = constructions.stream().anyMatch((construction) -> !construction.contains("PRG_"));

        return hasProgressive && hasSimple ? "simple or progressive " : 
        	hasProgressive ? "progressive " : "simple tense ";
	}
	
	protected String determinePronounList(ArrayList<String> constructions) {
		if(!constructions.stream().anyMatch((construction) -> construction.equals(DetailedConstruction.OTHERPRN))) {
            ArrayList<String> containedPronouns = new ArrayList<>();
            ArrayList<Pair<String, String>> pronouns = new ArrayList<>();
            pronouns.add(new Pair<>("who", DetailedConstruction.WHO));
            pronouns.add(new Pair<>("which", DetailedConstruction.WHICH));
            pronouns.add(new Pair<>("that", DetailedConstruction.THAT));

            for(Pair<String, String> pronoun : pronouns) {
                if(constructions.stream().anyMatch((construction) -> construction != null && construction.equals(pronoun.second))) {
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
		
	protected boolean determineAddLemmas(ExerciseData data) {
		return data.getInstructionLemmas().size() > 0;
	}
	
	protected boolean determineHasLemmasInBrackets(ExerciseData data) {
		return data.getBracketsProperties().contains(BracketsProperties.LEMMA);
	}
	
	protected ArrayList<String> determineConstructionTypes(ExerciseData data) {
		HashSet<String> types = new HashSet<>();
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				types.add(((ConstructionTextPart)part).getConstructionType());
			}
		}
		
		return new ArrayList<>(types);
	}
	
	protected int determineNConstructions(ExerciseData data) {
		int n = 0;
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				n++;
			}
		}
		
		return n;
	}
	
}