package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.SentenceAnnotations;
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
    public void matchHtmlToPlainText(ExerciseSettings exerciseSettings, WebpageData data, NlpManager nlpManager){
    	Collections.sort(exerciseSettings.getConstructions(),
                (c1, c2) -> c1.getConstructionIndices().first < c2.getConstructionIndices().first ? -1 : 1);
    	
    	String htmlText = Normalizer.normalizeWhitespaces(data.getDocument().wholeText());
    	String plainText = Normalizer.normalizeWhitespaces(exerciseSettings.getPlainText());
        ArrayList<Fragment> fragments = getUnambiguousFragments(htmlText, plainText);

        fragments = splitDisplayedParts(fragments, exerciseSettings.getRemovedParts());	// needs to be done before sentence splitting to preserve the sentence indices
        fragments = splitSentences(fragments, nlpManager, exerciseSettings.getConstructions());
        addBlanksIndicesToFragments(fragments, exerciseSettings.getConstructions());
        removeIncompleteConstructions(fragments);
        trimBlanks(fragments, htmlText);
        Pair<Integer, Integer> textIndices = determineTextBoundaries(fragments, htmlText, plainText, exerciseSettings.isOnlyText());
        fragments = insertNotContainedFragments(fragments, htmlText);

        data.setFragments(fragments);
        data.setTextIndices(textIndices);
    }
    
    /**
     * Determines the indices in the HTML plain text of the first and last characters of the FLAIR plain text.
     * @param fragments		The extracted fragments
     * @param htmlText		The HTML plain text
     * @param plainText		The FLAIR plain text
     * @param getBoundaries	<c>true</c> if the boundary indices are to be determined; otherwise <c>fasle</c>
     * @return				The start and end indices of the FLAIR plain text in the HTMl plain text; null for either if their could not be defined
     */
    private Pair<Integer, Integer> determineTextBoundaries(ArrayList<Fragment> fragments, String htmlText, String plainText, boolean getBoundaries) {
    	Integer startIndex = null;
        Integer endIndex = null;
        
        if(getBoundaries) {
	        if(fragments.size() > 0) {
	            if (fragments.get(0).getPlainTextStartIndex() == 0) {
	                startIndex = fragments.get(0).getStartIndex();
	            }
	            Fragment lastFragment = fragments.get(fragments.size() - 1);
	            if(lastFragment.getPlainTextStartIndex() + lastFragment.getText().length() == plainText.length()){
	                endIndex = lastFragment.getEndIndex();
	            }
	        }
	        if(startIndex == null) {
	            Pattern pattern = Pattern.compile("[\\s\\h]");
	            Matcher matcher = pattern.matcher(htmlText);
	            if(matcher.find()){
	                startIndex = matcher.start();
	            }
	        }
	        if(endIndex == null && htmlText.length() > 0) {
	            endIndex = htmlText.length();
	            while((htmlText.charAt(endIndex - 1) + "").matches("[\\s\\h]")) {
	                endIndex--;
	            }
	        }
	        if(endIndex != null && endIndex > 0) {
	            endIndex --;
	        } else {
	            endIndex = null;
	        }
        }
        
        return new Pair<>(startIndex, endIndex);
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
    private ArrayList<Fragment> splitSentences(ArrayList<Fragment> fragments, NlpManager nlpManager, ArrayList<Construction> constructions) {
    	ArrayList<Pair<Integer, Integer>> sentences = getSentenceBoundaries(nlpManager, constructions);
		ArrayList<Fragment> newFragments = splitFragmentsAtPartsBoundaries(fragments, sentences);
		addSentenceIndicesToFragments(newFragments, sentences);
		
		return newFragments;
    }
    
    /**
     * Splits the plain text into sentences.
     * Saves the texts and start indices per sentence.
     * @return			The sentence indices and the split sentences in the normalized plain text
     */
    private ArrayList<Pair<Integer,Integer>> getSentenceBoundaries(NlpManager nlpManager, ArrayList<Construction> constructions) {
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
