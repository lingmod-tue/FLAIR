package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.DocumentBasedExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.AdvancedFIBJsonManager;

public class FillInTheBlanksSettings extends ContentTypeSettings {

    public FillInTheBlanksSettings(String name) {
        super("advanced_fib.h5p", new AdvancedFIBJsonManager(true), true, new DocumentBasedExerciseGenerator(), 
        		"H5P.XAdvancedBlanks 0.1", name);
    }

}
