package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PresentPerfectProgressiveGenerator extends ProgressiveGenerator {

    public PresentPerfectProgressiveGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
    	((TenseSettings)settings).setTense("present");
        ((TenseSettings)settings).setProgressive(true);
        ((TenseSettings)settings).setPerfect(true);
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
                    if(((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("hasn't " + ((TenseSettings) settings).getSubject() + " been " + participle);
                    } else {
                        incorrectForms.add("haven't " + ((TenseSettings) settings).getSubject() + " been " + participle);
                    }
                }

                incorrectForms.add("haven't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not been " + correctParticiple);
                incorrectForms.add("hasn't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add("haven't " + ((TenseSettings)settings).getSubject() + correctParticiple);              
                incorrectForms.add("hasn't " + ((TenseSettings)settings).getSubject() + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("haven't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                } else {
                    incorrectForms.add("hasn't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                }
            } else {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("has " + ((TenseSettings) settings).getSubject() + " been " + participle);
                    } else {
                        incorrectForms.add("have " + ((TenseSettings) settings).getSubject() + " been " + participle);
                    }
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " has been " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " have been " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " been " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("have " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                } else {
                    incorrectForms.add("has " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("hasn't been " + participle);
                    } else {
                        incorrectForms.add("haven't been " + participle);
                    }
                }

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("haven't been " + correctParticiple);
                } else {
                    incorrectForms.add("hasn't been " + correctParticiple);
                }
                incorrectForms.add("not been " + correctParticiple);
            } else {
                participles.add(correctParticiple);

                for(String participle : participles) {
                    incorrectForms.add("have been " + participle);
                    incorrectForms.add("has been " + participle);
                    incorrectForms.add("been " + participle);
                    incorrectForms.add("has done " + participle);    // incorrect auxiliary
                    incorrectForms.add("have done " + participle);
                }
            }
        }

        return  incorrectForms;
    }
}
