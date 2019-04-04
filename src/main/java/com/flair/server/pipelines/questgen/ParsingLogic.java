
package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.grammar.EnglishGrammaticalConstants;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.utilities.ServerLogger;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Perform NER and co-reference resolution to aid question generation.
 */
class ParsingLogic {
	private final AbstractDocument workingDoc;

	ParsingLogic(AbstractDocument doc) {
		workingDoc = doc;
	}

	static final class CorefReplacementSpan {
		int begin = -1;
		int end = -1;
		String replacement = "";

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CorefReplacementSpan that = (CorefReplacementSpan) o;

			if (begin != that.begin) return false;
			if (end != that.end) return false;
			return replacement.equals(that.replacement);

		}
		@Override
		public int hashCode() {
			int result = begin;
			result = 31 * result + end;
			result = 31 * result + replacement.hashCode();
			return result;
		}
	}


	private String getCorefMentionString(CorefChain.CorefMention mention, List<CoreMap> sentences, boolean suppressContractions) {
		CoreMap corefSentence = sentences.get(mention.sentNum - 1);
		List<CoreLabel> corefSentenceTokens = corefSentence.get(CoreAnnotations.TokensAnnotation.class);
		StringBuilder out = new StringBuilder();

		for (int i = mention.startIndex; i < mention.endIndex; i++) {
			String token = corefSentenceTokens.get(i - 1).word();
			if (suppressContractions && EnglishGrammaticalConstants.CONTRACTIONS.stream().anyMatch(token::equals))
				continue;

			out.append(token);
			if (EnglishGrammaticalConstants.CONTRACTIONS.stream().noneMatch(token::startsWith))
				out.append(" ");
		}

		return out.toString().trim();
	}

	private void collateCorefReplacements(CorefChain.CorefMention mention, String replacement,
	                                      List<CoreMap> sentences, Map<CoreMap, List<CorefReplacementSpan>> sentencesToResolve) {
		CoreMap corefSentence = sentences.get(mention.sentNum - 1);
		List<CoreLabel> corefSentenceTokens = corefSentence.get(CoreAnnotations.TokensAnnotation.class);
		int sentBegin = corefSentence.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class);

		CorefReplacementSpan span = new CorefReplacementSpan();
		span.replacement = replacement;

		if (mention.startIndex == mention.endIndex - 1) {
			CoreLabel token = corefSentenceTokens.get(mention.startIndex - 1);
			span.begin = token.beginPosition() - sentBegin;
			span.end = token.endPosition() - sentBegin;
		} else {
			CoreLabel firstToken = corefSentenceTokens.get(mention.startIndex - 1), lastToken = corefSentenceTokens.get(mention.endIndex - 2);
			span.begin = firstToken.beginPosition() - sentBegin;
			span.end = lastToken.endPosition() - sentBegin;
		}

		if (span.begin == -1 || span.end == -1)
			ServerLogger.get().warn("Coref replacement span for mention '" + mention.toString() + "' (" + corefSentence.toString() + ") invalid");
		else
			sentencesToResolve.computeIfAbsent(corefSentence, (e) -> new ArrayList<>()).add(span);
	}

	private Map<CoreMap, String> reconstructCorefSentences(Map<CoreMap, List<CorefReplacementSpan>> sentencesToResolve) {
		return sentencesToResolve.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> {
			// consolidate and sort spans
			List<CorefReplacementSpan> uniqueSpans = e.getValue().stream().distinct().sorted((a, b) -> {
				int result = Integer.compareUnsigned(a.begin, b.begin);
				if (result == 0)
					result = Integer.compareUnsigned(a.end, b.end);

				return result;
			}).collect(Collectors.toList());
			List<CorefReplacementSpan> delinquentSpans = new ArrayList<>();

			CorefReplacementSpan previous = null;
			for (CorefReplacementSpan current : uniqueSpans) {
				if (previous != null && current.begin < previous.end)
					delinquentSpans.add(current);
				else
					previous = current;
			}

			if (!delinquentSpans.isEmpty()) {
				ServerLogger.get().trace("Removed " + delinquentSpans.size() + " coref replacement spans for sentence '" + e.getKey().toString() + "'");
				for (CorefReplacementSpan span : delinquentSpans)
					uniqueSpans.remove(span);
			}

			// reconstruct the sentence with the corefs replaced
			String sentText = e.getKey().get(CoreAnnotations.TextAnnotation.class);
			StringBuilder resolvedSent = new StringBuilder();
			int lastOffset = 0;
			for (CorefReplacementSpan currentSpan : uniqueSpans) {
				resolvedSent.append(sentText, lastOffset, currentSpan.begin).append(currentSpan.replacement);
				lastOffset = currentSpan.end;
			}
			if (lastOffset < sentText.length())
				resolvedSent.append(sentText.substring(lastOffset));

			return resolvedSent.toString();
		}));
	}

	private void reparseAndUpdateOldAnnotations(Map<CoreMap, String> resolvedSentences, List<CoreMap> oldSentences,
	                                            CoreNlpParser parser) {
		for (Entry<CoreMap, String> sentPair : resolvedSentences.entrySet()) {
			Annotation sentAnnotationWrapper = new Annotation(sentPair.getValue());
			CoreMap oldSentAnnotation = sentPair.getKey();
			parser.pipeline().annotate(sentAnnotationWrapper);

			List<CoreMap> singletonList = sentAnnotationWrapper.get(CoreAnnotations.SentencesAnnotation.class);
			if (singletonList.size() != 1) {
				ServerLogger.get().warn("Reparsing resolved sentence '" + oldSentAnnotation.toString() + "' generated multiple resultant sentences!");
				continue;
			}

			CoreMap newSentAnnotation = singletonList.get(0);
			int oldSentIndex = oldSentAnnotation.get(CoreAnnotations.SentenceIndexAnnotation.class);
			if (oldSentIndex < 0 || oldSentIndex >= oldSentences.size() || oldSentences.get(oldSentIndex) != oldSentAnnotation) {
				ServerLogger.get().warn("Unresolved sentence index " + oldSentIndex + " is invalid!");
				continue;
			}

			// add the old sentence as an annotation for debugging
			newSentAnnotation.set(CoreAnnotations.DocIDAnnotation.class, oldSentAnnotation.toString());
			oldSentences.set(oldSentIndex, newSentAnnotation);
			ServerLogger.get().trace("Coref replacement:\n\tOld: " + oldSentAnnotation.toString() + "\n\tNew: " + newSentAnnotation.toString());
		}
	}

	public void apply(CoreNlpParser parser) {
		Annotation docAnnotation = new Annotation(workingDoc.getText());
		parser.pipeline().annotate(docAnnotation);

		List<CoreMap> sentences = docAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
		Map<Integer, CorefChain> corefs = docAnnotation.get(CorefCoreAnnotations.CorefChainAnnotation.class)
				.entrySet().stream().filter((e) -> e.getValue().getMentionsInTextualOrder().size() > 1)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		Map<CoreMap, List<CorefReplacementSpan>> modifiedSentences = new HashMap<>();

		// collect replacement spans for the sentences with coreferences
		for (CorefChain coref : corefs.values()) {
			CorefChain.CorefMention mostRepresentative = coref.getRepresentativeMention();
			String mostRepresentativeString = getCorefMentionString(mostRepresentative, sentences, true)
					.replaceAll("(^((\\p{Punct}|\\s)*\\b))|(\\b(\\p{Punct}|\\s)*$)", "");   // leading and trailing punctuation/whitespace

			for (CorefChain.CorefMention mention : coref.getMentionsInTextualOrder()) {
				if (mention == mostRepresentative)
					continue;
				else if (getCorefMentionString(mention, sentences, true).equals(mostRepresentativeString))
					continue;

				collateCorefReplacements(mention, mostRepresentativeString, sentences, modifiedSentences);
			}
		}

		Map<CoreMap, String> resolvedSents = reconstructCorefSentences(modifiedSentences);
		reparseAndUpdateOldAnnotations(resolvedSents, sentences, parser);

		workingDoc.flagAsParsed(parser.createAnnotations(docAnnotation));
	}
}
