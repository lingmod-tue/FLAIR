package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.JsonManager;

import edu.stanford.nlp.util.Pair;

public class JsonComponents {

    public JsonComponents(ArrayList<String> plainTextElements, ArrayList<String> pureHtmlElements,
                          ArrayList<String> constructions, JsonManager jsonManager, String contentTypeLibrary, String folderName, 
                          ArrayList<ArrayList<Pair<String, String>>> distractors, String taskDescription) {
    	this.plainTextElements = plainTextElements;
        this.pureHtmlElements = pureHtmlElements;
        this.constructions = constructions;
        this.jsonManager = jsonManager;
        this.contentTypeLibrary = contentTypeLibrary;
        this.folderName = folderName;
        this.distractors = distractors;
        this.taskDescription = taskDescription;
    }

    private ArrayList<String> plainTextElements;
    private ArrayList<String> pureHtmlElements;
    private ArrayList<String> constructions;
    private JsonManager jsonManager;
    private String contentTypeLibrary;
    private String folderName;
    private ArrayList<ArrayList<Pair<String, String>>> distractors;
    private String taskDescription;

    public ArrayList<String> getPlainTextElements() { return plainTextElements; }
    public ArrayList<String> getPureHtmlElements() { return pureHtmlElements; }
    public ArrayList<String> getConstructions() { return constructions; }
    public JsonManager getJsonManager() { return jsonManager; }
    public String getContentTypeLibrary() {return contentTypeLibrary; }
    public String getFolderName() { return folderName; }
    public ArrayList<ArrayList<Pair<String, String>>> getDistractors() { return distractors; }
    public String getTaskDescription() { return taskDescription; }

}