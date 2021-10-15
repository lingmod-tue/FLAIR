package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class PastPerfectProgressiveGenerator extends ProgressiveGenerator {

    public PastPerfectProgressiveGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("past");
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
                    incorrectForms.add("hadn't " + ((TenseSettings) settings).getSubject() + " been " + participle);
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " hadn't been " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " not been " + correctParticiple);
                incorrectForms.add("didn't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add("wasn't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add("weren't " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
            } else {
                for(String participle : participles) {
                    incorrectForms.add("had " + ((TenseSettings) settings).getSubject() + " been " + participle);
                }

                incorrectForms.add(((TenseSettings)settings).getSubject() + " had been " + correctParticiple);
                incorrectForms.add("did " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add("was " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add("were " + ((TenseSettings)settings).getSubject() + " been " + correctParticiple);
                incorrectForms.add(((TenseSettings)settings).getSubject() + " been " + correctParticiple);
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                for(String participle : participles) {
                    incorrectForms.add("hadn't been " + participle);
                }

                incorrectForms.add("not been " + correctParticiple);
                incorrectForms.add("didn't been " + correctParticiple);
                incorrectForms.add("wasn't been " + correctParticiple);
                incorrectForms.add("weren't been " + correctParticiple);
            } else {
                participles.add(correctParticiple);

                for(String participle : participles) {
                    incorrectForms.add("had been " + participle);
                    incorrectForms.add("had be " + participle);
                    incorrectForms.add("did been " + participle);	// incorrect auxiliary
                    incorrectForms.add("been " + participle);
                    incorrectForms.add("was been " + participle);    
                    incorrectForms.add("were been " + participle);
                }
            }
        }

        return  incorrectForms;
    }
}
