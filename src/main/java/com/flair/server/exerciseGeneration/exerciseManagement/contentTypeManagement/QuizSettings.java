package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class QuizSettings extends ContentTypeSettings {

    public QuizSettings(String name) {
        super(ExerciseType.QUIZ);
    }

    private ArrayList<ContentTypeSettings> exercises;

    public ArrayList<ContentTypeSettings> getExercises() { return exercises; }

	public void setExercises(ArrayList<ContentTypeSettings> exercises) { this.exercises = exercises; }

}
