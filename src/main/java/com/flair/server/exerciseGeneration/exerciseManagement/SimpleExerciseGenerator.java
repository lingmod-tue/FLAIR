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
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.InstructionsManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SimpleExerciseJsonManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;

import edu.stanford.nlp.util.Pair;

public class SimpleExerciseGenerator extends ExerciseGenerator {

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
	
	        NlpManager nlpManager = new NlpManager(parser, generator, settings.getExerciseSettings().getPlainText(), lemmatizer);
	        
	        new ConstructionPreparer().prepareConstructions(settings.getExerciseSettings(), nlpManager);	
	        com.flair.shared.exerciseGeneration.Pair<ArrayList<Fragment>,com.flair.shared.exerciseGeneration.Pair<Integer,Integer>> res = new Indexer().matchHtmlToPlainText(settings.getExerciseSettings(), doc.wholeText(), nlpManager);
	
	        new ClozeManager().prepareBlanks(settings.getExerciseSettings(), nlpManager, res.first);
	        com.flair.shared.exerciseGeneration.Pair<ArrayList<String>,ArrayList<ArrayList<String>>> usedConstructions 
	        		= new DistractorManager().generateDistractors(settings.getExerciseSettings(), nlpManager, res.first);
		    
	        if(usedConstructions == null || usedConstructions.first.size() == 0) {
	        	return null;
	        }
	        
	        MatchResult matchResult = new Matcher(res.first, res.second).prepareDomForSplitting(doc);
	        HtmlManager.removeNonText(matchResult.getTextBoundaries());
	        HtmlManager.removeNotDisplayedElements(doc);
	        if(settings.getExerciseSettings().getContentType().equals("Mark")) {
	        	HtmlManager.removeLinks(doc);
	        }
	        
	        ArrayList<DownloadedResource> resources = new HtmlManager().extractResources(settings.getExerciseSettings().getUrl(), 
	        		resourceDownloader, doc);
	        settings.getResources().addAll(resources);
	        SentenceManager sentenceManager = new SentenceManager();
	        ArrayList<String> sentenceHtml = sentenceManager.extractSentencesFromDom(matchResult.getSentenceBoundaryElements());
	        HtmlSplitter splitter = new HtmlSplitter();
	
	        ArrayList<String> orderedPlainTextElements = new ArrayList<>();
	        ArrayList<String> pureHtmlElements = splitter.preparePureHtmlElements(doc.toString(), sentenceHtml,
	        		matchResult.getPlainTextElements(), orderedPlainTextElements);
	
	        if (settings.isEscapeAsterisksInHtml()) {
	            ((SimpleExerciseJsonManager)settings.getJsonManager()).escapeAsterisks(pureHtmlElements);
	        }
	
	        String taskDescription = InstructionsManager.componseTaskDescription(settings.getExerciseSettings(), nlpManager, res.first);	       
	
	        return new JsonComponents(orderedPlainTextElements, pureHtmlElements, usedConstructions.first,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
	                usedConstructions.second, taskDescription);
    	} else {
    		return null;
    	}
    }

}