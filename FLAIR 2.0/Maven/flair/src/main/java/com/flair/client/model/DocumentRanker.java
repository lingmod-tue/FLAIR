package com.flair.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flair.client.model.interfaces.AbstractDocumentRanker;
import com.flair.client.model.interfaces.DocumentRankerInput;
import com.flair.client.model.interfaces.DocumentRankerOutput;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.parser.DocumentReadabilityLevel;

/*
 * Implements language agnostic ranking functionality
 */
public class DocumentRanker implements AbstractDocumentRanker
{
	private static class WeightData
	{
		public final double		weight;
		public double			df;
		public double			idf;

		public WeightData(double w)
		{
			weight = w;
			df = idf = 0;
		}
	}

	private static class RankerWeights
	{
		public final WeightData			docLevelA;
		public final WeightData			docLevelB;
		public final WeightData			docLevelC;
		public final WeightData			keywords;
		public final Map<GrammaticalConstruction, WeightData> 	gram;

		public RankerWeights(DocumentRankerInput.Rank input)
		{
			double maxWeight = input.getMaxWeight();

			docLevelA = new WeightData(input.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A) ? 1 : 0);
			docLevelB = new WeightData(input.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B) ? 1 : 0);
			docLevelC = new WeightData(input.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C) ? 1 : 0);
			keywords = new WeightData(input.getKeywordWeight() / maxWeight);

			gram = new EnumMap<>(GrammaticalConstruction.class);
			for (GrammaticalConstruction itr : input.getConstructions())
				gram.put(itr, new WeightData(input.getConstructionWeight(itr) / maxWeight));

		}

		private WeightData getDocLevelWeight(RankableDocument doc)
		{
			WeightData data;
			switch (doc.getReadabilityLevel())
			{
			case LEVEL_A:
				return docLevelA;
			case LEVEL_B:
				return docLevelB;
			case LEVEL_C:
				return docLevelC;
			default:
				return null;
			}
		}

		public boolean isDocLevelFiltered(RankableDocument doc) {
			return getDocLevelWeight(doc).weight == 0;
		}

		private void incrementDocLevelDf(RankableDocument doc) {
			getDocLevelWeight(doc).df += 1;
		}

		private void incrementGramDf(RankableDocument doc)
		{
			for (GrammaticalConstruction itr : doc.getConstructions())
				gram.get(itr).df += 1;
		}

		private void incrementKeywordDf(RankableDocument doc)
		{
			if (doc.getKeywordCount() > 0)
				keywords.df += 1;
		}

		private void calcIdf(WeightData w, double docCount)
		{
			if (w.df != 0)
				w.idf = Math.log((docCount) / w.df);
			else
				w.idf = 0;
		}

		public void updateDfIdf(Collection<RankableDocument> docs)
		{
			for (RankableDocument doc : docs)
			{
				// update the df scores of all weights
				incrementDocLevelDf(doc);
				incrementKeywordDf(doc);
				incrementGramDf(doc);
			}

			// update idfs
			int docCount = docs.size() + 1;
			calcIdf(docLevelA, docCount);
			calcIdf(docLevelB, docCount);
			calcIdf(docLevelC, docCount);
			calcIdf(keywords, docCount);

			for (WeightData itr : gram.values())
				calcIdf(itr, docCount);
		}

		public WeightData getConstructionWeight(GrammaticalConstruction val) {
			return gram.get(val);
		}
	}

	private static class RankerScores
	{
		class Score
		{
			public double s;

			Score() {
				s = 0d;
			}
		}

		public final Map<RankableDocument, Score>	scores;

		public RankerScores(Iterable<RankableDocument> input)
		{
			scores = new HashMap<>();

			for (RankableDocument itr : input)
				scores.put(itr, new Score());
		}
	}

	private static class RankOperationOutput implements DocumentRankerOutput.Rank
	{
		public final List<RankableDocument>		docs;
		public final RankerWeights				weights;

		RankOperationOutput(List<RankableDocument> d, RankerWeights w)
		{
			docs = d;
			weights = w;
		}

		@Override
		public Collection<RankableDocument> getRankedDocuments() {
			return docs;
		}

		@Override
		public double getDocLevelDf(DocumentReadabilityLevel level)
		{
			switch (level)
			{
			case LEVEL_A:
				return weights.docLevelA.df;
			case LEVEL_B:
				return weights.docLevelB.df;
			case LEVEL_C:
				return weights.docLevelC.df;
			default:
				return 0;
			}
		}

		@Override
		public double getConstructionDf(GrammaticalConstruction gram) {
			return weights.gram.get(gram).df;
		}

		@Override
		public double getConstructionWeight(GrammaticalConstruction gram) {
			WeightData w = weights.getConstructionWeight(gram);
			if (w == null)
				return 0;
			else
				return w.weight;
		}

		@Override
		public double getKeywordWeight() {
			return weights.keywords.weight;
		}

		@Override
		public boolean isConstructionWeighted(GrammaticalConstruction gram) {
			return getConstructionWeight(gram) != 0;
		}

		@Override
		public boolean isKeywordWeighted() {
			return weights.keywords.weight != 0;
		}
	}

	private static final double			LENGTH_PARAM_MULTIPLIER = 10;		// ### what's this?
	private static final double			TF_NORM_MULTIPLIER = 1.7;			// ### what's this?


	private boolean isDocConstructionFiltered(DocumentRankerInput.Rank input, RankableDocument doc)
	{
		for (GrammaticalConstruction itr : doc.getConstructions())
		{
			if (input.hasConstructionSlider(itr) && input.isConstructionEnabled(itr) == false)
				return true;
		}

		return false;
	}

	@Override
	public DocumentRankerOutput.Rank rerank(DocumentRankerInput.Rank input)
	{
		RankerWeights weights = new RankerWeights(input);
		RankOperationOutput out = new RankOperationOutput(new ArrayList<>(), weights);

		double lengthParam = input.getDocLengthWeight() / LENGTH_PARAM_MULTIPLIER;
		double avDocLenAccum = -1;

		// perform filtering
		for (RankableDocument itr : input.getDocuments())
		{
			if (input.isDocumentFiltered(itr))
				continue;
			else if (weights.isDocLevelFiltered(itr))
				continue;
			else if (isDocConstructionFiltered(input, itr))
				continue;

			out.docs.add(itr);
		}

		// update df+idf of weights
		RankerScores scores = new RankerScores(out.docs);
		weights.updateDfIdf(out.docs);

		// update avg. doc length
		for (RankableDocument itr : out.docs)
			avDocLenAccum += itr.getNumWords();

		if (out.docs.size() > 0)
			avDocLenAccum /= out.docs.size();
		else
			avDocLenAccum = 0;

		// calculate scores
		final double avgDocLen = avDocLenAccum;
		class BoolWrapper {
			boolean b;

			public BoolWrapper(boolean b) {
				this.b = b;
			}
		}
		final BoolWrapper hasGramScore = new BoolWrapper(weights.keywords.weight != 0);
		for (RankableDocument itr : out.docs)
		{
			RankerScores.Score score = scores.scores.get(itr);
			// accumulate gram construction score
			weights.gram.forEach((g, w) -> {
				if (w.weight != 0 && w.df > 0)
				{
					hasGramScore.b = true;
					if (itr.hasConstruction(g))
					{
						double tf = itr.getConstructionFreq(g);
						double idf = w.idf;
						double tfNorm = ((TF_NORM_MULTIPLIER + 1) * tf) / (tf + TF_NORM_MULTIPLIER * (1 - lengthParam + lengthParam * (itr.getNumWords() / avgDocLen)));
						double gramScore = tfNorm * idf;

						score.s += gramScore * w.weight;
					}
				}
			});

			// accumulate keyword score
			{
				double tf = itr.getKeywordCount();
				double idf = weights.keywords.idf;
				double tfNorm = ((TF_NORM_MULTIPLIER + 1) * tf) / (tf + TF_NORM_MULTIPLIER * (1 - lengthParam + lengthParam * (itr.getNumWords() / avgDocLen)));
				double gramScore = tfNorm * idf;

				score.s += gramScore * weights.keywords.weight;
			}
		}

		// sort docs by weight
		if (hasGramScore.b)
		{
			Collections.sort(out.docs, (a, b) -> {
				return (int)(scores.scores.get(a).s - scores.scores.get(b).s);
			});
		}
		else if (lengthParam != 0)
		{
			Collections.sort(out.docs, (a, b) -> {
				return (int)(a.getNumWords() - b.getNumWords());
			});
		}
		else
		{
			Collections.sort(out.docs, (a, b) -> {
				return a.getRank() - b.getRank();
			});
		}

		return out;
	}
}
