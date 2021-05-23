package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;


import java.util.ArrayList;
import java.util.Collections;

import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.SentenceAnnotations;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class PlainTextPreparer {

	/**
	 * Calculates the indices of sentences and blanks in the normalized plain text.
	 * @param settings	The exercise settings from the client
	 */
    public String prepareIndices(ExerciseSettings settings, NlpManager nlpManager) {
        Collections.sort(settings.getConstructions(),
                (c1, c2) -> c1.getConstructionIndices().first < c2.getConstructionIndices().first ? -1 : 1);

        Pair<ArrayList<Pair<Integer, Integer>>, String> res = normalizePlainText(settings.getPlainText(), settings.getRemovedParts(),
                        settings.getConstructions(), nlpManager);

        // Construction indices are modified in-place, we just need to set the sentences and sentence start indices
        settings.setSentenceIndices(res.first);
        return res.second;
    }

    /**
     * Splits the plain text into sentences.
     * Saves the texts and start indices per sentence.
     * @return			The sentence indices and the split sentences in the normalized plain text
     */
    private ArrayList<Pair<Integer,Integer>> splitSentences(NlpManager nlpManager, String plainText, ArrayList<Pair<Integer, Integer>> removedParts, 
    		ArrayList<Construction> constructions) {
    	//TODO: split sentences only if they contain a construction

        ArrayList<Pair<Integer, Integer>> sentenceIndices = new ArrayList<>();        

        for(SentenceAnnotations sent : nlpManager.getSentences()) {
        	if(sent.getTokens().size() > 0) {
	        	int sentenceStartIndex = sent.getTokens().get(0).beginPosition();
	        	int sentenceEndIndex = sent.getTokens().get(sent.getTokens().size() - 1).endPosition();
	        	
                if(sentenceEndIndex - sentenceStartIndex > 0) {  
                	// we only extract sentences which contain at least 1 construction
                	boolean containsConstruction = false;
                    for(Construction construction : constructions) {
                    	if(construction.getConstructionIndices().first >= sentenceStartIndex && construction.getConstructionIndices().second < sentenceEndIndex) {
                    		containsConstruction = true;
                    		break;
                    	}
                    }
                    if(containsConstruction) {
                    	sentenceIndices.add(new Pair<>(sentenceStartIndex, sentenceEndIndex));
                    }                
                }
        	}
        }

        return sentenceIndices;
    }

    /**
     * Corrects the start and end indices of the constructions and the sentence start indices in the plain text
     * to fit the non-normalized plain text.
     * @param plainText 			The normalized plain text
     * @param originalPlainText		The original plain text
     * @param constructionIndices	The construction indices in the original plain text
     * @param sentenceStartIndices	The sentence indices in the original plain text
     */
    private void matchIndicesToNormalizedPlainText(String plainText, String originalPlainText,
                                                   ArrayList<Pair<Integer, Integer>> constructionIndices,
                                                   ArrayList<Pair<Integer, Integer>> sentenceIndices,
                                                   ArrayList<Pair<Integer, Integer>> removedParts) {
        int offset = 0;

        for(int i = 0; i < plainText.length();) {
            if(originalPlainText.charAt(i + offset) != plainText.charAt(i)) {
                for (int n = 0; n < constructionIndices.size(); n++) {
                    Pair<Integer, Integer> constructionIndex = constructionIndices.get(n);
                    if (constructionIndex.second > i) {
                        int newKey = constructionIndex.first > i ? constructionIndex.first - 1: constructionIndex.first;
                        constructionIndices.set(n, new Pair<>(newKey, constructionIndex.second - 1));
                    }
                }
                for (int n = 0; n < sentenceIndices.size(); n++) {
                	Pair<Integer, Integer> sentenceIndex = sentenceIndices.get(n);
                    if (sentenceIndex.second > i) {
                        int newKey = sentenceIndex.first > i ? sentenceIndex.first - 1: sentenceIndex.first;
                        sentenceIndices.set(n, new Pair<>(newKey, sentenceIndex.second - 1));
                    }
                }
                for (int n = 0; n < removedParts.size(); n++) {
                	Pair<Integer, Integer> removedPartIndex = removedParts.get(n);
                	if (removedPartIndex.second > i) {
                		int newKey = removedPartIndex.first > i ? removedPartIndex.first - 1: removedPartIndex.first;
                		removedParts.set(n, new Pair<>(newKey, removedPartIndex.second - 1));
                	}
                }
                offset++;
            } else{
                i++;
            }
        }
    }

    /**
     * Normalizes whitespace characters and punctuation in the plain text to make matching to the HTML text possible.
     * @param plainText 			The original plain text
     * @param removedParts			The start and end indices of the parts removed in the UI
     * @param constructions			The blanks
     * @return						The sentence indices and the split sentences in the normalized plain text
     */
    private Pair<ArrayList<Pair<Integer, Integer>>, String> normalizePlainText(String plainText, 
    		ArrayList<Pair<Integer, Integer>> removedParts, ArrayList<Construction> constructions, NlpManager nlpManager) {
        ArrayList<Pair<Integer, Integer>> constructionIndices = new ArrayList<>();
        for(Construction c : constructions) {
            constructionIndices.add(c.getConstructionIndices());
        }

        ArrayList<Pair<Integer,Integer>> sentenceIndices = splitSentences(nlpManager, plainText, removedParts, constructions);

        String originalPlainText = Normalizer.normalizeWhitespaces(plainText);
        String normalizedPlainTextString = Normalizer.normalizeText(plainText.trim());

        matchIndicesToNormalizedPlainText(normalizedPlainTextString, originalPlainText, constructionIndices, sentenceIndices, removedParts);

        for(int i = 0; i < constructionIndices.size(); i++) {
            constructions.get(i).setConstructionIndices(constructionIndices.get(i));
        }

        return new Pair<>(sentenceIndices, normalizedPlainTextString);
    }

}
