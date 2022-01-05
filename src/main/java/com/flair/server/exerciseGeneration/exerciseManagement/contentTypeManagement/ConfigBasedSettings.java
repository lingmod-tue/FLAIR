package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedConditionalExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator;

public class ConfigBasedSettings extends ContentTypeSettings {

    public ConfigBasedSettings(String topic) {
        super(null, null, true, 
        		topic.equals("'if'") ? new ConfigBasedConditionalExerciseGenerator() : new ConfigBasedExerciseGenerator(),
        	null, null);
    }

    private ArrayList<ContentTypeSettings> exercises;

    public ArrayList<ContentTypeSettings> getExercises() { return exercises; }

}
