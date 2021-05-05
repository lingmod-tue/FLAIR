package com.flair.server.exerciseGeneration.exerciseManagement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.HtmlManager;
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
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SimpleExerciseJsonManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

import edu.stanford.nlp.util.Pair;

import java.util.ArrayList;

public class SimpleExerciseGenerator extends ExerciseGenerator {

    @Override
	public byte[] generateExercise(ContentTypeSettings settings, ArrayList<Pair<String, byte[]>> resources, 
			CoreNlpParser parser, SimpleNlgParser generator) {
        JsonComponents jsonComponents = prepareExercise(settings, parser, generator);

    	if(jsonComponents != null) {
	        ArrayList<JsonComponents> helper = new ArrayList<>();
	        helper.add(jsonComponents);
	        return createH5pPackage(settings, helper, resources);
    	} else {
    		return null;
    	}
	}

    /**
     * Extracts all exercise components relevant for the JSON configuration from the HTML based on the plain text.
     * @param settings  The content type settings
     * @return          The extracted exercise components relevant for the JSON configuration
     */
    public JsonComponents prepareExercise(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator) {
    	if(settings.getDoc() != null) {
	        // We cannot operate on the same document for all exercises (in-place modifications), so we create a copy
	        Element doc = Jsoup.parse(settings.getDoc().toString());
	
	        ClozeManager clozeManager = new ClozeManager();
	        clozeManager.prepareBlanks(settings.getExerciseSettings(), parser, generator);
	
	        PlainTextPreparer plainTextPreparer = new PlainTextPreparer(); 
	        plainTextPreparer.prepareIndices(settings.getExerciseSettings());
	
	        Indexer indexer = new Indexer();
	        ArrayList<Fragment> res = indexer.matchHtmlToPlainText(settings.getExerciseSettings(), doc.wholeText());
	
	        Matcher matcher = new Matcher(res);
	        MatchResult matchResult = matcher.prepareDomForSplitting(doc);
	        doc = HtmlManager.makeHtmlEmbeddable(doc);
	        SentenceManager sentenceManager = new SentenceManager();
	        ArrayList<String> sentenceHtml = sentenceManager.extractSentencesFromDom(matchResult.getSentenceBoundaryElements());
	        HtmlSplitter splitter = new HtmlSplitter();
	
	        ArrayList<ArrayList<String>> plainTextPerSentence = new ArrayList<>();
	        ArrayList<String> pureHtmlElements = splitter.preparePureHtmlElements(doc.toString(), sentenceHtml,
	                matchResult.getPlainTextElements(), plainTextPerSentence);
	
	        if (settings.isEscapeAsterisksInHtml()) {
	            ((SimpleExerciseJsonManager)settings.getJsonManager()).escapeAsterisks(pureHtmlElements);
	        }
	
	        ArrayList<String> usedConstructions = updateConstructions(settings.getExerciseSettings(), matchResult.getConstructions());
	        String taskDescription = InstructionsManager.componseTaskDescription(settings.getExerciseSettings());
	        ArrayList<ArrayList<String>> distractors =
	        		DistractorManager.generateDistractors(settings.getExerciseSettings());
	
	        return new JsonComponents(plainTextPerSentence, pureHtmlElements, usedConstructions,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), distractors, taskDescription);
    	} else {
    		return null;
    	}
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