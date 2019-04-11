package com.flair.server.pipelines.questgen;

import com.flair.server.utilities.ServerLogger;
import edu.cmu.ark.ResourceLoader;

class QuestionGeneratorParams {
	boolean avoidDemonstratives;
	boolean dropPronouns;
	boolean downweighPronouns;
	boolean downweighFrequentWords;
	boolean preferWHQuestions;
	boolean onlyWHQuestions;
	boolean doStemming;
	boolean avoidSimpleQuestions;   // of the form "W* VB* *?"
	String rankerModelPath;
	QuestionKind type;

	public static final class Builder {
		boolean avoidDemonstratives = true;
		boolean dropPronouns = false;
		boolean downweighPronouns = true;
		boolean downweighFrequentWords = true;
		boolean preferWHQuestions = true;
		boolean onlyWHQuestions = false;
		boolean doStemming = true;
		boolean avoidSimpleQuestions = true;
		String rankerModelPath = ResourceLoader.path("linear-regression-ranker-reg500.ser.gz");
		QuestionKind type = QuestionKind.READING_COMPREHENSION;

		private Builder() {}
		public static Builder factory() { return new Builder(); }
		public Builder avoidDemonstratives(boolean dropPronouns) {
			this.avoidDemonstratives = dropPronouns;
			return this;
		}
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
		public Builder doStemming(boolean doStemming) {
			this.doStemming = doStemming;
			return this;
		}
		public Builder avoidSimpleQuestions(boolean avoidSimpleQuestions) {
			this.avoidSimpleQuestions = avoidSimpleQuestions;
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
			questionGeneratorParams.avoidDemonstratives = this.avoidDemonstratives;
			questionGeneratorParams.doStemming = this.doStemming;
			questionGeneratorParams.type = this.type;
			questionGeneratorParams.dropPronouns = this.dropPronouns;
			questionGeneratorParams.downweighPronouns = this.downweighPronouns;
			questionGeneratorParams.preferWHQuestions = this.preferWHQuestions;
			questionGeneratorParams.downweighFrequentWords = this.downweighFrequentWords;
			questionGeneratorParams.onlyWHQuestions = this.onlyWHQuestions;
			questionGeneratorParams.avoidSimpleQuestions = this.avoidSimpleQuestions;
			questionGeneratorParams.rankerModelPath = this.rankerModelPath;
			return questionGeneratorParams;
		}
	}
}
