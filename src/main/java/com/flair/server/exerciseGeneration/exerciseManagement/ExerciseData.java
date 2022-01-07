package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.InstructionsProperties;


public class ExerciseData {

	public ExerciseData(ArrayList<TextPart> parts) {
		this.parts = parts;
	}

	private ArrayList<TextPart> parts;		
	private ArrayList<String> instructionLemmas = new ArrayList<>();
	private String plainText;
	private ExerciseTopic topic;
	private ArrayList<BracketsProperties> bracketsProperties = new ArrayList<>();
	private ArrayList<DistractorProperties> distractorProperties = new ArrayList<>();
	private ArrayList<InstructionsProperties> instructionProperties = new ArrayList<>();
	private ContentTypeSettings contentTypeSettings;
    private ArrayList<DownloadedResource> resources = new ArrayList<>();
    private String exerciseTitle;
    private ExerciseType exerciseType = null;

	private String instructions = "";
	
	public ArrayList<String> getInstructionLemmas() {
		return instructionLemmas;
	}
	public void setInstructionLemmas(ArrayList<String> instructionLemmas) {
		this.instructionLemmas = instructionLemmas;
	}
	public ArrayList<TextPart> getParts() {
		return parts;
	}
	public String getPlainText() {
		return plainText;
	}
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	public ExerciseTopic getTopic() {
		return topic;
	}
	public void setTopic(ExerciseTopic topic) {
		this.topic = topic;
	}
	public ArrayList<BracketsProperties> getBracketsProperties() {
		return bracketsProperties;
	}
	public void setBracketsProperties(ArrayList<BracketsProperties> bracketsProperties) {
		this.bracketsProperties = bracketsProperties;
	}
	public ArrayList<DistractorProperties> getDistractorProperties() {
		return distractorProperties;
	}
	public void setDistractorProperties(ArrayList<DistractorProperties> distractorProperties) {
		this.distractorProperties = distractorProperties;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public ArrayList<InstructionsProperties> getInstructionProperties() {
		return instructionProperties;
	}
	public void setInstructionProperties(ArrayList<InstructionsProperties> instructionProperties) {
		this.instructionProperties = instructionProperties;
	}
	public ContentTypeSettings getContentTypeSettings() {
		return contentTypeSettings;
	}
	public void setContentTypeSettings(ContentTypeSettings contentTypeSettings) {
		this.contentTypeSettings = contentTypeSettings;
	}
	public ArrayList<DownloadedResource> getResources() {
		return resources;
	}
	public void setResources(ArrayList<DownloadedResource> resources) {
		this.resources = resources;
	}
	public String getExerciseTitle() {
		return exerciseTitle;
	}
	public void setExerciseTitle(String exerciseTitle) {
		this.exerciseTitle = exerciseTitle;
	}
	public ExerciseType getExerciseType() {
		return exerciseType;
	}
	public void setExerciseType(ExerciseType exerciseType) {
		this.exerciseType = exerciseType;
	}

	
}
