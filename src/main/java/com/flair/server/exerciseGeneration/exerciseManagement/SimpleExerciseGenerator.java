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
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.ClozeManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.ConstructionPreparer;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.DistractorManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.FeedbackManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.InstructionsManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SimpleExerciseJsonManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

import edu.stanford.nlp.util.Pair;

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
	
	        if (isCancelled) {
	        	return null;
	        }
	        
	        NlpManager nlpManager = new NlpManager(parser, generator, settings.getExerciseSettings().getPlainText(), lemmatizer);
	        
	        new ConstructionPreparer().prepareConstructions(settings.getExerciseSettings(), nlpManager);	
	        
	        if (isCancelled) {
	        	return null;
	        }
	        
	        com.flair.shared.exerciseGeneration.Pair<ArrayList<Fragment>,com.flair.shared.exerciseGeneration.Pair<Integer,Integer>> res = new Indexer().matchHtmlToPlainText(settings.getExerciseSettings(), doc.wholeText(), nlpManager);
	
	        if (isCancelled) {
	        	return null;
	        }
	        
	        new ClozeManager().prepareBlanks(settings.getExerciseSettings(), nlpManager, res.first);
	        
	        if (isCancelled) {
	        	return null;
	        }
	        
	        ArrayList<Construction> usedConstructions 
	        		= new DistractorManager().generateDistractors(settings.getExerciseSettings(), nlpManager, res.first);
		    
	        if(usedConstructions == null || usedConstructions.size() == 0) {
	        	return null;
	        }
	        
	        if (isCancelled) {
	        	return null;
	        }
	        
	        MatchResult matchResult = new Matcher(res.first, res.second).prepareDomForSplitting(doc);
	        HtmlManager.removeNonText(matchResult.getTextBoundaries());
	        HtmlManager.removeNotDisplayedElements(doc);
	        if(settings.getExerciseSettings().getContentType().equals("Mark")) {
	        	HtmlManager.removeLinks(doc);
	        }
	        
	        if (isCancelled) {
	        	return null;
	        }
	        
	        ArrayList<DownloadedResource> resources = new HtmlManager().extractResources(settings.getExerciseSettings().getUrl(), 
	        		resourceDownloader, doc);
	        
	        if (isCancelled) {
	        	return null;
	        }
	        
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
	        
	        String taskDescription = InstructionsManager.componseTaskDescription(settings.getExerciseSettings(), nlpManager, res.first);	       
	
	        if (isCancelled) {
	        	return null;
	        }
	        
	        ArrayList<String> constructionTexts = new ArrayList<>();
	        ArrayList<ArrayList<String>> distractorTexts = new ArrayList<>();
	        for(Construction usedConstruction : usedConstructions) {
	        	constructionTexts.add(usedConstruction.getConstructionText());
	        	distractorTexts.add(usedConstruction.getDistractors());
	        }
	        
	        ArrayList<ArrayList<Pair<String, String>>> distractors = 
	        		new FeedbackManager().generateFeedback(usedConstructions, settings.getExerciseSettings(), nlpManager);
	        
	        if (isCancelled) {
	        	return null;
	        }
	        
	        return new JsonComponents(orderedPlainTextElements, pureHtmlElements, constructionTexts,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
	                distractors, taskDescription);
    	} else {
    		return null;
    	}
    }

	@Override
	public void cancelGeneration() {
		isCancelled = true;
	}

}