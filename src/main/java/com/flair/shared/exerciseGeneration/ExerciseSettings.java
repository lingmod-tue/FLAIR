package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExerciseSettings implements IsSerializable{

    private ArrayList<Construction> constructions;
    private String url;
    private String plainText;
    private ArrayList<Pair<Integer, Integer>> removedParts;
    private String contentType;
    private String quiz;
    private ArrayList<DistractorProperties> distractors;
    private ArrayList<BracketsProperties> brackets;
    private int nDistractors;
    private String taskName;
    private boolean downloadResources;
    private boolean onlyText;
    private boolean generateFeedback;

    public ExerciseSettings() {}
    
    public ExerciseSettings(ArrayList<Construction> constructions, String url,
                            String plainText, ArrayList<Pair<Integer, Integer>> removedParts,
                            String contentType, String quiz, ArrayList<DistractorProperties> distractors, 
                            ArrayList<BracketsProperties> brackets, int nDistractors, String taskName, boolean downloadResources, 
                            boolean onlyText, boolean generateFeedback) {
        this.constructions = constructions;
        this.url = url;
        this.plainText = plainText;
        this.removedParts = removedParts;
        this.contentType = contentType;
        this.quiz = quiz;
        this.distractors = distractors;
        this.brackets = brackets;
        this.nDistractors = nDistractors;
        this.taskName = taskName;
        this.downloadResources = downloadResources;
        this.onlyText = onlyText;
        this.generateFeedback = generateFeedback;
    }

    public ArrayList<Construction> getConstructions() { return constructions; }
    public String getUrl() { return url; }
    public String getPlainText() { return plainText; }
    public ArrayList<Pair<Integer, Integer>> getRemovedParts() { return removedParts; }
    public String getContentType() { return contentType; }
    public String getQuiz() { return quiz; }
    public ArrayList<DistractorProperties> getDistractors() { return distractors; }
    public ArrayList<BracketsProperties> getBrackets() { return brackets; }
    public int getnDistractors() { return nDistractors; }
	public String getTaskName() { return taskName; }
	public boolean isDownloadResources() { return downloadResources; }
	public boolean isOnlyText() { return onlyText; }
	public boolean isGenerateFeedback() { return generateFeedback; }

}