package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PresentProgressiveGenerator extends ProgressiveGenerator {

    public PresentProgressiveGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("present");
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
                    if(((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("isn't " + ((TenseSettings) settings).getSubject() + " " + participle);
                    } else {
                        incorrectForms.add("aren't " + ((TenseSettings) settings).getSubject() + " " + participle);
                    }
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " aren't " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " isn't " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not be " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("aren't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    if(((TenseSettings)settings).getSubject().equals("I")) {
                        incorrectForms.add("I amn't " + correctParticiple);
                        incorrectForms.add("amn't I " + correctParticiple);
                        incorrectForms.add("aren't I " + correctParticiple);
                    }
                    incorrectForms.add("isn't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            } else {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("is " + ((TenseSettings) settings).getSubject() + " " + participle);
                    } else {
                        if(((TenseSettings)settings).getSubject().equals("I")) {
                            incorrectForms.add("am " + ((TenseSettings) settings).getSubject() + " " + participle);
                        } else {
                            incorrectForms.add("are " + ((TenseSettings) settings).getSubject() + " " + participle);
                        }
                    }
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " is " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " are " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " be " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctParticiple);
                incorrectForms.add("be " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("are " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    if(((TenseSettings)settings).getSubject().equals("I")) {
                        incorrectForms.add(((TenseSettings)settings).getSubject() + " " + "am " + correctParticiple);
                        incorrectForms.add("are " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                    }
                    incorrectForms.add("is " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    if(((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("isn't " + participle);
                    } else {
                        if(((TenseSettings)settings).getSubject().equals("I")) {
                            incorrectForms.add("am not " + participle);
                        } else {
                            incorrectForms.add("aren't " + participle);
                        }
                    }
                }

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("aren't " + correctParticiple);
                } else if(((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("amn't " + correctParticiple);
                }
                incorrectForms.add("not " + correctParticiple);
                incorrectForms.add("not be " + correctParticiple);
            } else {
                participles.add(correctParticiple);

                for(String participle : participles) {
                    incorrectForms.add("are " + participle);    // we also want 'I are' and "he are" forms as incorrect forms
                    incorrectForms.add("be " + participle);
                    incorrectForms.add("do " + participle);    // incorrect auxiliary
                    incorrectForms.add("does " + participle);

                    if (((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("is " + participle);
                    } else if (((TenseSettings)settings).getSubject().equals("I")) {
                        incorrectForms.add("am " + participle);
                    }
                }
            }
        }

        return  incorrectForms;
    }
}
