package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import simplenlg.features.*;
import simplenlg.framework.NLGElement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PresentProgressiveGenerator extends TenseGenerator {

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

    /**
     * Generates incorrect present participle forms for the given lemma.
     * @param lemma Verb lemma
     * @return      Incorrect present participle forms
     */
    private HashSet<String> generateIncorrectPresentParticipleForms(String lemma) {
        HashSet<String> incorrectForms = new HashSet<>();

        if(lemma.substring(lemma.length() - 1).matches("[^aeiouyw]")) {
            incorrectForms.add(lemma + lemma.substring(lemma.length() - 1) + "ing");   // incorrect if the trailing consonant shouldn't be doubled
        } else if(lemma.endsWith("e")) {
            incorrectForms.add(lemma.substring(0, lemma.length() - 1) + "ing");   // incorrect if the trailing 'e' shouldn't be dropped
        }

        return incorrectForms;
    }

    /**
     * Generates the correct present participle form for the given lemma.
     * @param lemma The verb lemma
     * @return      The correct present participle
     */
    private String generateCorrectPresentParticipleForm(String lemma) {
        NLGElement p = generator.nlgFactory().createVerbPhrase(lemma);
        p.setFeature(Feature.TENSE, Tense.PRESENT);
        p.setFeature(Feature.PROGRESSIVE, true);

        return generator.realiser().realise(p).toString().substring(2);
    }
}
