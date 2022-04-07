package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;


public class ExerciseData {

	public ExerciseData(ArrayList<TextPart> parts) {
		this.parts = parts;
	}

	private ArrayList<TextPart> parts;		
	private ArrayList<String> instructionLemmas = new ArrayList<>();
	private String plainText;
	private String topic;
	private ArrayList<String> bracketsProperties = new ArrayList<>();
	private ArrayList<String> distractorProperties = new ArrayList<>();
	private ArrayList<String> instructionProperties = new ArrayList<>();
    private ArrayList<DownloadedResource> resources = new ArrayList<>();
    private String exerciseTitle;
    private String exerciseType = null;
	private String instructions = "";
	private String secondInstructions = null;
	private String support = null;
	private String subtopic = "";
	private String hintLink = null;
	
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
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public ArrayList<String> getBracketsProperties() {
		return bracketsProperties;
	}
	public void setBracketsProperties(ArrayList<String> bracketsProperties) {
		this.bracketsProperties = bracketsProperties;
	}
	public ArrayList<String> getDistractorProperties() {
		return distractorProperties;
	}
	public void setDistractorProperties(ArrayList<String> distractorProperties) {
		this.distractorProperties = distractorProperties;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public ArrayList<String> getInstructionProperties() {
		return instructionProperties;
	}
	public void setInstructionProperties(ArrayList<String> instructionProperties) {
		this.instructionProperties = instructionProperties;
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
	public String getExerciseType() {
		return exerciseType;
	}
	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}
	public String getSecondInstructions() {
		return secondInstructions;
	}
	public void setSecondInstructions(String secondInstructions) {
		this.secondInstructions = secondInstructions;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	public String getSubtopic() {
		return subtopic;
	}
	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}
	public String getHintLink() {
		return hintLink;
	}
	public void setHintLink(String hintLink) {
		this.hintLink = hintLink;
	}

	
}
