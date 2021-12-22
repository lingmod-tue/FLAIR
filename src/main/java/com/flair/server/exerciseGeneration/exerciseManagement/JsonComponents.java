package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.JsonManager;
import com.flair.shared.exerciseGeneration.Pair;

public class JsonComponents {

    public JsonComponents(ArrayList<String> plainTextElements, ArrayList<String> pureHtmlElements,
                          ArrayList<Pair<String, Integer>> constructions, JsonManager jsonManager, String contentTypeLibrary, String folderName, 
                          ArrayList<ArrayList<Pair<String, String>>> distractors, String taskDescription,
                          ArrayList<String> instructionLemmas) {
    	this.plainTextElements = plainTextElements;
        this.pureHtmlElements = pureHtmlElements;
        this.constructions = constructions;
        this.jsonManager = jsonManager;
        this.contentTypeLibrary = contentTypeLibrary;
        this.folderName = folderName;
        this.distractors = distractors;
        this.taskDescription = taskDescription;
        this.instructionLemmas = instructionLemmas;
    }
    
    

    public JsonComponents(ArrayList<String> plainTextElements, ArrayList<String> pureHtmlElements,
			ArrayList<Pair<String, Integer>> constructions, JsonManager jsonManager, String contentTypeLibrary,
			String folderName, ArrayList<ArrayList<Pair<String, String>>> distractors, String taskDescription,
			ArrayList<String> instructionLemmas, ContentTypeSettings settings) {
    	this(plainTextElements, pureHtmlElements, constructions, jsonManager, contentTypeLibrary,
    			folderName, distractors, taskDescription, instructionLemmas);
		
		this.settings = settings;
	}



	private ArrayList<String> plainTextElements;
    private ArrayList<String> pureHtmlElements;
    private ArrayList<Pair<String, Integer>> constructions;
    private JsonManager jsonManager;
    private String contentTypeLibrary;
    private String folderName;
    private ArrayList<ArrayList<Pair<String, String>>> distractors;
    private String taskDescription;
    private ArrayList<String> instructionLemmas;
    private ContentTypeSettings settings;
    private ArrayList<Integer> conditionalType = new ArrayList<Integer>();

    public ArrayList<String> getPlainTextElements() { return plainTextElements; }
    public ArrayList<String> getPureHtmlElements() { return pureHtmlElements; }
    public ArrayList<Pair<String, Integer>> getConstructions() { return constructions; }
    public JsonManager getJsonManager() { return jsonManager; }
    public String getContentTypeLibrary() {return contentTypeLibrary; }
    public String getFolderName() { return folderName; }
    public ArrayList<ArrayList<Pair<String, String>>> getDistractors() { return distractors; }
    public String getTaskDescription() { return taskDescription; }
	public ArrayList<String> getInstructionLemmas() { return instructionLemmas; }
	public ContentTypeSettings getSettings() { return settings; }
	public ArrayList<Integer> getConditionalType() { return conditionalType; }

	public void setConditionalType(ArrayList<Integer> conditionalType) {this.conditionalType = conditionalType;}

}