package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.DocumentBasedExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.AdvancedFIBJsonManager;

public class SingleChoiceSettings extends ContentTypeSettings {

    public SingleChoiceSettings(String name) {
        super("advanced_fib.h5p", new AdvancedFIBJsonManager(false), true, new DocumentBasedExerciseGenerator(), 
        		"H5P.XAdvancedBlanks 0.1", name);
    }

}
