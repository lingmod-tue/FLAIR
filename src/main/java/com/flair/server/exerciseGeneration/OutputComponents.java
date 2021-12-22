package com.flair.server.exerciseGeneration;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.flair.shared.exerciseGeneration.Pair;

public class OutputComponents {
		
	public OutputComponents(JSONObject h5pJson, HashMap<String, String> previews, ArrayList<ArrayList<Pair<String,String>>> distractors, 
			String plainText, ArrayList<String> htmlElements, String taskDescription, ArrayList<Pair<String, Integer>> targets, 
			String name, ArrayList<Integer> conditionalTypes) {
		super();
		this.h5pJson = h5pJson;
		this.previews = previews;
		this.distractors = distractors;
		this.plainText = plainText;
		this.htmlElements = htmlElements;
		this.taskDescription = taskDescription;
		this.targets = targets;
		this.name = name;
		this.conditionalTypes = conditionalTypes;
	}
	
	private JSONObject h5pJson;
	private HashMap<String, String> previews;
	private HashMap<String, String> feedBookXml;
	private byte[] h5pFile;
	private HashMap<String, byte[]> xmlFile;
	private HashMap<String, byte[]> zipFiles;
	private ArrayList<ArrayList<Pair<String,String>>> distractors;
	private String plainText;
	private ArrayList<String> htmlElements;
	private String taskDescription;
	private ArrayList<OutputComponents> simpleExercises;
	private ArrayList<Pair<String, Integer>> targets;
	private String name;
    private ArrayList<Integer> conditionalTypes = new ArrayList<Integer>();

	public HashMap<String, byte[]> getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(HashMap<String, byte[]> xmlFile) {
		this.xmlFile = xmlFile;
	}
	public byte[] getH5pFile() {
		return h5pFile;
	}
	public ArrayList<ArrayList<Pair<String, String>>> getDistractors() {
		return distractors;
	}
	public String getPlainText() {
		return plainText;
	}
	public ArrayList<String> getHtmlElements() {
		return htmlElements;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public ArrayList<Pair<String, Integer>> getTargets() {
		return targets;
	}
	public ArrayList<Integer> getConditionalTypes() { return conditionalTypes; }

	public void setH5pFile(byte[] h5pFile) {
		this.h5pFile = h5pFile;
	}
	public JSONObject getH5pJson() {
		return h5pJson;
	}
	public HashMap<String, String> getPreviews() {
		return previews;
	}
	public HashMap<String, String> getFeedBookXml() {
		return feedBookXml;
	}
	public void setPreviews(HashMap<String, String> previews) {
		this.previews = previews;
	}
	public void setFeedBookXml(HashMap<String, String> feedBookXml) {
		this.feedBookXml = feedBookXml;
	}
	public ArrayList<OutputComponents> getSimpleExercises() {
		return simpleExercises;
	}
	public void setSimpleExercises(ArrayList<OutputComponents> simpleExercises) {
		this.simpleExercises = simpleExercises;
	}
	public String getName() {
		return name;
	}
	public HashMap<String, byte[]> getZipFiles() {
		return zipFiles;
	}
	public void setZipFiles(HashMap<String, byte[]> zipFiles) {
		this.zipFiles = zipFiles;
	}
	
}
