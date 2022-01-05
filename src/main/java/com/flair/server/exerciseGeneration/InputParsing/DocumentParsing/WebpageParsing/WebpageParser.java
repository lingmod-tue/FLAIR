package com.flair.server.exerciseGeneration.InputParsing.DocumentParsing.WebpageParsing;

import org.jsoup.Jsoup;

import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class WebpageParser {

	public ExerciseData parseWebpage(ContentTypeSettings settings, NlpManager nlpManager, ResourceDownloader resourceDownloader) {
    	ExerciseSettings exerciseSettings = (ExerciseSettings)settings.getExerciseSettings();

		// We cannot operate on the same document for all exercises (in-place modifications), so we create a copy
    	WebpageData data = new WebpageData(Jsoup.parse(settings.getDoc().toString()));
        
		new Indexer().matchHtmlToPlainText(exerciseSettings, data, nlpManager);
		new Matcher().prepareDomForSplitting(data);
        HtmlManipulator.manipulateDom(data.getDocument(), data.getTextBoundaries(), 
        		exerciseSettings.getContentType().equals(ExerciseType.MARK));
        
        ResourceExtractor.extractResources(settings.getExerciseSettings().getUrl(), resourceDownloader, data.getDocument(), data);
        settings.getResources().addAll(data.getDownloadedResources());
        
        SentenceManager.extractSentencesFromDom(data);
        return HtmlSplitter.preparePureHtmlElements(data, exerciseSettings);
	}
}
