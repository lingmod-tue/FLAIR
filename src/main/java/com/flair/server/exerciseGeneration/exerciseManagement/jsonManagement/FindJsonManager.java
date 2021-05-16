package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

public class FindJsonManager extends ExerciseWithInlineBlanksJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<String> distractorList) {
        return " *" + construction.replaceAll("\\s", "\u00A0") + "* ";
    }

}
