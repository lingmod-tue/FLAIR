package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

public class Normalizer {

    /**
     * Replaces any whitespace character with a space character.
     * @param text 	The original text
     * @return 		The whitespace-normalized text
     */
    public static String normalizeWhitespaces(String text) {
        return text.replaceAll("[\\s\\h]", " ");
    }

}
