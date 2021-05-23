package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class Indexer {

    /**
     * Determines the indices of fragments that can be matched to plain text fragments,
     * of sentence boundaries and of blanks boundaries in the HTML text
     * @param htmlText The HTML text of the DOM
     * @return Fragment, sentence and blanks indices
     */
    public ArrayList<Fragment> matchHtmlToPlainText(ExerciseSettings exerciseSettings, String htmlText, String plainText){
    	String originalHtml = Normalizer.normalizeWhitespaces(htmlText);
        htmlText = Normalizer.normalizeText(htmlText);
        ArrayList<Fragment> fragments = getUnambiguousFragments(htmlText, plainText);

        addSentenceFinalPunctuation(fragments, htmlText);
        fragments = splitDisplayedParts(fragments, exerciseSettings.getRemovedParts());	// needs to be done before sentence splitting to preserve the sentence indices
        fragments = splitSentences(fragments, exerciseSettings.getSentenceIndices());
        addBlanksIndicesToFragments(fragments, exerciseSettings.getConstructions());
        matchIndicesToNonNormalizedHtml(fragments, originalHtml, htmlText);
        removeIncompleteConstructions(fragments);
        trimBlanks(fragments, originalHtml);
        fragments = insertNotContainedFragments(fragments, originalHtml);

        return fragments;
    }
    
    /**
     * Splits the identified fragments at start and end indices of parts.
     * Can be used for splitting at sentence boundaries or for removed parts.
     * @param fragments		The identified fragments
     * @param parts			The normalized plain text indices of the parts
     * @return				The new list of fragments
     */
    private ArrayList<Fragment> splitFragmentsAtPartsBoundaries(ArrayList<Fragment> fragments, ArrayList<Pair<Integer, Integer>> parts) {
    	ArrayList<Fragment> newFragments = new ArrayList<>();
		
		for(Fragment fragment : fragments) {
			int newStartIndex = 0;
			for(Pair<Integer, Integer> part : parts) {
				int partStartIndex = part.first;
				int partEndIndex = part.second;
				int fragmentStartIndex = fragment.getPlainTextStartIndex();
				int fragmentEndIndex = fragmentStartIndex + fragment.getText().length();
				
				if(partStartIndex > fragmentStartIndex + newStartIndex && partStartIndex < fragmentEndIndex) {
					// we're entering a part, so the text before gets its own fragment
					String newText = fragment.getText().substring(newStartIndex, partStartIndex - fragmentStartIndex);
					int newStart = fragment.getStartIndex() + newStartIndex;
					newFragments.add(new Fragment(
							newText,
							newStart,
							newStart + newText.length(),
							fragment.getPlainTextStartIndex() + newStartIndex,
							fragment.isDisplay()));		
					newStartIndex = partStartIndex - fragmentStartIndex;
				}
				if(partEndIndex > fragmentStartIndex + newStartIndex && partEndIndex < fragmentEndIndex) {
					// we're leaving a part, so we create a new fragment for the previous text
					String newText = fragment.getText().substring(newStartIndex, partEndIndex - fragmentStartIndex);
					int newStart = fragment.getStartIndex() + newStartIndex;
					newFragments.add(new Fragment(
							newText,
							newStart,
							newStart + newText.length(),
							fragment.getPlainTextStartIndex() + newStartIndex,
							fragment.isDisplay()));
					newStartIndex = partEndIndex - fragmentStartIndex;
				}
			}
			
			// There's something after the last split
			if(newStartIndex < fragment.getPlainTextStartIndex() + fragment.getText().length()) {
				newFragments.add(new Fragment(
						fragment.getText().substring(newStartIndex),
						fragment.getStartIndex() + newStartIndex,
						fragment.getEndIndex(),
						fragment.getPlainTextStartIndex() + newStartIndex,
						fragment.isDisplay()));
			}
		}
		
		return newFragments;
    }
    
    /**
     * Splits the identified fragments if part of them is to be removed. Adds the removal information to the fragments.
     * @param fragments		The identified fragments
     * @param removedParts	The normalized plain text indices of the removed parts
     * @return				The new list of fragments
     */
    private ArrayList<Fragment> splitDisplayedParts(ArrayList<Fragment> fragments, ArrayList<Pair<Integer, Integer>> removedParts) {
		ArrayList<Fragment> newFragments = splitFragmentsAtPartsBoundaries(fragments, removedParts);
		addDisplayInformationToFragments(newFragments, removedParts);
		
		return newFragments;
    }
    
    /**
     * Adds display information to the fragments.
     * @param fragments		The fragments split at sentence boundaries
     * @param removedParts	The indices of the removed parts in the normalized plain text
     */
    private void addDisplayInformationToFragments(ArrayList<Fragment> fragments, ArrayList<Pair<Integer, Integer>> removedParts) {
    	for(int i = 0; i < removedParts.size(); i++) {
    		for(Fragment fragment : fragments) {
    			int fragmentStartIndex = fragment.getPlainTextStartIndex();
    			int fragmentEndIndex = fragmentStartIndex + fragment.getText().length();
    			int partStartIndex = removedParts.get(i).first;
    			int partEndIndex = removedParts.get(i).second;
    			
    			if(fragmentStartIndex >= partStartIndex && fragmentEndIndex <= partEndIndex) {
    				fragment.setDisplay(false);
    			}    			
			}
    	}
    }
    
    /**
     * Splits the identified fragments if part of them is to be removed. Adds the sentence boundary information to the fragments.
     * @param fragments		The identified fragments
     * @param sentences		The normalized plain text indices of the sentences
     * @return				The new list of fragments
     */
    private ArrayList<Fragment> splitSentences(ArrayList<Fragment> fragments, ArrayList<Pair<Integer, Integer>> sentences) {
		ArrayList<Fragment> newFragments = splitFragmentsAtPartsBoundaries(fragments, sentences);
		addSentenceIndicesToFragments(newFragments, sentences);
		
		return newFragments;
    }
    
    /**
     * Adds sentence start and sentence end information to the fragments.
     * @param fragments	The fragments split at sentence boundaries
     * @param sentences	The sentence indices in the normalized plain text
     */
    private void addSentenceIndicesToFragments(ArrayList<Fragment> fragments, ArrayList<Pair<Integer, Integer>> sentences) {
    	Collections.sort(fragments,
                (c1, c2) -> c1.getStartIndex() < c2.getStartIndex() ? -1 : 1);
    	Collections.sort(sentences,
                (c1, c2) -> c1.first < c2.first ? -1 : 1);
    	
    	for(int i = 0; i < sentences.size(); i++) {
    		Fragment potentialSentenceEnd = null;
    		boolean foundStart = false;
    		for(Fragment fragment : fragments) {
    			int fragmentStartIndex = fragment.getPlainTextStartIndex();
    			int fragmentEndIndex = fragmentStartIndex + fragment.getText().length();
    			int sentenceStartIndex = sentences.get(i).first;
    			int sentenceEndIndex = sentences.get(i).second;
    			
    			if(fragmentStartIndex >= sentenceStartIndex && fragmentEndIndex <= sentenceEndIndex) {
    				fragment.setSentenceIndex(i + 1);
    			}
    			if(!foundStart && fragment.isDisplay() && fragment.getPlainTextStartIndex() >= sentenceStartIndex) {
    				fragment.setSentenceStart(true);
    				foundStart = true;
    			}
				if(fragment.getPlainTextStartIndex() + fragment.getText().length() <= sentenceEndIndex) {
					if(fragment.isDisplay()) {
						potentialSentenceEnd = fragment;
					}
				} else {
					break;
				}
			}
    		// if we don't have a fragment for sentence end of the last sentence, we take the last displayed fragment
    		if(potentialSentenceEnd == null) {
    			int j = fragments.size() - 1;
    			while(j >= 0 && !fragments.get(j).isDisplay()) {
    				j--;
    			}
    			potentialSentenceEnd = fragments.get(j);
    		}
    		potentialSentenceEnd.setSentenceEnd(true);
    	}
    }

	/**
     * Inserts fragments for html text that is not contained in the plain text.
     * Such fragments can never have constructions, thus not be a sentence.
     * @param fragments The matched fragments corresponding to plain text fragments
     * @param htmlText  The original HTML text
     * @return          The list of fragments covering the entire HTMl text
     */
    private ArrayList<Fragment> insertNotContainedFragments(ArrayList<Fragment> fragments, String htmlText) {
        ArrayList<Fragment> newFragments = new ArrayList<>();

        int lastEndIndex = 0;
        for(int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if(fragment.getStartIndex() > lastEndIndex) {
                boolean display = fragment.isDisplay() && (i == 0 || fragments.get(i - 1).isDisplay());

                newFragments.add(new Fragment(lastEndIndex, fragment.getStartIndex(), display));
            }

            newFragments.add(fragment);
            lastEndIndex = fragment.getEndIndex();
        }

        if(lastEndIndex < htmlText.length()) {
        	boolean display = newFragments.size() > 0 && newFragments.get(newFragments.size() - 1).isDisplay();
            newFragments.add(new Fragment(lastEndIndex, htmlText.length(), display));
        }

        return newFragments;
    }
    
    /**
     * Adds trailing punctuation which were excluded from sentences for better matching
     * if they haven't been matched to another sentence.
     * @param fragments The identified plain text fragments
     * @param htmlText The normalized HTML text
     */
    private void addSentenceFinalPunctuation(ArrayList<Fragment> fragments, String htmlText) {
        for(int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if((i + 1 < fragments.size() && fragment.getEndIndex() < fragments.get(i + 1).getStartIndex() ||
                    i == fragments.size() - 1 && fragment.getEndIndex() < htmlText.length()) &&
                    (htmlText.charAt(fragment.getEndIndex()) + "").matches("[.;,]")) {
                fragment.setEndIndex(fragment.getEndIndex() + 1);
            }
        }
    }

    /**
     * Corrects the start and end indices of the fragments in the entire HTML text to fit the non-normalized HTML text
     * @param fragments The matched plain text fragments
     * @param originalHtml The non-normalized HTML text
     * @param normalizedHtml The normalized HTML text
     */
    private void matchIndicesToNonNormalizedHtml(ArrayList<Fragment> fragments, String originalHtml, String normalizedHtml) {
        int offset = 0;

        for(int i = 0; i < normalizedHtml.length();) {
            if(originalHtml.charAt(i + offset) != normalizedHtml.charAt(i)) {
                for (Fragment fragment : fragments) {
                    if (fragment.getStartIndex() >= i + offset) {
                        fragment.setStartIndex(fragment.getStartIndex() + 1);
                    }
                    if (fragment.getEndIndex() >= i + offset) {
                        fragment.setEndIndex(fragment.getEndIndex() + 1);
                    }
                    for(int k = 0; k < fragment.getBlanksBoundaries().size(); k++) {
                    	Blank blank = fragment.getBlanksBoundaries().get(k);
                        int blanksIndex = blank.getBoundaryIndex();
                        if (blanksIndex >= i + offset) {
                        	fragment.getBlanksBoundaries().get(k).setBoundaryIndex(blanksIndex + 1);
                        }
                    }
                }
                offset++;
            } else{
                i++;
            }
        }
    }

    /**
     * Removes leading and trailing whitespaces from constructions.
     * @param fragments The detected plain text fragments
     * @param htmlText The non-normalized HTML text.
     */
    private void trimBlanks(ArrayList<Fragment> fragments, String htmlText){
        for(Fragment fragment : fragments) {
            for(int k = 0; k < fragment.getBlanksBoundaries().size(); k++) {
            	Blank blank = fragment.getBlanksBoundaries().get(k);
                int blanksIndex = blank.getBoundaryIndex();
                boolean isStartBoundary = blank.getConstruction() == null;
                while(("" + htmlText.charAt(isStartBoundary ? blanksIndex : blanksIndex - 1)).matches("[\\s\\h]")) {
                    blanksIndex += isStartBoundary ? 1 : -1;
                }

                fragment.getBlanksBoundaries().get(k).setBoundaryIndex(blanksIndex);
            }
        }
    }

    /**
     * Adds the indices in the entire HTML text of contained blanks to fragments
     * @param fragments The fragments
     * @param plainTextIndices The blanks indices in the plain text
     */
    private void addBlanksIndicesToFragments(ArrayList<Fragment> fragments,
                                             ArrayList<Construction> plainTextIndices){
        for(Fragment fragment : fragments) {
            for(Construction plainTextConstruction : plainTextIndices) {
            	Pair<Integer, Integer> plainTextBlanks = plainTextConstruction.getConstructionIndices();
                int blanksStartIndex = plainTextBlanks.first;
                int blanksEndIndex = plainTextBlanks.second;
                int fragmentStartIndex = fragment.getPlainTextStartIndex();
                int fragmentEndIndex = fragmentStartIndex + fragment.getText().length();
                int htmlToPlainTextOffset = fragment.getStartIndex() - fragmentStartIndex;

                if(blanksStartIndex >= fragmentStartIndex && blanksStartIndex < fragmentEndIndex) {
                    fragment.getBlanksBoundaries().add(new Blank(blanksStartIndex + htmlToPlainTextOffset, plainTextIndices.indexOf(plainTextConstruction)));
                }
                if(blanksEndIndex > fragmentStartIndex && blanksEndIndex <= fragmentEndIndex) {
                    fragment.getBlanksBoundaries().add(new Blank(blanksEndIndex + htmlToPlainTextOffset, plainTextIndices.indexOf(plainTextConstruction), plainTextConstruction));
                }
            }
        }
    }
    
    /**
     * Removes constructions which are not entirely contained in a fragment.
     * @param fragments	The already merged fragments
     */
    private void removeIncompleteConstructions(ArrayList<Fragment> fragments) {
    	HashMap<Integer, ArrayList<Pair<Fragment, Blank>>> constructionBoundaries = new HashMap<> ();
    	for(Fragment fragment : fragments) {
    		for(Blank blank : fragment.getBlanksBoundaries()) {
    			if(!constructionBoundaries.containsKey(blank.getConstructionIndex())) {
    				constructionBoundaries.put(blank.getConstructionIndex(), new ArrayList<Pair<Fragment, Blank>>());
    			}
    			constructionBoundaries.get(blank.getConstructionIndex()).add(new Pair<>(fragment, blank));
    		}
    	}
    	
    	for(Entry<Integer, ArrayList<Pair<Fragment, Blank>>> entry : constructionBoundaries.entrySet()) {
    		if(entry.getValue().size() != 2) {
    			for(Pair<Fragment, Blank> pair : entry.getValue()) {
    				pair.first.getBlanksBoundaries().remove(pair.second);
    			}
    		}
    	}
    }

    /**
     * Identifies differences between the flair plain text and the HTML plain text.
     * Generates fragments with both indices.
     * @param htmlPlainText		The normalized HTML plain text
     * @param flairPlainText	The normalized FLAIR plain text
     * @return					The identified fragments
     */
    public ArrayList<Fragment> getUnambiguousFragments(String htmlPlainText, String flairPlainText) {
        EditScript<Character> es = new StringsComparator(htmlPlainText, flairPlainText).getScript();
        MyersCommandVisitor cv = new MyersCommandVisitor(flairPlainText);
        es.visit(cv);

        return cv.getFragments();
    }
    
}
