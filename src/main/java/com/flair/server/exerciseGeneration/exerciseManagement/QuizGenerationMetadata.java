package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class QuizGenerationMetadata extends ExerciseGenerationMetadata {

    public QuizGenerationMetadata(String name) {
        super(ExerciseType.QUIZ, null);
    }

    private ArrayList<ExerciseGenerationMetadata> exercises;

    public ArrayList<ExerciseGenerationMetadata> getExercises() { return exercises; }

	public void setExercises(ArrayList<ExerciseGenerationMetadata> exercises) { this.exercises = exercises; }

}
