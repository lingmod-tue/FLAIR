package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.AdvancedFIBXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.AdvancedFIBJsonManager;

public class FillInTheBlanksSettings extends ContentTypeSettings {

    public FillInTheBlanksSettings(String name) {
        super("advanced_fib.h5p", new AdvancedFIBJsonManager(true), true, new SimpleExerciseGenerator(), 
        		"H5P.XAdvancedBlanks 0.1", name, new AdvancedFIBXmlManager(true));
    }

}
