package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class SimplePresentGenerator extends TenseGenerator {

    public SimplePresentGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("present");
        ((TenseSettings)settings).setProgressive(false);
        ((TenseSettings)settings).setPerfect(false);
        return super.generateCorrectTenseForm(((TenseSettings)settings));
    }

    @Override
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        // We are very likely to also generate the correct form at some point, but we will filter that out later
        HashSet<String> incorrectForms = new HashSet<>();

        if(((TenseSettings)settings).isInterrogative()) {
            if(((TenseSettings)settings).isNegated()) {
                if(((TenseSettings)settings).isThirdSingular()) {
                    String correctAffirmativeForm = generateCorrectForm(settings);
                    incorrectForms.add(correctAffirmativeForm + " " + ((TenseSettings)settings).getSubject() + " not");
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm + " not");
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + correctAffirmativeForm);
                    incorrectForms.add("not " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                    incorrectForms.add("doesn't " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                    incorrectForms.add("don't " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                    incorrectForms.add(((TenseSettings)settings).getSubject() + "doesn't " + correctAffirmativeForm);
                    incorrectForms.add(((TenseSettings)settings).getSubject() + "don't " + correctAffirmativeForm);
                    incorrectForms.add(((TenseSettings)settings).getSubject() + "doesn't " + ((TenseSettings)settings).getLemma());
                    incorrectForms.add(((TenseSettings)settings).getSubject() + "don't " + ((TenseSettings)settings).getLemma());
                    incorrectForms.add("don't " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                } else {
                    incorrectForms.add(((TenseSettings)settings).getLemma() + " " + ((TenseSettings)settings).getSubject() + " not");
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma() + " not");
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + ((TenseSettings)settings).getLemma());
                    incorrectForms.add("not " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " don't " + ((TenseSettings)settings).getLemma());
                }
            } else {
                if(((TenseSettings)settings).isThirdSingular()) {
                    String correctAffirmativeForm = generateCorrectForm(settings);
                    incorrectForms.add(correctAffirmativeForm + " " + ((TenseSettings)settings).getSubject());
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " does " + correctAffirmativeForm);
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " does " + ((TenseSettings)settings).getLemma());
                    incorrectForms.add("does " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                } else {
                    incorrectForms.add(((TenseSettings)settings).getLemma() + " " + ((TenseSettings)settings).getSubject());
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                    incorrectForms.add(((TenseSettings)settings).getSubject() + " do " + ((TenseSettings)settings).getLemma());
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                if(((TenseSettings)settings).isThirdSingular()) {
                    // We could also include incorrectly formed forms as base, but we don't want to introduce too may errors in a single distractor
                    String correctNotNegatedForm = generateCorrectForm(settings);
                    incorrectForms.add(correctNotNegatedForm + "n't");
                    incorrectForms.add(correctNotNegatedForm + " not");
                    incorrectForms.add("not " + correctNotNegatedForm);
                    incorrectForms.add("doesn't " + correctNotNegatedForm);
                    incorrectForms.add("don't " + correctNotNegatedForm);
                } else {
                    incorrectForms.add(((TenseSettings)settings).getLemma() + "n't");
                    incorrectForms.add(((TenseSettings)settings).getLemma() + " not");
                    incorrectForms.add("not " + ((TenseSettings)settings).getLemma());
                }
            } else {
                if(((TenseSettings)settings).isThirdSingular()) {
                    if(((TenseSettings)settings).getLemma().endsWith("y")) {
                        incorrectForms.add(((TenseSettings)settings).getLemma() + "s");
                        incorrectForms.add((((TenseSettings)settings).getLemma().substring(0, ((TenseSettings)settings).getLemma().length() - 1)) + "ies");
                    }
                    incorrectForms.add(((TenseSettings)settings).getLemma() + "s");
                    incorrectForms.add(((TenseSettings)settings).getLemma() + "es");
                } else {
                    incorrectForms.add(((TenseSettings)settings).getLemma());
                }
            }
        }

        return  incorrectForms;
    }
}
