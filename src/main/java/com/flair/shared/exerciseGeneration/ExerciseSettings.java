package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExerciseSettings implements IsSerializable, IExerciseSettings {

    private ArrayList<Construction> constructions;
    private String url;
    private String plainText;
    private ArrayList<Pair<Integer, Integer>> removedParts;
    private ExerciseType contentType;
    private String quiz;
    private ArrayList<DistractorProperties> distractors;
    private ArrayList<BracketsProperties> brackets;
    private ArrayList<InstructionsProperties> instructions;
    private int nDistractors;
    private String taskName;
    private boolean downloadResources;
    private boolean onlyText;
    private boolean generateFeedback;
    private int id;
    private String fileName;
    private String fileContent;
    private ArrayList<OutputFormat> outputFormats;
    private ArrayList<String> instructionLemmas;

	public ExerciseSettings() {}
    
    public ExerciseSettings(ArrayList<Construction> constructions, String url,
                            String plainText, ArrayList<Pair<Integer, Integer>> removedParts,
                            ExerciseType contentType, String quiz, ArrayList<DistractorProperties> distractors, 
                            ArrayList<BracketsProperties> brackets, ArrayList<InstructionsProperties> instructions, int nDistractors, String taskName, boolean downloadResources, 
                            boolean onlyText, boolean generateFeedback, int id, String fileName, String fileContent,
                            ArrayList<OutputFormat> outputFormats) {
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
        this.id = id;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.instructions = instructions;
        this.outputFormats = outputFormats;
    }

    public ArrayList<Construction> getConstructions() { return constructions; }
    @Override
    public String getUrl() { return url; }
    public String getPlainText() { return plainText; }
    public ArrayList<Pair<Integer, Integer>> getRemovedParts() { return removedParts; }
    public ExerciseType getContentType() { return contentType; }
    @Override
    public String getQuiz() { return quiz; }
    public ArrayList<DistractorProperties> getDistractors() { return distractors; }
    public ArrayList<BracketsProperties> getBrackets() { return brackets; }
    public ArrayList<InstructionsProperties> getInstructions() { return instructions; }
    public int getnDistractors() { return nDistractors; }
	public String getTaskName() { return taskName; }
	public boolean isDownloadResources() { return downloadResources; }
	public boolean isOnlyText() { return onlyText; }
	public boolean isGenerateFeedback() { return generateFeedback; }
	@Override
    public int getId() { return id; }
    @Override
	public String getFileName() { return fileName; }
    @Override
	public String getFileContent() { return fileContent; }
	@Override
	public ArrayList<OutputFormat> getOutputFormats() { return outputFormats; }
	public ArrayList<String> getInstructionLemmas() { return instructionLemmas; }

	@Override
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
	public void setInstructionLemmas(ArrayList<String> instructionLemmas) { this.instructionLemmas = instructionLemmas; }

}