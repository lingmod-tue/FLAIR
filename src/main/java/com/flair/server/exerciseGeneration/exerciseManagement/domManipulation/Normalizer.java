package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

public class Normalizer {

    /**
     * Merges multiple successive whitespaces into a single space character.
     * Replaces single whitespaces with a space character.
     * Removes whitespaces before and after punctuation marks.
     * @param text 	The original text
     * @return 		The normalized text
     */
    public static String normalizeText(String text) {
        return text.replaceAll("[\\s\\h]+", " ")
                .replaceAll("(\\p{Punct}) ", "$1").replaceAll(" (\\p{Punct})", "$1");
    }

    /**
     * Replaces any whitespace character with a space character.
     * @param text 	The original text
     * @return 		The whitespace-normalized text
     */
    public static String normalizeWhitespaces(String text) {
        return text.replaceAll("[\\s\\h]", " ");
    }

}
