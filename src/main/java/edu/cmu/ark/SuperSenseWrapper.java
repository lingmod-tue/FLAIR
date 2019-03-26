package edu.cmu.ark;

import arkref.parsestuff.AnalysisUtilities;
import com.flair.server.utilities.ServerLogger;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;


public class SuperSenseWrapper {
	private SuperSenseWrapper() {
		DiscriminativeTagger.loadProperties(ResourceLoader.path("SuperSenseWrapper.properties"));
		sst = DiscriminativeTagger.loadModel(ResourceLoader.path("superSenseModelAllSemcor.ser.gz"));
	}

	public static SuperSenseWrapper getInstance() {
		if (instance == null) {
			synchronized (SuperSenseWrapper.class) {
				if (instance == null)
					instance = new SuperSenseWrapper();
			}
		}
		return instance;
	}

	private LabeledSentence generateSupersenseTaggingInput(Tree sentence) {
		LabeledSentence res = new LabeledSentence();
		List<Tree> leaves = sentence.getLeaves();

		for (Tree leave : leaves) {
			String word = leave.label().value();
			Tree preterm = leave.parent(sentence);
			String pos = preterm.label().value();
			String stem = AnalysisUtilities.getLemma(word, pos);
			res.addToken(word, stem, pos, "0");
		}

		return res;
	}


	public List<String> annotateMostFrequentSenses(Tree sentence) {
		int numleaves = sentence.getLeaves().size();
		if (numleaves <= 1) {
			return new ArrayList<String>();
		}
		LabeledSentence labeled = generateSupersenseTaggingInput(sentence);
		labeled.setMostFrequentSenses(SuperSenseFeatureExtractor.getInstance().extractFirstSensePredictedLabels(labeled));
		return labeled.getMostFrequentSenses();
	}

	public List<String> annotateSentenceWithSupersenses(Tree sentence) {
		List<String> result = new ArrayList<String>();

		int numleaves = sentence.getLeaves().size();
		if (numleaves <= 1) {
			return result;
		}
		LabeledSentence labeled = generateSupersenseTaggingInput(sentence);
		synchronized (this) {
			sst.findBestLabelSequenceViterbi(labeled, sst.getWeights());
		}
		result.addAll(labeled.getPredictions());

		//add a bunch of blanks if necessary
		while (result.size() < numleaves) result.add("0");

		if (GlobalProperties.getInstance().getDebug()) ServerLogger.get().info("annotateSentenceSST: " + result);
		return result;
	}


	private DiscriminativeTagger sst;
	private static SuperSenseWrapper instance;
}



