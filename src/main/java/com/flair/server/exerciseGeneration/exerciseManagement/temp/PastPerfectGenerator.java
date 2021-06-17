package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PastPerfectGenerator extends TenseGenerator {

    public PastPerfectGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("past");
        ((TenseSettings)settings).setProgressive(false);
        ((TenseSettings)settings).setPerfect(true);
        return super.generateCorrectTenseForm(((TenseSettings)settings));
    }

    @Override
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        // We are very likely to also generate the correct form at some point, but we will filter that out later
        HashSet<String> incorrectForms = new HashSet<>();

        String correctParticiple = generateCorrectPastParticipleForm(((TenseSettings)settings).getLemma());
        HashSet<String> participles = generateIncorrectPastParticipleForms(((TenseSettings)settings).getLemma());

        if(((TenseSettings)settings).isInterrogative()) {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    incorrectForms.add("hadn't " + ((TenseSettings)settings).getSubject() + " " + participle);
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " hadn't " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("wasn't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    incorrectForms.add("weren't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            } else {
                for(String participle : participles) {
                    incorrectForms.add("had " + ((TenseSettings)settings).getSubject() + " " + participle);
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " had " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("was " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    incorrectForms.add("were " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    incorrectForms.add("hadn't " + participle);
                }

                if(((TenseSettings)settings).isThirdSingular() || ((TenseSettings)settings).getSubject().equals("I")) {
                    incorrectForms.add("wasn't " + correctParticiple);
                } else {
                    incorrectForms.add("weren't " + correctParticiple);
                }
                incorrectForms.add("not " + correctParticiple);
            } else {
                participles.add(correctParticiple);

                for(String participle : participles) {
                    incorrectForms.add("had " + participle);
                    if (((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("was " + participle);
                    } else {
                        incorrectForms.add("were " + participle);
                    }
                }
            }
        }

        return  incorrectForms;
    }

    /**
     * Generates the correct past participle form for the given lemma.
     * @param lemma The verb lemma
     * @return      The correct past participle
     */
    private String generateCorrectPastParticipleForm(String lemma) {
        NLGElement p = generator.nlgFactory().createVerbPhrase(lemma);
        p.setFeature(Feature.TENSE, Tense.PRESENT);
        p.setFeature(Feature.PERFECT, true);

        return generator.realiser().realise(p).toString().substring(3);
    }
}
