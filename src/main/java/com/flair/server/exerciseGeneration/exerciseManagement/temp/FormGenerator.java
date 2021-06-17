package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public abstract class FormGenerator {

    public FormGenerator(SimpleNlgParser generator) {
        this.generator = generator;
    }

    protected final SimpleNlgParser generator;

    /**
     * Generates the correct form for the given parameters.
     * @param settings  The parameter settings
     * @return          The generated form
     */
    public abstract String generateCorrectForm(ParameterSettings settings);

    /**
     * Generates incorrect forms for the given parameters.
     * @param settings  The parameter settings
     * @return          The generated incorrect forms, possibly also including the correct form
     */
    public abstract HashSet<String> generateIncorrectForms(ParameterSettings settings);
}
