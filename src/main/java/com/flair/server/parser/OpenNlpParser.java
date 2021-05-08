package com.flair.server.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.shared.grammar.Language;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;

public class OpenNlpParser implements ThreadSafeParser<OpenNlpParser,
        OpenNlpParsingStrategy<? extends AbstractParser.Input,
                ? extends AbstractParser.Output>> {
    public static final class Factory implements AbstractParser.Factory {
        private Factory() {}
        @Override
        public OpenNlpParser create(Language lang, Properties properties) {
            return new OpenNlpParser(lang);
        }
    }

    DictionaryLemmatizer lemmatizer = null;
    private final Language language;

    private OpenNlpParser(Language lang) {
        this.language = lang;
        
        InputStream dictLemmatizer = ResourceLoader.loadFile("dictionary.txt");
        try {
			lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public DictionaryLemmatizer lemmatizer() {
    	return lemmatizer;
    }


    @Override
    public ParserKind type() {
        return ParserKind.OPEN_NLP;
    }
    @Override
    public Language language() {
        return language;
    }

    @Override
    public void parse(OpenNlpParsingStrategy<?, ?> strategy) {
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
