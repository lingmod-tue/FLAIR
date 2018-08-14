package com.flair.server.parser.corenlp;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.ArraySet;
import edu.stanford.nlp.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CoreNlp Annotator that checks if in coming token is a keyword
 */
public class KeywordAnnotator implements Annotator, CoreAnnotation<Pair<Boolean, Boolean>> {

	/**
	 * stopword annotator class name used in annotators property
	 */
	public static final String ANNOTATOR_CLASS = "keyword";

	/**
	 * Property key to specify the comma delimited list of custom keywords
	 */
	public static final String KEYWORD_LIST = "keyword-list";

	/**
	 * Property key to specify if stopword list is case insensitive
	 */
	public static final String IGNORE_KEYWORD_CASE = "ignore-keyword-case";

	private static Class<? extends Pair> boolPair = Pair.makePair(true, true).getClass();

	private Set<String> keywords;
	private boolean ignoreCase;

	public KeywordAnnotator() {
		this(new Properties());
	}

	public KeywordAnnotator(String notUsed, Properties props) {
		this(props);
	}

	public KeywordAnnotator(Properties props) {
		if (props.containsKey(KEYWORD_LIST)) {
			this.ignoreCase = Boolean.parseBoolean(props.getProperty(IGNORE_KEYWORD_CASE, "true"));
			this.keywords = readKeywordList(props.getProperty(KEYWORD_LIST), ignoreCase);
		} else
			throw new IllegalArgumentException("Keyword list was not specified");
	}

	@Override
	public void annotate(Annotation annotation) {
		if (keywords != null && keywords.size() > 0 && annotation.containsKey(CoreAnnotations.TokensAnnotation.class)) {
			List<CoreLabel> tokens = annotation.get(CoreAnnotations.TokensAnnotation.class);
			for (CoreLabel token : tokens) {
				String word = token.word();
				if (ignoreCase)
					word = word.toLowerCase();
				boolean isKeyword = keywords.contains(word);
				token.set(KeywordAnnotator.class, Pair.makePair(isKeyword, ignoreCase));
			}
		}
	}

	@Override
	public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
		return Collections.singleton(KeywordAnnotator.class);
	}

	@Override
	public Set<Class<? extends CoreAnnotation>> requires() {
		return Collections.unmodifiableSet(new ArraySet<>(Arrays.asList(
				CoreAnnotations.TextAnnotation.class,
				CoreAnnotations.TokensAnnotation.class
		)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Pair<Boolean, Boolean>> getType() {
		return (Class<Pair<Boolean, Boolean>>) boolPair;
	}

	private static Set<String> readKeywordList(String keywordList, boolean ignoreCase) {
		Set<String> keywords = Arrays.stream(keywordList.split(",")).map(w -> {
			if (ignoreCase)
				return w.toLowerCase();
			else
				return w;
		}).collect(Collectors.toSet());
		return Collections.unmodifiableSet(keywords);
	}
}
