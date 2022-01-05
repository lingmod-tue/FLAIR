package com.flair.server.exerciseGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.util.ExerciseTopic;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DistractorProperties;


public class ExerciseData {

	public ExerciseData(ArrayList<TextPart> parts) {
		this.parts = parts;
	}

	private ArrayList<TextPart> parts;		
	private ArrayList<String> instructionLemmas = new ArrayList<>();
	private String plainText;
	private ExerciseTopic topic;
	private ArrayList<BracketsProperties> bracketsProperties = new ArrayList<>();
	ArrayList<DistractorProperties> distractorProperties = new ArrayList<>();
	
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
	
}
