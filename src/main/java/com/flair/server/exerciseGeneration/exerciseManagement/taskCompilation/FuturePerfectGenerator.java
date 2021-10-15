package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class FuturePerfectGenerator extends TenseGenerator {

    public FuturePerfectGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("future");
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
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not have " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " will not " + correctParticiple);
                incorrectForms.add("won't " + ((TenseSettings)settings).getSubject() + " have " + ((TenseSettings)settings).getLemma());
                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("won't " + ((TenseSettings)settings).getSubject() + " has " + correctParticiple);
                }
            } else {
                incorrectForms.add(((TenseSettings)settings).getSubject() + " will " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " " + correctParticiple);
                incorrectForms.add("will " + ((TenseSettings)settings).getSubject() + " have " + ((TenseSettings)settings).getLemma());
                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("will " + ((TenseSettings)settings).getSubject() + " has " + correctParticiple);
                }
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                if(((TenseSettings)settings).isThirdSingular()) {
                    incorrectForms.add("won't has " + correctParticiple);
                }
                incorrectForms.add("not " + correctParticiple);
                incorrectForms.add("not have " + correctParticiple);
            } else {
                HashSet<String> participles = generateIncorrectPastParticipleForms(((TenseSettings)settings).getLemma());
                participles.add(correctParticiple);

                for(String participle : participles) {
                    incorrectForms.add("will " + participle);
                    incorrectForms.add("will have " + ((TenseSettings)settings).getLemma());

                    if (((TenseSettings)settings).isThirdSingular()) {
                        incorrectForms.add("will has " + participle);
                        incorrectForms.add("will has " + ((TenseSettings)settings).getLemma());
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
