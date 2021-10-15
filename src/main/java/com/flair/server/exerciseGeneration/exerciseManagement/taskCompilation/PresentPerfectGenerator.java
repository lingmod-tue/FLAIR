package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PresentPerfectGenerator extends TenseGenerator {

    public PresentPerfectGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("present");
        ((TenseSettings)settings).setProgressive(false);
        ((TenseSettings)settings).setPerfect(true);
        return super.generateCorrectTenseForm(((TenseSettings)settings));
    }

    @Override
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        // We are very likely to also generate the correct form at some point, but we will filter that out later
        HashSet<String> incorrectForms = new HashSet<>();

        String correctParticiple = generateCorrectPastParticipleForm(((TenseSettings)settings).getLemma());

        if(((TenseSettings)settings).isInterrogative()) {
            if(((TenseSettings)settings).isNegated()) {
                incorrectForms.add(((TenseSettings)settings).getSubject() + " haven't " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " hasn't " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("haven't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                    incorrectForms.add("isn't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    incorrectForms.add("hasn't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                    incorrectForms.add("aren't " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            } else {
                incorrectForms.add(((TenseSettings)settings).getSubject() + " has " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " have " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctParticiple);

                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("have " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                    incorrectForms.add("is " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                } else {
                    incorrectForms.add("has " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                    incorrectForms.add("are " + ((TenseSettings)settings).getSubject() + " " + correctParticiple);
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("haven't " + correctParticiple);
                    incorrectForms.add("isn't " + correctParticiple);
                } else {
                    incorrectForms.add("hasn't " + correctParticiple);
                    incorrectForms.add("aren't " + correctParticiple);
                }
                incorrectForms.add("not " + correctParticiple);
            } else {
                HashSet<String> participles = generateIncorrectPastParticipleForms(((TenseSettings)settings).getLemma());
                participles.add(correctParticiple);

                for(String participle : participles) {
                    if (((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("have " + participle);
                        incorrectForms.add("is " + participle);
                    } else {
                        incorrectForms.add("has " + participle);
                        incorrectForms.add("are " + participle);
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
