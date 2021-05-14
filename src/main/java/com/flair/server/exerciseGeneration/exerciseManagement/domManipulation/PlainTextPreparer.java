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
    public void prepareIndices(ExerciseSettings settings, NlpManager nlpManager) {
        Collections.sort(settings.getConstructions(),
                (c1, c2) -> c1.getConstructionIndices().first < c2.getConstructionIndices().first ? -1 : 1);

        Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> res = normalizePlainText(settings.getPlainText(), settings.getRemovedParts(),
                settings.getConstructions(), nlpManager);

        // Construction indices are modified in-place, we just need to set the sentences and sentence start indices
        settings.setSentences(res.second);
        settings.setSentenceStartIndices(res.first);
    }

    /**
     * Splits the plain text into sentences.
     * Saves the texts and start indices per sentence.
     * @return			The sentence indices and the split sentences in the normalized plain text
     */
    private Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> splitSentences(NlpManager nlpManager, String plainText, 
    		ArrayList<Pair<Integer, Integer>> removedParts) {
        ArrayList<Integer> sentenceStartIndices = new ArrayList<>();
        ArrayList<Pair<String, Boolean>> sentences = new ArrayList<>();
        
        
        for(SentenceAnnotations sent : nlpManager.getSentences()) {
        	if(sent.getTokens().size() > 0) {
	        	int sentenceStartIndex = sent.getTokens().get(0).beginPosition();
	        	int sentenceEndIndex = sent.getTokens().get(sent.getTokens().size() - 1).endPosition();
	        	String sentence = plainText.substring(sentenceStartIndex, sentenceEndIndex);
	        	
	        	// Sentence-final punctuation is sometimes added by the framework, so we can't rely on it existing in the HTML text
                if (sentence.substring(sentence.length() - 1).matches("[.;,]")) {
                    sentence = sentence.substring(0, sentence.length() - 1);
                }
                sentence = sentence.trim();
                if(sentence.length() > 0) {
                    sentenceStartIndices.add(sentenceStartIndex);
                    
                    // Split at displayed parts change
                    ArrayList<Integer> partsStartIndices = new ArrayList<>();
                    partsStartIndices.add(sentenceStartIndex);
                    for(Pair<Integer, Integer> removedPart : removedParts) {
                    	if(removedPart.first >= sentenceStartIndex && removedPart.first < sentenceStartIndex + sentence.length()) {
                    		partsStartIndices.add(removedPart.first);
                    	}
                    	if(removedPart.second > sentenceStartIndex && removedPart.second <= sentenceStartIndex + sentence.length()) {
                    		partsStartIndices.add(removedPart.second);
                    	}
                    }
                    
                    partsStartIndices.add(sentenceEndIndex);
                    for(int i = 0; i < partsStartIndices.size() - 1; i++) {
                    	int partStartIndex = partsStartIndices.get(i);
                    	int partEndIndex = partsStartIndices.get(i + 1);
                    	if(partEndIndex - partStartIndex > 0) {
                    		boolean display = true;
                    		for(Pair<Integer, Integer> removedPart : removedParts) {
                    			if(partStartIndex >= removedPart.first && partStartIndex < removedPart.second) {
                    				display = false;
                    				break;
                    			}
                    		}
                            sentences.add(new Pair<>(Normalizer.normalizeText(plainText.substring(partStartIndex, partEndIndex)), display));
                    	}
                    }                    
                }
        	}
        }

        return new Pair<>(sentenceStartIndices, sentences);
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
                                                   ArrayList<Integer> sentenceStartIndices) {
        int offset = 0;

        for(int i = 0; i < plainText.length();) {
            if(originalPlainText.charAt(i + offset) != plainText.charAt(i)) {
                for (int n = 0; n < constructionIndices.size(); n++) {
                    Pair<Integer, Integer> constructionIndex = constructionIndices.get(n);
                    if (constructionIndex.second > i) {
                        int newKey = constructionIndex.first > i ? constructionIndex.first - 1: constructionIndex.first;
                        constructionIndices.set(n, new Pair<Integer, Integer>(newKey, constructionIndex.second - 1));
                    }
                }
                for (int n = 0; n < sentenceStartIndices.size(); n++) {
                    int sentenceStartIndex = sentenceStartIndices.get(n);
                    if (sentenceStartIndex > i) {
                        sentenceStartIndices.set(n, sentenceStartIndex - 1);
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
    private Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> normalizePlainText(String plainText, 
    		ArrayList<Pair<Integer, Integer>> removedParts, ArrayList<Construction> constructions, NlpManager nlpManager) {
        ArrayList<Pair<Integer, Integer>> constructionIndices = new ArrayList<>();
        for(Construction c : constructions) {
            constructionIndices.add(c.getConstructionIndices());
        }

        Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> res = splitSentences(nlpManager, plainText, removedParts);

        String originalPlainText = Normalizer.normalizeWhitespaces(plainText);
        plainText = Normalizer.normalizeText(plainText.trim());

        matchIndicesToNormalizedPlainText(plainText, originalPlainText, constructionIndices, res.first);

        for(int i = 0; i < constructionIndices.size(); i++) {
            constructions.get(i).setConstructionIndices(constructionIndices.get(i));
        }

        return res;
    }

}
