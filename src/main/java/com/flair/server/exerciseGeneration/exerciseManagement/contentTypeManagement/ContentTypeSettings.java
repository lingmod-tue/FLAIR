package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import java.util.ArrayList;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;
import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedConditionalExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.DocumentBasedExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.QuizGenerator;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.IExerciseSettings;

public class ContentTypeSettings {
    
    public ContentTypeSettings(ExerciseType type) {
		exerciseType = type;

    	if(type == ExerciseType.FIB) {
    		resourceFolder = "advanced_fib.h5p";
    		escapeAsterisksInHtml = true;
    		exerciseGenerator = new DocumentBasedExerciseGenerator();
    		contentTypeLibrary = "H5P.XAdvancedBlanks 0.1";
        } else if(type == ExerciseType.SINGLE_CHOICE) {
        	resourceFolder = "advanced_fib.h5p";
        	escapeAsterisksInHtml = true;
        	exerciseGenerator = new DocumentBasedExerciseGenerator();
        	contentTypeLibrary = "H5P.XAdvancedBlanks 0.1";
        } else if(type == ExerciseType.DRAG_SINGLE) {
        	resourceFolder = "drag_the_words_1task.h5p";
        	escapeAsterisksInHtml = true;
        	exerciseGenerator = new DocumentBasedExerciseGenerator(); 
        	contentTypeLibrary = "H5P.XXDragText 0.1";
        } else if(type == ExerciseType.DRAG_MULTI) {
        	resourceFolder = "drag_the_words.h5p";
        	escapeAsterisksInHtml = true;
        	exerciseGenerator = new DocumentBasedExerciseGenerator();
        	contentTypeLibrary = "H5P.XDragText 1.1";
        } else if(type == ExerciseType.MARK) {
        	resourceFolder = "mark_the_words.h5p";
        	escapeAsterisksInHtml = false;
        	exerciseGenerator = new DocumentBasedExerciseGenerator();
        	contentTypeLibrary = "H5P.XMarkTheWords 0.1";
        } else if(type == ExerciseType.MEMORY) {
        	resourceFolder = "memory.h5p";
        	escapeAsterisksInHtml = false;
        	exerciseGenerator = new DocumentBasedExerciseGenerator();
        	contentTypeLibrary = "H5P.XMemoryGame 0.1";
    	} else if(type == ExerciseType.JUMBLED_SENTENCES) {
    		resourceFolder = "drag_the_words.h5p";
    		escapeAsterisksInHtml = true;
    		exerciseGenerator = new DocumentBasedExerciseGenerator();
    		contentTypeLibrary = "H5P.XDragText 1.1";
        } else if(type == ExerciseType.CATEGORIZE) {
        	resourceFolder = "drag_the_words.h5p";
        	escapeAsterisksInHtml = true;
        	exerciseGenerator = new DocumentBasedExerciseGenerator();
        	contentTypeLibrary = "H5P.XDragText 1.1";
        } else if(type == ExerciseType.QUIZ) {
        	resourceFolder = "quiz.h5p";
        	escapeAsterisksInHtml = true;
        	exerciseGenerator = new QuizGenerator();
        	contentTypeLibrary = "H5P.XQuestionSet 0.1";
        } else {
            throw new IllegalArgumentException();
        }
	}
    
    public ContentTypeSettings(String topic) {
		if(topic.equals("'if'")) {
			exerciseGenerator = new ConfigBasedConditionalExerciseGenerator();
		}
    }

    private String resourceFolder;
    private boolean escapeAsterisksInHtml;
    private ExerciseGenerator exerciseGenerator;
    private String contentTypeLibrary;
    private IExerciseSettings exerciseSettings;
    private Element doc;
    private ArrayList<DownloadedResource> resources;
    private int index;
    private ExerciseType exerciseType;

	public String getResourceFolder() { return resourceFolder; }
    public boolean isEscapeAsterisksInHtml() {return escapeAsterisksInHtml; }
    public ExerciseGenerator getExerciseGenerator() { return exerciseGenerator; }
    public String getContentTypeLibrary() { return contentTypeLibrary; }
    public IExerciseSettings getExerciseSettings() { return exerciseSettings; }
    public Element getDoc() { return doc; }
    public ArrayList<DownloadedResource> getResources() { return resources; }
    public int getIndex() { return index; }
	public ExerciseType getExerciseType() { return exerciseType; }

    public void setExerciseSettings(IExerciseSettings exerciseSettings) { this.exerciseSettings = exerciseSettings; }
    public void setDoc(Element doc) { this.doc = doc; }
	public void setResources(ArrayList<DownloadedResource> resources) { this.resources = resources; }
	public void setIndex(int index) { this.index = index; }
	public void setExerciseType(ExerciseType exerciseType) { this.exerciseType = exerciseType; }

}
