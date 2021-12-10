package com.flair.server.exerciseGeneration.exerciseManagement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.OutputComponents;
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
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ClozeManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparer;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DistractorManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.FeedbackManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.InstructionsManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

public class SimpleExerciseGenerator extends ExerciseGenerator {

	private boolean isCancelled = false;
	
    @Override
	public ArrayList<OutputComponents> generateExercise(ContentTypeSettings settings,
			CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
        JsonComponents jsonComponents = prepareExercise(settings, parser, generator, lemmatizer, resourceDownloader);

    	if(jsonComponents != null) {
	        ArrayList<JsonComponents> helper = new ArrayList<>();
	        helper.add(jsonComponents);
	        
	        ArrayList<Pair<String, byte[]>> relevantResources = getRelevantResources(settings.getResources());
    		ArrayList<OutputComponents> exercises = new ArrayList<>();
	        OutputComponents output = createH5pPackage(settings, helper, relevantResources);
	        if(output != null) {
	        	output.setXmlFile(writeXmlToFile(output.getFeedBookXml()));
	        }
        	exercises.add(output);

	        return exercises;
    	} else {
    		return null;
    	}
	}
    
    private HashMap<String, byte[]> writeXmlToFile(HashMap<String, String> xml) {
    	if(xml == null) {
    		return null;
    	}
    	
    	HashMap<String, byte[]> files = new HashMap<>();
    	
    	for(Entry<String, String> entry : xml.entrySet()) {
    		files.put(entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
    	}
    	return files;
    }

    /**
     * Extracts all exercise components relevant for the JSON configuration from the HTML based on the plain text.
     * @param settings  The content type settings
     * @return          The extracted exercise components relevant for the JSON configuration
     */
    public JsonComponents prepareExercise(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	ExerciseSettings exerciseSettings = (ExerciseSettings)settings.getExerciseSettings();
    	if(settings.getDoc() != null) {
	        // We cannot operate on the same document for all exercises (in-place modifications), so we create a copy
	        Element doc = Jsoup.parse(settings.getDoc().toString());
	        doc = HtmlManager.makeHtmlEmbeddable(doc);
	
	        if (isCancelled) {
	        	return null;
	        }
	        
	        //TODO: disable for generation with FeedBook config
	        NlpManager nlpManager = new NlpManager(parser, generator, exerciseSettings.getPlainText(), lemmatizer);
	        new ConstructionPreparer().prepareConstructions(exerciseSettings, nlpManager);	

	        if (isCancelled) {
	        	return null;
	        }

	        Pair<ArrayList<Fragment>,Pair<Integer,Integer>> res = 
	        		new Indexer().matchHtmlToPlainText(exerciseSettings, doc.wholeText(), nlpManager);

	        if (isCancelled) {
	        	return null;
	        }

	        //TODO: create alternative ClozeManager which adds brackets generated from FeedBook config
	        new ClozeManager().prepareBlanks(exerciseSettings, nlpManager, res.first);

	        if (isCancelled) {
	        	return null;
	        }

	        //TODO: create alternative DistractorManager which adds distractors generated from FeedBook config
	        DistractorManager distractorManager = new DistractorManager();
	        ArrayList<Construction> usedConstructions 
	        		= distractorManager.generateDistractors(exerciseSettings, nlpManager, res.first);

	        if(usedConstructions == null || usedConstructions.size() == 0) {
	        	return null;
	        }
	        
	        if (isCancelled) {
	        	return null;
	        }

	        MatchResult matchResult = new Matcher(res.first, res.second).prepareDomForSplitting(doc);
	        HtmlManager.removeNonText(matchResult.getTextBoundaries());
	        HtmlManager.removeNotDisplayedElements(doc);
	        if(exerciseSettings.getContentType().equals(ExerciseType.MARK)) {
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

	        //TODO: create alternative InstructionsManager which adds instructionLemmas and returns instructions generated from FeedBook config
	        edu.stanford.nlp.util.Pair<String, ArrayList<String>> taskDescription = InstructionsManager.composeTaskDescription((ExerciseSettings)settings.getExerciseSettings(), nlpManager, res.first);	       

	        if (isCancelled) {
	        	return null;
	        }
	        
	        ArrayList<Pair<String, Integer>> constructionTexts = new ArrayList<>();
	        for(Construction usedConstruction : usedConstructions) {
	        	constructionTexts.add(new Pair<>(usedConstruction.getConstructionText(), usedConstruction.getSentenceIndex()));
	        }

	        ArrayList<ArrayList<Pair<com.flair.shared.exerciseGeneration.Pair<String,Boolean>,String>>> distractors = 
	        		new FeedbackManager().generateFeedback(usedConstructions, exerciseSettings, nlpManager);

	        if (isCancelled) {
	        	return null;
	        }

	        ArrayList<ArrayList<Pair<String,String>>> usedDistractors = 
	        		distractorManager.chooseDistractors(
	        				distractors, exerciseSettings.getnDistractors(), exerciseSettings.getContentType().equals(ExerciseType.SINGLE_CHOICE));

	        return new JsonComponents(orderedPlainTextElements, pureHtmlElements, constructionTexts,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
	                usedDistractors, taskDescription.first, taskDescription.second);
    	} else {
    		return null;
    	}
    }

	@Override
	public void cancelGeneration() {
		isCancelled = true;
	}

}