package com.flair.server.pipelines.questgen;

import com.flair.server.utilities.ServerLogger;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap;

import java.util.List;

/*
 * Used to lazy-load parse trees of sentences containing co-reference mentions.
 */
class NerCorefAnnotation implements TypesafeMap.Key<NerCorefAnnotation> {
	interface LazyParseHandler {
		Annotation parse(String text);
	}

	private final CoreMap originalSentAnnotation;
	private final String resolvedSentText;
	private final LazyParseHandler parseHandler;
	private CoreMap resolvedSentAnnotation;
	private boolean alreadyParsed;

	private void parseResolvedText() {
		if (alreadyParsed)
			return;

		Annotation wrapper = parseHandler.parse(resolvedSentText);
		alreadyParsed = true;

		List<CoreMap> singletonList = wrapper.get(CoreAnnotations.SentencesAnnotation.class);
		if (singletonList.size() != 1) {
			ServerLogger.get()
					.warn("Parsing NerCorefAnnotation text generated multiple resultant sentences!")
					.indent()
					.warn("Original: " + originalSentAnnotation.toString())
					.warn("Resolved: " + resolvedSentText)
					.exdent();
			return;
		}

		resolvedSentAnnotation = singletonList.get(0);
	}

	NerCorefAnnotation(CoreMap originalSentAnnotation, String resolvedSentText, LazyParseHandler parseHandler) {
		this.originalSentAnnotation = originalSentAnnotation;
		this.resolvedSentText = resolvedSentText;
		this.parseHandler = parseHandler;
		this.resolvedSentAnnotation = null;
		this.alreadyParsed = false;
	}

	String resolvedText() {
		return resolvedSentText;
	}

	String originalText() {
		return originalSentAnnotation.toString();
	}

	Tree parseTree() {
		parseResolvedText();
		if (resolvedSentAnnotation != null)
			return resolvedSentAnnotation.get(TreeCoreAnnotations.TreeAnnotation.class);
		else
			return originalSentAnnotation.get(TreeCoreAnnotations.TreeAnnotation.class);
	}
}
