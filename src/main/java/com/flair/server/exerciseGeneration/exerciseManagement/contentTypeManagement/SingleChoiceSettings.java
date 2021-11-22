package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.SimpleExerciseXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.AdvancedFIBJsonManager;

public class SingleChoiceSettings extends ContentTypeSettings {

    public SingleChoiceSettings(String name) {
        super("advanced_fib.h5p", new AdvancedFIBJsonManager(false), true, new SimpleExerciseGenerator(), 
        		"H5P.XAdvancedBlanks 0.1", name, new SimpleExerciseXmlManager());
    }

}
