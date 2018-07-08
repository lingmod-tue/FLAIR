package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.TextSegment;
import com.flair.server.resources.ResourceLoader;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.SimpleObjectPool;
import com.flair.server.utilities.SimpleObjectPoolResource;
import com.flair.shared.grammar.Language;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.Pair;
import intoxicant.analytics.corenlp.StopwordAnnotator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/*
 * Performs various preprocessing tasks for sentence selectors
 */
final class Preprocessor {
	public static final ThreadLocal<Preprocessor> INSTANCE = ThreadLocal.withInitial(Preprocessor::new);

	static final class PreprocessedSentence {
		final AbstractDocument source;
		final TextSegment sourceSentence;
		final int sentId;
		final Set<String> tokens;       // unique tokens only

		PreprocessedSentence(AbstractDocument s, TextSegment ss, int id) {
			source = s;
			sourceSentence = ss;
			sentId = id;
			tokens = new HashSet<>();
		}
	}

	private static final class LanguageData {
		private static final int PARSER_INSTANCE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;

		final String stopwords;   // comma-separated
		final SimpleObjectPool<StanfordCoreNLP> parserPool;

		LanguageData(String sw, SimpleObjectPool.Factory f) {
			stopwords = sw;
			parserPool = new SimpleObjectPool<StanfordCoreNLP>(PARSER_INSTANCE_POOL_SIZE, f);
		}
	}

	private static Map<Language, LanguageData> SUPPORTED_LANGUAGES;

	static {
		SUPPORTED_LANGUAGES = new EnumMap<>(Language.class);

		String stopWordsEng = parseStopwords(Language.ENGLISH, ResourceLoader.get("stopwords-en.txt"));
		LanguageData eng = new LanguageData(stopWordsEng, () -> {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma, stopword");
			props.setProperty("customAnnotatorClass.stopword", "intoxicant.analytics.corenlp.StopwordAnnotator");
			props.setProperty(StopwordAnnotator.STOPWORDS_LIST, stopWordsEng);
			props.setProperty(StopwordAnnotator.IGNORE_STOPWORD_CASE, "true");
			return new StanfordCoreNLP(props);
		});
		SUPPORTED_LANGUAGES.put(Language.ENGLISH, eng);

		// ensure immutablity
		SUPPORTED_LANGUAGES = Collections.unmodifiableMap(SUPPORTED_LANGUAGES);
	}


	private static String parseStopwords(Language l, InputStream input) {
		StringBuilder out = new StringBuilder();
		try (BufferedReader buf = new BufferedReader(new InputStreamReader(input))) {
			String line;
			while ((line = buf.readLine()) != null)
				out.append(line).append(",");
		} catch (IOException e) {
			ServerLogger.get().error(e, "Couldn't read stopwords list for " + l);
		}

		String stopwords = out.toString();
		return stopwords.substring(0, stopwords.length() - 1);
	}

	private boolean isLanguageSupported(Language lang) {
		return SUPPORTED_LANGUAGES.get(lang) != null;
	}

	List<PreprocessedSentence> preprocessDocument(AbstractDocument doc, DocumentSentenceSelectorParams params) {
		List<PreprocessedSentence> out = new ArrayList<>();

		if (!isLanguageSupported(doc.getLanguage()))
			throw new IllegalArgumentException("Couldn't preprocess for language " + doc.getLanguage());
		else if (!isLanguageSupported(params.lang))
			throw new IllegalArgumentException("Couldn't preprocess for language " + params.lang);
		else if (doc.getLanguage() != params.lang)
			throw new IllegalArgumentException("Mismatching languages for preprocessor");
		else if (!doc.isParsed())
			throw new IllegalArgumentException("Couldn't preprocess unparsed document");

		LanguageData state = SUPPORTED_LANGUAGES.get(params.lang);
		String docText = doc.getText();

		try (SimpleObjectPoolResource<StanfordCoreNLP> parser = state.parserPool.get()) {
			int i = 1;
			for (TextSegment itr : doc.getSentenceSegments()) {
				String sentenceText = docText.substring(itr.getStart(), itr.getEnd());
				PreprocessedSentence preprocSent = new PreprocessedSentence(doc, itr, i);

				Annotation anno = new Annotation(sentenceText);
				parser.get().annotate(anno);

				for (CoreLabel token : anno.get(CoreAnnotations.TokensAnnotation.class)) {
					String lemma = token.lemma();
					String word = token.word();
					if (word.length() == 1 && "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".contains(word))
						continue;

					if (params.ignoreStopwords) {
						Pair<Boolean, Boolean> stopword = token.get(StopwordAnnotator.class);
						if (lemma != null && stopword.second)
							continue;
						else if (stopword.first)
							continue;
					}

					if (params.stemWords && lemma != null)
						preprocSent.tokens.add(lemma.toLowerCase());
					else
						preprocSent.tokens.add(word.toLowerCase());
				}

				out.add(preprocSent);
				++i;
			}
		} catch (Throwable e) {
			ServerLogger.get().error(e, "Error while preprocessing document " + doc.toString());
		}

		return out;
	}
}
