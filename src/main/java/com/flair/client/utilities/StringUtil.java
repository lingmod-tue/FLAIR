package com.flair.client.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * Removes all occurrences of the specified tag.
     *
     * @param str The string to remove the tags from.
     * @param tag The HTML tag.
     * @return The string with the tags remvoed.
     */
    public static String removeAllTagsByTag(String str, String tag) {
        return str.replaceAll("</?" + tag + "[^>]*>", "");
    }

}
