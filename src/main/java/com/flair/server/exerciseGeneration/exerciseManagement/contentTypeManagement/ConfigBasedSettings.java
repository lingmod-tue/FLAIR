package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator;

public class ConfigBasedSettings extends ContentTypeSettings {

    public ConfigBasedSettings() {
        super(null, null, true, new ConfigBasedExerciseGenerator(), null, null, null);
    }

    private ArrayList<ContentTypeSettings> exercises;

    public ArrayList<ContentTypeSettings> getExercises() { return exercises; }

}
