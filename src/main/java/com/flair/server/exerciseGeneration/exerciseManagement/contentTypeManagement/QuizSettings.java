package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.QuizGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.QuizJsonManager;

public class QuizSettings extends ContentTypeSettings {

    public QuizSettings(ArrayList<ContentTypeSettings> exercises, String name) {
        super("quiz.h5p", new QuizJsonManager(), true, new QuizGenerator(), "H5P.XQuestionSet 0.1", name);
        this.exercises = exercises;
    }

    private ArrayList<ContentTypeSettings> exercises;

    public ArrayList<ContentTypeSettings> getExercises() { return exercises; }

}
