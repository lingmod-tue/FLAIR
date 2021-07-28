package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.HtmlManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Fragment;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.HtmlSplitter;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Indexer;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.MatchResult;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Matcher;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.SentenceManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SimpleExerciseJsonManager;
import com.flair.server.exerciseGeneration.exerciseManagement.temp.ClozeManager;
import com.flair.server.exerciseGeneration.exerciseManagement.temp.ConstructionPreparer;
import com.flair.server.exerciseGeneration.exerciseManagement.temp.DistractorManager;
import com.flair.server.exerciseGeneration.exerciseManagement.temp.FeedbackManager;
import com.flair.server.exerciseGeneration.exerciseManagement.temp.InstructionsManager;
import com.flair.server.exerciseGeneration.exerciseManagement.temp.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

public class SimpleExerciseGenerator extends ExerciseGenerator {

	private boolean isCancelled = false;
	
    @Override
	public byte[] generateExercise(ContentTypeSettings settings,
			CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
        JsonComponents jsonComponents = prepareExercise(settings, parser, generator, lemmatizer, resourceDownloader);

    	if(jsonComponents != null) {
	        ArrayList<JsonComponents> helper = new ArrayList<>();
	        helper.add(jsonComponents);
	        
	        ArrayList<Pair<String, byte[]>> relevantResources = getRelevantResources(settings.getResources());
	        return createH5pPackage(settings, helper, relevantResources);
    	} else {
    		return null;
    	}
	}

    /**
     * Extracts all exercise components relevant for the JSON configuration from the HTML based on the plain text.
     * @param settings  The content type settings
     * @return          The extracted exercise components relevant for the JSON configuration
     */
    public JsonComponents prepareExercise(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	if(settings.getDoc() != null) {
	        // We cannot operate on the same document for all exercises (in-place modifications), so we create a copy
	        Element doc = Jsoup.parse(settings.getDoc().toString());
	        doc = HtmlManager.makeHtmlEmbeddable(doc);
	
	        if (isCancelled) {
	        	return null;
	        }
	        
	        NlpManager nlpManager = new NlpManager(parser, generator, settings.getExerciseSettings().getPlainText(), lemmatizer);
	        ServerLogger.get().info("Start NLP processing: construction preparation");
	        new ConstructionPreparer().prepareConstructions(settings.getExerciseSettings(), nlpManager);	
	        ServerLogger.get().info("End NLP processing: construction preparation");

	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start matching");

	        Pair<ArrayList<Fragment>,Pair<Integer,Integer>> res = 
	        		new Indexer().matchHtmlToPlainText(settings.getExerciseSettings(), doc.wholeText(), nlpManager);
	        ServerLogger.get().info("End matching");

	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start NLP processing: brackets");

	        new ClozeManager().prepareBlanks(settings.getExerciseSettings(), nlpManager, res.first);
	        ServerLogger.get().info("End NLP processing: brackets");

	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start NLP processing: distractors");

	        DistractorManager distractorManager = new DistractorManager();
	        ArrayList<Construction> usedConstructions 
	        		= distractorManager.generateDistractors(settings.getExerciseSettings(), nlpManager, res.first);
	        ServerLogger.get().info("End NLP processing: distractors");

	        if(usedConstructions == null || usedConstructions.size() == 0) {
	        	return null;
	        }
	        
	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start HTML preparation");

	        MatchResult matchResult = new Matcher(res.first, res.second).prepareDomForSplitting(doc);
	        HtmlManager.removeNonText(matchResult.getTextBoundaries());
	        HtmlManager.removeNotDisplayedElements(doc);
	        if(settings.getExerciseSettings().getContentType().equals(ExerciseType.MARK)) {
	        	HtmlManager.removeLinks(doc);
	        }
	        ServerLogger.get().info("End HTML preparation");

	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start resource downloading");

	        ArrayList<DownloadedResource> resources = new HtmlManager().extractResources(settings.getExerciseSettings().getUrl(), 
	        		resourceDownloader, doc);
	        ServerLogger.get().info("End resource downloading");

	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start prepare JSON");

	        settings.getResources().addAll(resources);
	        SentenceManager sentenceManager = new SentenceManager();
	        ArrayList<String> sentenceHtml = sentenceManager.extractSentencesFromDom(matchResult.getSentenceBoundaryElements());

	        if (isCancelled) {
	        	return null;
	        }
	        
	        ArrayList<String> orderedPlainTextElements = new ArrayList<>();
	        ArrayList<String> pureHtmlElements = new HtmlSplitter().preparePureHtmlElements(doc.toString(), sentenceHtml,
	        		matchResult.getPlainTextElements(), orderedPlainTextElements);
	
	        if (isCancelled) {
	        	return null;
	        }
	        
	        if (settings.isEscapeAsterisksInHtml()) {
	            ((SimpleExerciseJsonManager)settings.getJsonManager()).escapeAsterisks(pureHtmlElements);
	        }
	
	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("End prepare JSON");
	        ServerLogger.get().info("Start NLP processing: task description");

	        String taskDescription = InstructionsManager.componseTaskDescription(settings.getExerciseSettings(), nlpManager, res.first);	       
	        ServerLogger.get().info("End NLP processing: task description");

	        if (isCancelled) {
	        	return null;
	        }
	        
	        ArrayList<String> constructionTexts = new ArrayList<>();
	        for(Construction usedConstruction : usedConstructions) {
	        	constructionTexts.add(usedConstruction.getConstructionText());
	        }
	        ServerLogger.get().info("Start generate feedback");

	        ArrayList<ArrayList<Pair<com.flair.shared.exerciseGeneration.Pair<String,Boolean>,String>>> distractors = 
	        		new FeedbackManager().generateFeedback(usedConstructions, settings.getExerciseSettings(), nlpManager);
	        ServerLogger.get().info("End generate feedback");

	        if (isCancelled) {
	        	return null;
	        }
	        ServerLogger.get().info("Start NLP processing: select distractors");

	        ArrayList<ArrayList<Pair<String,String>>> usedDistractors = 
	        		distractorManager.chooseDistractors(
	        				distractors, settings.getExerciseSettings().getnDistractors(), settings.getExerciseSettings().getContentType().equals(ExerciseType.SINGLE_CHOICE));
	        ServerLogger.get().info("End NLP processing: select distractors");

	        return new JsonComponents(orderedPlainTextElements, pureHtmlElements, constructionTexts,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
	                usedDistractors, taskDescription);
    	} else {
    		return null;
    	}
    }

	@Override
	public void cancelGeneration() {
		isCancelled = true;
	}

}