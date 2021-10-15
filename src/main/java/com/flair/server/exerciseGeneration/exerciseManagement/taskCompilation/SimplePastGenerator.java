package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class SimplePastGenerator extends TenseGenerator {

    public SimplePastGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("past");
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
                String correctAffirmativeForm = generateCorrectForm(settings);
                incorrectForms.add(correctAffirmativeForm + " " + ((TenseSettings)settings).getSubject() + " not");
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm + " not");
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + correctAffirmativeForm);
                incorrectForms.add("not " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                incorrectForms.add("didn't " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                incorrectForms.add(((TenseSettings)settings).getSubject() + "didn't " + correctAffirmativeForm);
                incorrectForms.add(((TenseSettings)settings).getLemma() + " " + ((TenseSettings)settings).getSubject() + " not");
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma() + " not");
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + ((TenseSettings)settings).getLemma());
                incorrectForms.add("not " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                incorrectForms.add(((TenseSettings)settings).getSubject() + "didn't " + ((TenseSettings)settings).getLemma());
                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("wasn't " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                } else {
                    incorrectForms.add("aren't " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                }
            } else {
                String correctAffirmativeForm = generateCorrectForm(settings);
                incorrectForms.add(correctAffirmativeForm + " " + ((TenseSettings)settings).getSubject());
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " did " + correctAffirmativeForm);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " did " + ((TenseSettings)settings).getLemma());
                incorrectForms.add("did " + ((TenseSettings)settings).getSubject() + " " + correctAffirmativeForm);
                incorrectForms.add(((TenseSettings)settings).getLemma() + " " + ((TenseSettings)settings).getSubject());
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                incorrectForms.add(((TenseSettings)settings).getSubject() + " did " + ((TenseSettings)settings).getLemma());

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("was " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                } else {
                    incorrectForms.add("were " + ((TenseSettings)settings).getSubject() + " " + ((TenseSettings)settings).getLemma());
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                String correctNotNegatedForm = generateCorrectForm(settings);
                incorrectForms.add(correctNotNegatedForm + "n't");
                incorrectForms.add(correctNotNegatedForm + " not");
                incorrectForms.add("not " + correctNotNegatedForm);
                incorrectForms.add("didn't " + correctNotNegatedForm);
                incorrectForms.add("not " + ((TenseSettings)settings).getLemma());
                incorrectForms.add(((TenseSettings)settings).getLemma() + " not");
            } else {
                incorrectForms.addAll(generateIncorrectPastParticipleForms(((TenseSettings)settings).getLemma()));
            }
        }

        return  incorrectForms;
    }
}
