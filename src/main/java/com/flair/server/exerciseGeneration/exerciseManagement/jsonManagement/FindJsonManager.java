package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

public class FindJsonManager extends SingleExerciseWithInlineBlanksJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction) {
        return " *" + construction.replaceAll("\\s", "\u00A0") + "* ";
    }

}