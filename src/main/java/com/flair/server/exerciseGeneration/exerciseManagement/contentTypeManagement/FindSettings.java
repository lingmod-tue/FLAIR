package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.DocumentBasedExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.FindJsonManager;

public class FindSettings extends ContentTypeSettings {

    public FindSettings(String name) {
        super("mark_the_words.h5p", new FindJsonManager(), false, new DocumentBasedExerciseGenerator(), "H5P.XMarkTheWords 0.1", 
        		name);
    }

}
