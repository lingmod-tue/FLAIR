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

import edu.stanford.nlp.util.Pair;

import java.util.ArrayList;

public class SimpleExerciseGenerator extends ExerciseGenerator {

    @Override
	public byte[] generateExercise(ContentTypeSettings settings, ArrayList<Pair<String, byte[]>> resources) {
        JsonComponents jsonComponents = prepareExercise(settings);

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
    public JsonComponents prepareExercise(ContentTypeSettings settings) {
    	if(settings.getDoc() != null) {
	        // We cannot operate on the same document for all exercises (in-place modifications), so we create a copy
	        Element doc = Jsoup.parse(settings.getDoc().toString());
	
	        ClozeManager clozeManager = new ClozeManager();
	        clozeManager.prepareBlanks(settings.getExerciseSettings());
	
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
	
	        String taskDescription = InstructionsManager.componseTaskDescription(settings.getExerciseSettings());
	        ArrayList<ArrayList<String>> distractors =
	                DistractorManager.generateDistractors(settings.getExerciseSettings(), matchResult.getConstructions());
	
	        return new JsonComponents(plainTextPerSentence, pureHtmlElements, matchResult.getConstructions(),
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), distractors, taskDescription);
    	} else {
    		return null;
    	}
    }

}