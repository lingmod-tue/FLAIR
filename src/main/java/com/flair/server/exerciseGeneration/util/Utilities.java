package com.flair.server.exerciseGeneration.util;

public class Utilities {
	
	/**
	 * Identifies the index of the next character as specified that is not escaped.
	 * Escaping is expected to be realized as doubling the character.
	 * @param text			The text in which to search for the character
	 * @param startIndex	The index in the text after which to look for the character
	 * @param character		The character to search for
	 * @return				The index of the first occurrence of the character
	 */
    public static int getNextNotEscapedCharacter(String text, int startIndex, String character) {
    	int index = text.indexOf(character, startIndex);
        String nextCharacter = "";
        if (index != -1 && index < text.length() - 1) {
            nextCharacter = text.substring(index + 1, index + 2);
        }
        while (nextCharacter.equals(character)) {
            index = text.indexOf(character, index + 2);
            if (index != -1 && index < text.length() - 1) {
                nextCharacter = text.substring(index + 1, index + 2);
            } else {
                nextCharacter = "";
            }
        }

        return index;
    }
}
