package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;

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
    public ArrayList<Fragment> matchHtmlToPlainText(ExerciseSettings exerciseSettings, String htmlText){
        ArrayList<Pair<String, Boolean>> sentences = exerciseSettings.getSentences();

        String originalHtml = Normalizer.normalizeWhitespaces(htmlText);
        htmlText = Normalizer.normalizeText(htmlText);
        ArrayList<Fragment> fragments = new ArrayList<>();

        // initialize all sentences as unresolved fragments with entire html text as search space
        for(int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i).first;
            Fragment newFragment = new Fragment(sentence, 0, htmlText.length(), i, false,
                    exerciseSettings.getSentenceStartIndices().get(i), sentences.get(i).second);
            fragments.add(newFragment);
        }

        fragments = recheckMatches(fragments, htmlText);
        addSentenceFinalPunctuation(fragments, htmlText);
        addBlanksIndicesToFragments(fragments, exerciseSettings.getConstructions());
        matchIndicesToNonNormalizedHtml(fragments, originalHtml, htmlText);
        fragments = mergeFragments(fragments, originalHtml);
        trimBlanks(fragments, originalHtml);
        fragments = insertNotContainedFragments(fragments, originalHtml);

        return fragments;
    }

    /**
     * Inserts fragments for html text that is not contained in the plain text.
     * Collapse adjoining fragments without constructions into a single sentence.
     * @param fragments The matched fragments corresponding to plain text fragments
     * @param htmlText  The original HTML text
     * @return          The list of fragments covering the entire HTMl text
     */
    private ArrayList<Fragment> insertNotContainedFragments(ArrayList<Fragment> fragments, String htmlText) {
        ArrayList<Fragment> newFragments = new ArrayList<>();

        int lastEndIndex = 0;
        int sentenceIndex = 0;
        for(int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if(fragment.getStartIndex() > lastEndIndex) {
                boolean display = fragment.isDisplay() && (i == 0 || fragments.get(i - 1).isDisplay());
                boolean isSentenceStart = i == 0 || newFragments.get(newFragments.size() - 1).isSentenceEnd();

                if(newFragments.size() > 0 && display != newFragments.get(newFragments.size() - 1).isDisplay()) {
                    newFragments.get(newFragments.size() - 1).setSentenceEnd(true);
                    isSentenceStart = true;
                }
                if(isSentenceStart) {
                    sentenceIndex++;
                }

                boolean isSentenceEnd = fragment.getBlanksBoundaries().size() > 0 || display != fragment.isDisplay();

                newFragments.add(new Fragment(lastEndIndex, fragment.getStartIndex(), isSentenceStart, isSentenceEnd,
                        sentenceIndex, display));
            }
            fragment.setSentenceIndex(++i);
            boolean isSentenceStart = newFragments.size() == 0 || newFragments.get(newFragments.size() - 1).isSentenceEnd();
            if(isSentenceStart) {
                sentenceIndex++;
            }
            fragment.setSentenceStart(isSentenceStart);
            fragment.setSentenceEnd(fragment.isSentenceEnd() && fragment.getBlanksBoundaries().size() > 0);
            fragment.setSentenceIndex(sentenceIndex);

            newFragments.add(fragment);
            lastEndIndex = fragment.getEndIndex();
        }

        if(lastEndIndex < htmlText.length()) {
            boolean isSentenceStart = newFragments.size() == 0 || newFragments.get(newFragments.size() - 1).isSentenceEnd();
            if(isSentenceStart) {
                sentenceIndex++;
            }
            newFragments.add(new Fragment(lastEndIndex, htmlText.length(), isSentenceStart, true,
                    sentenceIndex, newFragments.size() > 0 && newFragments.get(newFragments.size() - 1).isDisplay()));
        }

        if(newFragments.size() > 0) {
            newFragments.get(newFragments.size() - 1).setSentenceEnd(true);
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
        boolean isStartBoundary = true;
        for(Fragment fragment : fragments) {
            for(int k = 0; k < fragment.getBlanksBoundaries().size(); k++) {
            	Blank blank = fragment.getBlanksBoundaries().get(k);
                int blanksIndex = blank.getBoundaryIndex();
                while(("" + htmlText.charAt(isStartBoundary ? blanksIndex : blanksIndex - 1)).matches("[\\s\\h]")) {
                    blanksIndex += isStartBoundary ? 1 : -1;
                }

                fragment.getBlanksBoundaries().get(k).setBoundaryIndex(blanksIndex);
                isStartBoundary = !isStartBoundary;
            }
        }
    }

    /**
     * Merges the fragments of the same sentence into 1 fragment.
     * Discards all fragments that couldn't be matched unambiguously.
     * @param fragments The fragments into which the plain text was split with corresponding indices in the HTML text
     * @param htmlText The HTML string
     * @return The merged and filtered fragments
     */
    private ArrayList<Fragment> mergeFragments(ArrayList<Fragment> fragments, String htmlText) {
        ArrayList<Fragment> validFragments = new ArrayList<>();
        Fragment previousFragment = null;
        for(Fragment fragment : fragments) {
            if(fragment.isUnambiguousMatch()) {
                if(previousFragment != null) {
                    if (previousFragment.getSentenceIndex() == fragment.getSentenceIndex()) {
                        previousFragment.getBlanksBoundaries().addAll(fragment.getBlanksBoundaries());
                        previousFragment = new Fragment(
                                htmlText.substring(previousFragment.getStartIndex(), fragment.getEndIndex()),
                                previousFragment.getStartIndex(),
                                fragment.getEndIndex(),
                                fragment.getSentenceIndex(),
                                previousFragment.getPlainTextStartIndex(),
                                true,
                                previousFragment.getBlanksBoundaries(),
                                previousFragment.isDisplay());
                    } else {
                        validFragments.add(previousFragment);
                        previousFragment = fragment;
                    }
                } else {
                    fragment.setSentenceStart(true);
                    previousFragment = fragment;
                }
            }
        }
        if(previousFragment != null) {
            validFragments.add(previousFragment);
        }

        for(Fragment fragment : validFragments) {
            // all new Fragments are both sentence start and sentence end
            fragment.setSentenceStart(true);
            fragment.setSentenceEnd(true);
        }

        return validFragments;
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
                    fragment.getBlanksBoundaries().add(new Blank(blanksStartIndex + htmlToPlainTextOffset));
                }
                if(blanksEndIndex > fragmentStartIndex && blanksEndIndex <= fragmentEndIndex) {
                    fragment.getBlanksBoundaries().add(new Blank(blanksEndIndex + htmlToPlainTextOffset, plainTextConstruction.getBracketsText(), plainTextIndices.indexOf(plainTextConstruction)));
                }
            }
        }
    }

    /**
     * Recursively tries to match fragments and parts thereof against their search window.
     * The search window is adjusted as neighbouring fragments are determined.
     * @param fragments The current splitting of the plain text with markings whether they have been matched successfully
     * @param htmlText The HTML text
     * @return The fragments into which the plain text has been split
     */
    private ArrayList<Fragment> recheckMatches(ArrayList<Fragment> fragments, String htmlText) {
        boolean foundMatch = true;
        boolean allSentencesDone = false;

        while(!allSentencesDone && foundMatch) {
            foundMatch = false;
            allSentencesDone = true;
            ArrayList<Fragment> newFragments = new ArrayList<>();

            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (!fragment.isUnambiguousMatch()) {
                    Integer startIndex = getEndOfPreviousUnambiguousFragment(newFragments);
                    Integer endIndex = getStartOfNextUnambiguousFragment(i, fragments);

                    if (startIndex == null) {
                        startIndex = 0;
                    }
                    if (endIndex == null) {
                        endIndex = htmlText.length();
                    }

                    Pair<Boolean, Boolean> res = matchFragment(startIndex, endIndex, htmlText, fragment.getText(),
                            fragment.getSentenceIndex(), fragment.getPlainTextStartIndex(), newFragments,
                            foundMatch, allSentencesDone, fragment.isDisplay());
                    foundMatch = res.first;
                    allSentencesDone = res.second;
                } else {
                    newFragments.add(fragment);
                }
            }

            fragments = newFragments;
        }

        return fragments;
    }

    /**
     * Recursively matches parts of a plain text to the HTML text until the end of the plain text fragment is reached.
     * @param startIndex The index in the entire HTML text at which we want to start the mapping (to reduce the search space)
     * @param endIndex The index in the entire HTML text until which we search mappings (to reduce the search space)
     * @param htmlText The entire HTML text
     * @param fragmentText The plain text we want to match
     * @param fragmentSentenceIndex The index of the sentence to which the plain text (fragment) belongs
     * @param fragmentPlainTextStartIndex The start index of the fragment in the plain text
     * @param newFragments The already handled fragments in this iteration
     * @param foundMatch Indicates whether a match has been found in an earlier handled fragment
     * @param allSentencesDone Indicates whether all fragment parts have been covered in the earlier handled fragments
     * @return Whether a match has been found and whether all fragment parts could be matched in the so far handled fragments (including the current one)
     */
    private Pair<Boolean, Boolean> matchFragment(int startIndex, int endIndex, String htmlText, String fragmentText, int fragmentSentenceIndex,
                               int fragmentPlainTextStartIndex, ArrayList<Fragment> newFragments,
                                                 boolean foundMatch, boolean allSentencesDone, boolean display) {
        while(fragmentText.length() > 0) {
            String searchText = htmlText.substring(startIndex, endIndex);

            Fragment foundFragment = matchReducingFromBack(searchText, fragmentText, fragmentSentenceIndex, startIndex,
                    fragmentPlainTextStartIndex, display);
            if (foundFragment != null) {
                foundMatch = true;
                int offset = foundFragment.getPlainTextStartIndex() - fragmentPlainTextStartIndex;
                if (offset > 0) { // there is still some unmatched part before
                    Fragment newFragment = new Fragment(fragmentText.substring(0, offset), fragmentSentenceIndex,
                            fragmentPlainTextStartIndex, display);
                    newFragments.add(newFragment);
                    allSentencesDone = false;
                }
                newFragments.add(foundFragment);

                // there might still be some unmatched part after
                int newStart = offset + foundFragment.getText().length();
                startIndex = foundFragment.getEndIndex();
                fragmentText = fragmentText.substring(newStart);
                fragmentPlainTextStartIndex = fragmentSentenceIndex + newStart;
            } else {
                newFragments.add(new Fragment(fragmentText, fragmentSentenceIndex, fragmentPlainTextStartIndex, display));
                allSentencesDone = false;
                fragmentText = "";
            }
        }

        return new Pair<>(foundMatch, allSentencesDone);
    }

    /**
     * Tries to match a given string to the HTML text
     * @param htmlText The HTML text
     * @param fragmentText The string to match
     * @param sentenceIndex The index of the sentence the string belongs to
     * @param startIndexOffset The index in the original fragment text at which the string starts
     * @param plainTextStartIndex The index in the plain text at which the original fragment text starts
     * @return The fragment if the string could be matched unambiguously; otherwise null
     */
    private Fragment tryMatchString(String htmlText, String fragmentText, int sentenceIndex, int startIndexOffset,
                                    int plainTextStartIndex, boolean display) {
        // Whitespaces can be very different in the HTML text than in the plain text
        if(fragmentText.trim().length() > 0) {
            // We remove trailing punctuation because that's sometimes added to the text although it's not in the HTML text
            while(fragmentText.length() > 0 && fragmentText.charAt(0) <= '\u0020'){ // trim removes anything smaller than this
                fragmentText = fragmentText.substring(1);
                plainTextStartIndex++;
            }
            fragmentText = fragmentText.trim();

            int startIndex = htmlText.indexOf(fragmentText);
            if (startIndex != -1) { //we have at least 1 match
                int secondIndex = htmlText.indexOf(fragmentText, startIndex + 1);
                if (secondIndex == -1) { // we have exactly 1 match
                    return new Fragment(
                            fragmentText, startIndex + startIndexOffset,
                            startIndex + startIndexOffset + fragmentText.length(),
                            sentenceIndex, true, plainTextStartIndex, display);
                }
            }
        }

        return null;
    }

    /**
     * Tries to match the plain text against the HTML text by recursively removing a character from the end of the plain text
     * until an unambiguous match is found.
     * @param htmlText The HTML text (fragment) in which the plain text fragment needs to be found
     * @param fragmentText The text of the (partial) plain text fragment
     * @param sentenceIndex The index of the sentence to which the plain text fragment belongs
     * @param startIndexOffset The index at which the HTML text starts in the entire HTML text
     * @param plainTextStartIndex The index in the plain text at which the original fragment starts
     * @return The fragment which could be matched unambiguously if any was found; otherwise null
     */
    private Fragment matchReducingFromBack(String htmlText, String fragmentText, int sentenceIndex, int startIndexOffset,
                                           int plainTextStartIndex, boolean display) {
        Fragment foundFragment = null;
        while(foundFragment == null && fragmentText.length() > 0) {
            foundFragment = tryMatchString(htmlText, fragmentText, sentenceIndex, startIndexOffset,
                    plainTextStartIndex, display);
            if (foundFragment == null && fragmentText.length() > 1) {
                foundFragment = matchReducingFromFront(htmlText, fragmentText, sentenceIndex, startIndexOffset,
                        plainTextStartIndex, display);
            }
            fragmentText = fragmentText.substring(0, fragmentText.length() - 1);
        }

        return foundFragment;
    }

    /**
     * Tries to match the plain text against the HTML text by recursively removing a character from the front of the plain text
     * until an unambiguous match is found.
     * @param htmlText The HTML text (fragment) in which the plain text fragment needs to be found
     * @param fragmentText The text of the (partial) plain text fragment
     * @param sentenceIndex The index of the sentence to which the plain text fragment belongs
     * @param startIndexOffset The index at which the HTML text starts in the entire HTML text
     * @param plainTextStartIndex The index in the plain text at which the original fragment starts
     * @return The fragment which could be matched unambiguously if any was found; otherwise null
     */
    private Fragment matchReducingFromFront(String htmlText, String fragmentText, int sentenceIndex,
                                            int startIndexOffset, int plainTextStartIndex, boolean display) {
        Fragment foundFragment = null;

        while (fragmentText.length() > 1) {
            fragmentText = fragmentText.substring(1);
            plainTextStartIndex++;

            foundFragment = tryMatchString(htmlText, fragmentText, sentenceIndex,
                    startIndexOffset, plainTextStartIndex, display);
        }

        return foundFragment;
    }

    /**
     * Determines the index at which the most previous fragment ends which has been matched unambiguously.
     * @param fragments The fragments into which the plain text is currently split
     * @return The end index of the last unambiguous fragment before the current fragment
     */
    private Integer getEndOfPreviousUnambiguousFragment(ArrayList<Fragment> fragments) {
        for(int i = fragments.size() - 1; i >= 0; i--) {
            if(fragments.get(i).isUnambiguousMatch()) {
                return fragments.get(i).getEndIndex();
            }
        }

        return null;
    }

    /**
     * Determines the index at which the nex fragment starts which has been matched unambiguously.
     * @param currentFragmentIndex The index of the current fragment
     * @param fragments The fragments into which the plain text is currently split
     * @return The start index of the first unambiguous fragment after the current fragment
     */
    private Integer getStartOfNextUnambiguousFragment(int currentFragmentIndex, ArrayList<Fragment> fragments) {
        if(currentFragmentIndex + 1 < fragments.size()) {
            Fragment nextFragment = fragments.get(currentFragmentIndex + 1);
            if(nextFragment.isUnambiguousMatch()) {
                return nextFragment.getStartIndex();
            } else {
                return getStartOfNextUnambiguousFragment(currentFragmentIndex + 1, fragments);
            }
        }

        return null;
    }
}
