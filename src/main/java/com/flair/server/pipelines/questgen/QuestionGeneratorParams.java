package com.flair.server.pipelines.questgen;

import com.flair.server.utilities.ServerLogger;
import edu.cmu.ark.ResourceLoader;

class QuestionGeneratorParams {
	boolean dropPronouns;
	boolean downweighPronouns;
	boolean downweighFrequentWords;
	boolean preferWHQuestions;
	boolean onlyWHQuestions;
	boolean resolveNonPronounNPs;
	boolean resolvePronounNPs;
	boolean doStemming;
	String rankerModelPath;
	QuestionKind type;

	public static final class Builder {
		boolean dropPronouns = true;
		boolean downweighPronouns = false;
		boolean downweighFrequentWords = true;
		boolean preferWHQuestions = false;
		boolean onlyWHQuestions = false;
		boolean resolveNonPronounNPs = true;
		boolean resolvePronounNPs = true;
		boolean doStemming = true;
		String rankerModelPath = ResourceLoader.path("linear-regression-ranker-reg500.ser.gz");
		QuestionKind type = QuestionKind.READING_COMPREHENSION;

		private Builder() {}
		public static Builder factory() { return new Builder(); }
		public Builder dropPronouns(boolean dropPronouns) {
			this.dropPronouns = dropPronouns;
			return this;
		}
		public Builder downweighPronouns(boolean downweighPronouns) {
			this.downweighPronouns = downweighPronouns;
			return this;
		}
		public Builder downweighFrequentWords(boolean downweighFrequentWords) {
			this.downweighFrequentWords = downweighFrequentWords;
			return this;
		}
		public Builder preferWHQuestions(boolean preferWHQuestions) {
			this.preferWHQuestions = preferWHQuestions;
			return this;
		}
		public Builder onlyWHQuestions(boolean onlyWHQuestions) {
			this.onlyWHQuestions = onlyWHQuestions;
			return this;
		}
		public Builder resolveNonPronounNPs(boolean resolveNonPronounNPs) {
			this.resolveNonPronounNPs = resolveNonPronounNPs;
			return this;
		}
		public Builder resolvePronounNPs(boolean resolvePronounNPs) {
			this.resolvePronounNPs = resolvePronounNPs;
			return this;
		}
		public Builder doStemming(boolean doStemming) {
			this.doStemming = doStemming;
			return this;
		}
		public Builder rankerModelPath(String rankerModelPath) {
			this.rankerModelPath = rankerModelPath;
			return this;
		}
		public Builder type(QuestionKind type) {
			this.type = type;
			return this;
		}
		public QuestionGeneratorParams build() {
			if (rankerModelPath.isEmpty())
				ServerLogger.get().warn("No QuestionRanker model set. Generated questions will not be ranked!");

			QuestionGeneratorParams questionGeneratorParams = new QuestionGeneratorParams();
			questionGeneratorParams.doStemming = this.doStemming;
			questionGeneratorParams.resolvePronounNPs = this.resolvePronounNPs;
			questionGeneratorParams.type = this.type;
			questionGeneratorParams.dropPronouns = this.dropPronouns;
			questionGeneratorParams.resolveNonPronounNPs = this.resolveNonPronounNPs;
			questionGeneratorParams.downweighPronouns = this.downweighPronouns;
			questionGeneratorParams.preferWHQuestions = this.preferWHQuestions;
			questionGeneratorParams.downweighFrequentWords = this.downweighFrequentWords;
			questionGeneratorParams.onlyWHQuestions = this.onlyWHQuestions;
			questionGeneratorParams.rankerModelPath = this.rankerModelPath;
			return questionGeneratorParams;
		}
	}
}
