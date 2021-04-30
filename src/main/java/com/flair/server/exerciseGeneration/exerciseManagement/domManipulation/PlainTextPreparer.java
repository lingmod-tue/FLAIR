package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;


import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class PlainTextPreparer {

	/**
	 * Calculates the indices of sentences and blanks in the normalized plain text.
	 * @param settings	The exercise settings from the client
	 */
    public void prepareIndices(ExerciseSettings settings) {
        Collections.sort(settings.getConstructions(),
                (c1, c2) -> c1.getConstructionIndices().first < c2.getConstructionIndices().first ? -1 : 1);

        Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> res = normalizePlainText(settings.getPlainText(), settings.getSelectionStartIndex(),
                settings.getSelectionEndIndex(), settings.getConstructions());

        // Construction indices are modified in-place, we just need to set the sentences and sentence start indices
        settings.setSentences(res.second);
        settings.setSentenceStartIndices(res.first);
    }

    /**
     * Splits the plain text into sentences.
     * Saves the texts and start indices per sentence.
     * @param lines 	The original plain text split into lines at linefeeds
     * @return			The sentence indices and the split sentences in the normalized plain text
     */
    private Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> splitSentences(ArrayList<Pair<String, Boolean>> lines) {
        ArrayList<Integer> sentenceStartIndices = new ArrayList<>();
        ArrayList<Pair<String, Boolean>> sentences = new ArrayList<>();

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int offset = 0;
        for(Pair<String, Boolean> line : lines) {
            if(line.first.trim().length() > 0) {
                CoreDocument doc = new CoreDocument(line.first);
                pipeline.annotate(doc);

                for (CoreSentence sent : doc.sentences()) {
                    String sentence = sent.text();

                    // Sentence-final punctuation is sometimes added by the framework, so we can't rely on it existing in the HTML text
                    if (sentence.substring(sentence.length() - 1).matches("[.;,]")) {
                        sentence = sentence.substring(0, sentence.length() - 1).trim();
                    }
                    if(sentence.length() > 0) {
                        sentenceStartIndices.add(offset + sent.charOffsets().first);
                        sentences.add(new Pair<>(Normalizer.normalizeText(sentence.trim()), line.second));
                    }
                }
            }
            offset += line.first.length();
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
     * @param selectionStartIndex	The start index of the selected document part in the original plain text
     * @param selectionEndIndex		The end index of the selected document part in the original plain text
     * @param constructions			The blanks
     * @return						The sentence indices and the split sentences in the normalized plain text
     */
    private Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> normalizePlainText(String plainText, int selectionStartIndex, int selectionEndIndex,
                                    ArrayList<Construction> constructions) {
        ArrayList<Pair<Integer, Integer>> constructionIndices = new ArrayList<>();
        for(Construction c : constructions) {
            constructionIndices.add(c.getConstructionIndices());
        }

        ArrayList<Pair<String, Boolean>> lines = new ArrayList<>();
        StringBuilder originalPlainTextBuilder = new StringBuilder();
        StringBuilder plainTextBuilder = new StringBuilder();
        ArrayList<Pair<String, Boolean>> selectionSegments = new ArrayList<>();
        if(selectionStartIndex > 0) {
            selectionSegments.add(new Pair<>(plainText.substring(0, selectionStartIndex), false));
        }
        selectionSegments.add(new Pair<>(plainText.substring(selectionStartIndex, selectionEndIndex), true));
        if(selectionEndIndex < plainText.length()) {
            selectionSegments.add(new Pair<>(plainText.substring(selectionEndIndex), false));
        }

        for(Pair<String, Boolean> selectionSegment : selectionSegments) {
            String[] segmentLines = selectionSegment.first.split("\n");
            for (int i = 0; i < segmentLines.length; i++) {
                String line = segmentLines[i];
                // add a space to account for the newline split character if it wasn't the last line of the segment
                String delimiterReplacement = i == segmentLines.length - 1 ? "" : " ";
                String originalLine = Normalizer.normalizeWhitespaces(line) + delimiterReplacement;
                originalPlainTextBuilder.append(originalLine);
                String normalizedLine = Normalizer.normalizeText(line.trim() + delimiterReplacement);
                plainTextBuilder.append(normalizedLine);
                lines.add(new Pair<>(originalLine, selectionSegment.second));
            }
        }

        Pair<ArrayList<Integer>, ArrayList<Pair<String, Boolean>>> res = splitSentences(lines);

        String originalPlainText = originalPlainTextBuilder.toString();
        plainText = plainTextBuilder.toString();

        matchIndicesToNormalizedPlainText(plainText, originalPlainText, constructionIndices, res.first);

        for(int i = 0; i < constructionIndices.size(); i++) {
            constructions.get(i).setConstructionIndices(constructionIndices.get(i));
        }

        return res;
    }

}
