package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DocumentExerciseSettings extends ExerciseSettings implements IsSerializable {

    private ArrayList<Construction> constructions;
    private String plainText;
    private ArrayList<Pair<Integer, Integer>> removedParts;
    private String contentType;
    private ArrayList<String> distractors;
    private ArrayList<String> brackets;
    private ArrayList<String> instructions;
    private int nDistractors;
    private boolean downloadResources;
    private boolean onlyText;
    private ArrayList<String> instructionLemmas;
    private boolean isWebPage = true;
    private String fileContent;
    private String taskName;

	public DocumentExerciseSettings() {
		super();
	}
    
    public DocumentExerciseSettings(ArrayList<Construction> constructions, String url,
                            String plainText, ArrayList<Pair<Integer, Integer>> removedParts,
                            String contentType, String quiz, ArrayList<String> distractors, 
                            ArrayList<String> brackets, ArrayList<String> instructions, int nDistractors, String taskName, boolean downloadResources, 
                            boolean onlyText, boolean generateFeedback, int id, String fileName, String fileContent,
                            ArrayList<String> outputFormats, String topic) {
    	super(quiz, fileName, url, outputFormats, id, generateFeedback, topic);
    	
        this.constructions = constructions;
        this.plainText = plainText;
        this.removedParts = removedParts;
        this.contentType = contentType;
        this.distractors = distractors;
        this.brackets = brackets;
        this.nDistractors = nDistractors;
        this.downloadResources = downloadResources;
        this.onlyText = onlyText;
        this.instructions = instructions;
		this.fileContent = fileContent;
		this.taskName = taskName;
    }

    public ArrayList<Construction> getConstructions() { return constructions; }
    public String getPlainText() { return plainText; }
    public ArrayList<Pair<Integer, Integer>> getRemovedParts() { return removedParts; }
    public String getContentType() { return contentType; }
    public ArrayList<String> getDistractors() { return distractors; }
    public ArrayList<String> getBrackets() { return brackets; }
    public ArrayList<String> getInstructions() { return instructions; }
    public int getnDistractors() { return nDistractors; }
    public boolean isDownloadResources() { return downloadResources; }
	public boolean isOnlyText() { return onlyText; }
	public ArrayList<String> getInstructionLemmas() { return instructionLemmas; }
	public boolean isWebPage() { return isWebPage; }
	public String getFileContent() { return fileContent; }
	public String getTaskName() { return taskName; }
	
	public void setInstructionLemmas(ArrayList<String> instructionLemmas) { this.instructionLemmas = instructionLemmas; }
	public void setWebPage(boolean isWebPage) { this.isWebPage = isWebPage; }
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
}