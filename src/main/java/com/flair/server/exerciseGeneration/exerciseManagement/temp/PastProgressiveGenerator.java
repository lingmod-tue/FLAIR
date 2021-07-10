package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PastProgressiveGenerator extends ProgressiveGenerator {

    public PastProgressiveGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("past");
        ((TenseSettings)settings).setProgressive(true);
        ((TenseSettings)settings).setPerfect(false);
        return super.generateCorrectTenseForm(((TenseSettings)settings));
    }

    @Override
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        // We are very likely to also generate the correct form at some point, but we will filter that out later
        HashSet<String> incorrectForms = new HashSet<>();

        String correctParticiple = generateCorrectPresentParticipleForm(((TenseSettings)settings).getLemma());
        HashSet<String> participles = generateIncorrectPresentParticipleForms(((TenseSettings)settings).getLemma());

        if(((TenseSettings)settings).isInterrogative()) {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                        incorrectForms.add("wasn't " + ((TenseSettings) settings).getSubject() + " " + participle);
                    } else {
                        incorrectForms.add("weren't " + ((TenseSettings) settings).getSubject() + " " + participle);
                    }
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " weren't " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " wasn't " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("weren't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    incorrectForms.add("wasn't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            } else {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                        incorrectForms.add("was " + ((TenseSettings) settings).getSubject() + " " + participle);
                    } else {
                        incorrectForms.add("were " + ((TenseSettings) settings).getSubject() + " " + participle);
                    }
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " was " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " were " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " be " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctParticiple);
                incorrectForms.add("be " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("were " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    incorrectForms.add("was " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                        incorrectForms.add("wasn't " + participle);
                    } else {
                        incorrectForms.add("weren't " + participle);
                    }
                }

                if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("weren't " + correctParticiple);
                } else {
                    incorrectForms.add("wasn't " + correctParticiple);
                }
                incorrectForms.add("not were " + correctParticiple);
                incorrectForms.add("not was " + correctParticiple);
            } else {
                participles.add(correctParticiple);

                for(String participle : participles) {
                    incorrectForms.add("were " + participle);
                    incorrectForms.add("was " + participle);
                    incorrectForms.add("did " + participle);    // incorrect auxiliary
                }
            }
        }

        return  incorrectForms;
    }
}
