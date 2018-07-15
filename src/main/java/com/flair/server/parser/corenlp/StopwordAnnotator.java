package com.flair.server.parser.corenlp;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.ArraySet;
import edu.stanford.nlp.util.Pair;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import java.util.*;

/**
 * CoreNlp Annotator that checks if in coming token is a stopword
 *
 * @author John Conwell
 * @author Paul Landes
 * @author Madeesh Kannan (lemma fixes)
 */
public class StopwordAnnotator implements Annotator, CoreAnnotation<Pair<Boolean, Boolean>> {

	/**
	 * stopword annotator class name used in annotators property
	 */
	public static final String ANNOTATOR_CLASS = "stopword";

	/**
	 * Property key to specify the comma delimited list of custom stopwords
	 */
	public static final String STOPWORDS_LIST = "stopword-list";

	/**
	 * Property key to specify if stopword list is case insensitive
	 */
	public static final String IGNORE_STOPWORD_CASE = "ignore-stopword-case";

	private static Class<? extends Pair> boolPair = Pair.makePair(true, true).getClass();

	private Properties props;
	private CharArraySet stopwords;

	public StopwordAnnotator() {
		this(new Properties());
	}

	public StopwordAnnotator(String notUsed, Properties props) {
		this(props);
	}
	public StopwordAnnotator(Properties props) {
		this.props = props;

		if (this.props.containsKey(STOPWORDS_LIST)) {
			String stopwordList = props.getProperty(STOPWORDS_LIST);
			boolean ignoreCase = Boolean.parseBoolean(props.getProperty(IGNORE_STOPWORD_CASE, "false"));
			this.stopwords = getStopWordList(Version.LUCENE_36, stopwordList, ignoreCase);
		} else {
			this.stopwords = (CharArraySet) StopAnalyzer.ENGLISH_STOP_WORDS_SET;
		}
	}

	@Override
	public void annotate(Annotation annotation) {
		if (stopwords != null && stopwords.size() > 0 && annotation.containsKey(CoreAnnotations.TokensAnnotation.class)) {
			List<CoreLabel> tokens = annotation.get(CoreAnnotations.TokensAnnotation.class);
			for (CoreLabel token : tokens) {
				boolean isWordStopword = stopwords.contains(token.word().toLowerCase());
				boolean isLemmaStopword = false;
				if (token.lemma() != null)
					isLemmaStopword = stopwords.contains(token.lemma().toLowerCase());
				Pair<Boolean, Boolean> pair = Pair.makePair(isWordStopword, isLemmaStopword);
				token.set(StopwordAnnotator.class, pair);
			}
		}
	}

	@Override
	public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
		return Collections.singleton(StopwordAnnotator.class);
	}

	@Override
	public Set<Class<? extends CoreAnnotation>> requires() {
		return Collections.unmodifiableSet(new ArraySet<>(Arrays.asList(
				CoreAnnotations.TextAnnotation.class,
				CoreAnnotations.TokensAnnotation.class,
				CoreAnnotations.LemmaAnnotation.class,
				CoreAnnotations.PartOfSpeechAnnotation.class
		)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Pair<Boolean, Boolean>> getType() {
		return (Class<Pair<Boolean, Boolean>>) boolPair;
	}

	public static CharArraySet getStopWordList(Version luceneVersion, String stopwordList, boolean ignoreCase) {
		String[] terms = stopwordList.split(",");
		CharArraySet stopwordSet = new CharArraySet(luceneVersion, terms.length, ignoreCase);
		for (String term : terms) {
			stopwordSet.add(term);
		}
		return CharArraySet.unmodifiableSet(stopwordSet);
	}
}
