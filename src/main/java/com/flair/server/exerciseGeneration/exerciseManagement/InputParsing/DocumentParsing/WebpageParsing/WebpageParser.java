package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing;

import org.jsoup.Jsoup;

import com.flair.server.exerciseGeneration.downloadManagement.HtmlManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseGenerationMetadata;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.shared.exerciseGeneration.DocumentExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class WebpageParser {

	public ExerciseData parseWebpage(ExerciseGenerationMetadata settings, NlpManager nlpManager, ResourceDownloader resourceDownloader) {
    	DocumentExerciseSettings exerciseSettings = (DocumentExerciseSettings)settings.getExerciseSettings();

		// We cannot operate on the same document for all exercises (in-place modifications), so we create a copy
    	WebpageData data = new WebpageData(HtmlManager.makeHtmlEmbeddable(Jsoup.parse(settings.getDoc().toString())));
        
		new Indexer().matchHtmlToPlainText(exerciseSettings, data, nlpManager);
		new Matcher().prepareDomForSplitting(data);
        HtmlManipulator.manipulateDom(data.getDocument(), data.getTextBoundaries(), 
        		exerciseSettings.getContentType().equals(ExerciseType.MARK_THE_WORDS));
        
        ResourceExtractor.extractResources(settings.getExerciseSettings().getUrl(), resourceDownloader, data.getDocument(), data);
        settings.getResources().addAll(data.getDownloadedResources());
        
        SentenceManager.extractSentencesFromDom(data);
        return HtmlSplitter.preparePureHtmlElements(data, exerciseSettings);
	}
}
