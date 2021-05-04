package com.flair.server.parser;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.english.Realiser;

import java.util.Properties;

import com.flair.shared.grammar.Language;

public class SimpleNlgParser implements ThreadSafeParser<SimpleNlgParser,
        SimpleNlgParsingStrategy<? extends AbstractParser.Input,
                ? extends AbstractParser.Output>> {
    public static final class Factory implements AbstractParser.Factory {
        private Factory() {}
        @Override
        public SimpleNlgParser create(Language lang, Properties properties) {
            return new SimpleNlgParser(lang);
        }
    }

    private NLGFactory nlgFactory = null;
    private Realiser realiser = null;
    private final Language language;
    private Lexicon lexicon = null;

    private SimpleNlgParser(Language lang) {
        this.language = lang;
    }

    public Lexicon lexicon() {
        if(lexicon == null) {
            lexicon = Lexicon.getDefaultLexicon();
        }
        return lexicon;
    }

    public Realiser realiser() {
        if(realiser == null) {
            realiser = new Realiser(lexicon());
        }
        return realiser;
    }
    public NLGFactory nlgFactory() {
        if(nlgFactory == null) {
            nlgFactory = new NLGFactory(lexicon());
        }
        return nlgFactory;
    }


    @Override
    public ParserKind type() {
        return ParserKind.SIMPLE_NLG;
    }
    @Override
    public Language language() {
        return language;
    }

    @Override
    public void parse(SimpleNlgParsingStrategy<?, ?> strategy) {
        try {
            if (language != strategy.input().language()) {
                throw new IllegalArgumentException("Document language " + strategy.input().language()
                        + " not supported (Model language: " + language + ")");
            }

            strategy.apply(this);
        } catch (Throwable e) {
            throw e;
        }
    }

    public static Factory factory() { return new Factory(); }
}
