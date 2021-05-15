package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;
import java.util.HashSet;

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
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.PlainTextPreparer;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.SentenceManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.ClozeManager;
import com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation.DistractorManager;
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

	        // Remove overlapping constructions (can occur e.g. with synthetic and analytic comparatives)
	        HashSet<Construction> constructionsToRemove = new HashSet<>();
	        for(Construction construction : settings.getExerciseSettings().getConstructions()) {
	        	for(Construction otherConstruction : settings.getExerciseSettings().getConstructions()) {
	        		if(construction != otherConstruction && 
	        				(Math.max(construction.getConstructionIndices().first, otherConstruction.getConstructionIndices().first) < 
	        						Math.min(construction.getConstructionIndices().second, otherConstruction.getConstructionIndices().second))) {
	        					if(construction.getConstructionIndices().second - construction.getConstructionIndices().first > 
	        							otherConstruction.getConstructionIndices().second - otherConstruction.getConstructionIndices().first) {
	        						constructionsToRemove.add(otherConstruction);
	        					} else {
	        						constructionsToRemove.add(construction);
	        					}
	        		}

	        	}
	        }
	        
	        for(Construction construction : constructionsToRemove) {
	        	settings.getExerciseSettings().getConstructions().remove(construction);
	        }
	        
	        ClozeManager clozeManager = new ClozeManager();
	        clozeManager.prepareBlanks(settings.getExerciseSettings(), nlpManager);
	        DistractorManager distractorManager = new DistractorManager(); // we need the original indices, so we need to generate distractors for all possible blanks
	        distractorManager.generateDistractors(settings.getExerciseSettings(), nlpManager);
	
	        PlainTextPreparer plainTextPreparer = new PlainTextPreparer(); 
	        plainTextPreparer.prepareIndices(settings.getExerciseSettings(), nlpManager);
	
	        Indexer indexer = new Indexer();
	        ArrayList<Fragment> res = indexer.matchHtmlToPlainText(settings.getExerciseSettings(), doc.wholeText());
	
	        Matcher matcher = new Matcher(res);
	        MatchResult matchResult = matcher.prepareDomForSplitting(doc);
	        HtmlManager htmlManager = new HtmlManager();
	        ArrayList<DownloadedResource> resources = htmlManager.extractResources(settings.getExerciseSettings().getUrl(), resourceDownloader, doc);
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
	
	        ArrayList<String> usedConstructions = updateConstructions(settings.getExerciseSettings(), matchResult.getConstructions());
	        String taskDescription = InstructionsManager.componseTaskDescription(settings.getExerciseSettings(), nlpManager);	       
	
	        return new JsonComponents(orderedPlainTextElements, pureHtmlElements, usedConstructions,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), assembleDistractors(settings.getExerciseSettings()), taskDescription);
    	} else {
    		return null;
    	}
    }
    
    /**
     * Collects the distractors for Single Choice exercises from the individual constructions into a single ArrayList.
     * @param settings	The exercise settings
     * @return			The distractors per construction
     */
    private ArrayList<ArrayList<String>> assembleDistractors(ExerciseSettings settings) {
        ArrayList<ArrayList<String>> distractors = new ArrayList<>();
        if(settings.getContentType().equals("Select")) {
            for(Construction construction : settings.getConstructions()) {
                distractors.add(construction.getDistractors());
            }
        }

        return distractors;
    }
    
    /**
     * Removes unused constructions and sets the construction text for the remaining constructions.
     * @param settings                  The exercise settings containing the constructions
     * @param arrayList         The used constructions with their index in the original list of all constructions in the exercise settings
     */
    private ArrayList<String> updateConstructions(ExerciseSettings settings, ArrayList<com.flair.shared.exerciseGeneration.Pair<String, Integer>> arrayList) {
        ArrayList<String> usedConstructionTexts = new ArrayList<>();
        ArrayList<Construction> constructionsToRemove = new ArrayList<>();
        for(int i = 0; i < settings.getConstructions().size(); i++) {
            com.flair.shared.exerciseGeneration.Pair<String, Integer> matchingConstruction = null;
            for(com.flair.shared.exerciseGeneration.Pair<String, Integer> usedConstruction : arrayList) {
                if(usedConstruction.second == i) {
                    matchingConstruction = usedConstruction;
                    break;
                }
            }
            if(matchingConstruction != null) {
                settings.getConstructions().get(i).setConstructionText(matchingConstruction.first);
                usedConstructionTexts.add(matchingConstruction.first);
            } else {
                // we want to make sure that we don't corrupt indices by removing elements by index, so we better save the elements to remove
                constructionsToRemove.add(settings.getConstructions().get(i));
            }
        }

        for(Construction construction : constructionsToRemove) {
            settings.getConstructions().remove(construction);
        }

        return usedConstructionTexts;
    }

}