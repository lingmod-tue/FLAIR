package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public class ExerciseGenerationMetadata {
    
    public ExerciseGenerationMetadata(String type, String topic) {
    	if(type == null) {
    		exerciseGenerator = new ConfigBasedExerciseGenerator(topic);
    	} else if(type.equals(ExerciseType.QUIZ)) {
        	exerciseGenerator = new QuizGenerator(topic);
        } else {
        	exerciseGenerator = new DocumentBasedExerciseGenerator(topic);
        }
	}

    private ExerciseGenerator exerciseGenerator;
    private ExerciseSettings exerciseSettings;
    private Element doc;
    private ArrayList<DownloadedResource> resources = new ArrayList<>();

    public ExerciseGenerator getExerciseGenerator() { return exerciseGenerator; }
    public ExerciseSettings getExerciseSettings() { return exerciseSettings; }
    public Element getDoc() { return doc; }
    public ArrayList<DownloadedResource> getResources() { return resources; }

    public void setExerciseSettings(ExerciseSettings exerciseSettings) { this.exerciseSettings = exerciseSettings; }
    public void setDoc(Element doc) { this.doc = doc; }
	public void setResources(ArrayList<DownloadedResource> resources) { this.resources = resources; }

}
